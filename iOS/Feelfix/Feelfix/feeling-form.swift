import SwiftUI
import CoreLocation
import CoreData

struct FeelingFormView: View {
    @Environment(\.dismiss) private var dismiss
    @Environment(\.colorScheme) private var colorScheme
    
    @ObservedObject private var locationManager = LocationManager.shared
    
    // For new or editing existing feeling
    @State private var feeling: Feeling
    private var isEditing: Bool
    
    // UI State variables
    @State private var otherStatus: String = ""
    @State private var showDatePicker: Bool = false
    @State private var isSecuritySelected: Bool = true
    @State private var isSexSelected: Bool = false
    @State private var isSocialSelected: Bool = false
    
    // Initialize for new feeling
    init() {
        let now = Date()
        let feeling = Feeling(
            feeling: "",
            ratingValue: 0,
            feelingTime: now,
            feelingCauseDesc: "",
            mainAffectedInstinct: "security",
            status: "New",
            involvedPeople: "",
            wouldHaveBeenBetter: "",
            wouldHaveBeenWorse: "",
            created: now,
            updatedLast: now,
            latitude: 0.0,
            longitude: 0.0
        )
        _feeling = State(initialValue: feeling)
        self.isEditing = false
    }
    
    // Initialize for editing existing feeling
    init(feeling: Feeling) {
        _feeling = State(initialValue: feeling)
        self.isEditing = true
        
        // Initialize instinct selections
        _isSecuritySelected = State(initialValue: feeling.mainAffectedInstinct.contains("security"))
        _isSexSelected = State(initialValue: feeling.mainAffectedInstinct.contains("sex"))
        _isSocialSelected = State(initialValue: feeling.mainAffectedInstinct.contains("social"))
        
        // Initialize other status if needed
        if !["New", "Pending", "Shared", "Resolved"].contains(feeling.status) {
            _otherStatus = State(initialValue: feeling.status)
        }
    }
    
    var body: some View {
        NavigationView {
            Form {
                // Feeling rating section
                Section(header: Text("Feeling Rating")) {
                    HStack {
                        Text("-10")
                        Slider(value: Binding(
                            get: { Double(feeling.ratingValue + 10) },
                            set: { feeling.ratingValue = Int($0) - 10 }
                        ), in: 0...20, step: 1)
                        Text("+10")
                    }
                    
                    HStack {
                        Text("\(feeling.ratingValue)")
                            .frame(width: 35, height: 35)
                            .padding(5)
                            .background(Color(.systemGray6))
                            .cornerRadius(8)
                        
                        Text("Feelings Rating")
                            .bold()
                        
                        Spacer()
                        
                        Text(getFeelingEmoji())
                            .font(.system(size: 40))
                    }
                }
                
                // People involved section
                Section(header: Text("Related People/Organizations")) {
                    TextField("Who was involved?", text: $feeling.involvedPeople)
                }
                
                // Feelings section
                Section(header: Text("Feelings (comma separated)")) {
                    TextField("e.g. anger, fear, jealousy", text: $feeling.feeling)
                }
                
                // Affected instincts section
                Section(header: Text("Affected Instincts")) {
                    HStack {
                        Toggle("Sex", isOn: $isSexSelected)
                        Toggle("Security", isOn: $isSecuritySelected)
                        Toggle("Social", isOn: $isSocialSelected)
                    }
                }
                
                // Feeling time section
                Section(header: Text("Feeling Time")) {
                    HStack {
                        Text(feeling.feelingTime, formatter: dateFormatter)
                            .onTapGesture {
                                showDatePicker.toggle()
                            }
                    }
                    
                    if showDatePicker {
                        DatePicker("", selection: $feeling.feelingTime)
                            .datePickerStyle(.graphical)
                    }
                }
                
                // Status section
                Section(header: Text("Status")) {
                    Picker("Status", selection: $feeling.status) {
                        ForEach(FeelingStatus.allCases, id: \.self) { status in
                            Text(status.rawValue)
                        }
                    }
                    .pickerStyle(.menu)
                    
                    if feeling.status == "Other" {
                        TextField("Other status", text: $otherStatus)
                            .onChange(of: otherStatus) { newValue in
                                if feeling.status == "Other" {
                                    feeling.status = newValue
                                }
                            }
                    }
                }
                
                // What happened section
                Section(header: Text("What happened?")) {
                    TextEditor(text: $feeling.feelingCauseDesc)
                        .frame(minHeight: 100)
                }
                
                // Would have been better section
                Section(header: Text("Would have been Better to:")) {
                    TextEditor(text: $feeling.wouldHaveBeenBetter)
                        .frame(minHeight: 100)
                }
                
                // Would have been worse section
                Section(header: Text("Would have been Worse to:")) {
                    TextEditor(text: $feeling.wouldHaveBeenWorse)
                        .frame(minHeight: 100)
                }
            }
            .navigationTitle(isEditing ? "Edit Feeling" : "New Feeling")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .navigationBarLeading) {
                    Button("Cancel") {
                        dismiss()
                    }
                }
                
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button("Save") {
                        saveFeeling()
                        dismiss()
                    }
                }
            }
            .onAppear {
                print("Model URL:", Bundle.main.url(forResource: "FeelFix", withExtension: "momd"))
                print("Entities:", PersistenceController.shared.container.managedObjectModel.entities.map { $0.name })

                // Request location when the form appears
                locationManager.requestLocation()
            }
        }
    }
    
    // Helpers
    
    // was getFeelingImage()
    private func getFeelingEmoji() -> String {
        if feeling.ratingValue <= -6 {
            return "😢" // Very sad
        } else if feeling.ratingValue <= -1 {
            return "🙁" // Sad
        } else if feeling.ratingValue == 0 {
            return "😐" // Neutral
        } else if feeling.ratingValue <= 5 {
            return "🙂" // Happy
        } else {
            return "😄" // Very happy
        }
    }
    
    private func saveFeeling() {
        // Update the location
        if let location = locationManager.lastLocation {
            feeling.latitude = location.coordinate.latitude
            feeling.longitude = location.coordinate.longitude
        }
        
        // Update affected instincts
        var instincts: [String] = []
        if isSexSelected { instincts.append("sex") }
        if isSecuritySelected { instincts.append("security") }
        if isSocialSelected { instincts.append("social") }
        feeling.mainAffectedInstinct = instincts.joined(separator: ",")
        
        // Save to CoreData
        if feeling.status == "Other" {
            feeling.status = otherStatus
        }
        
        // Update the last updated time
        feeling.updatedLast = Date()
        
        // Save to database
        let persistence = PersistenceController.shared
        if isEditing {
            persistence.updateFeeling(feeling)
        } else {
            persistence.createFeeling(feeling)
        }
    }
    
    // Date formatter
    private let dateFormatter: DateFormatter = {
        let formatter = DateFormatter()
        formatter.dateStyle = .medium
        formatter.timeStyle = .short
        return formatter
    }()
}

// Location Manager for getting current coordinates
class LocationManager: NSObject, ObservableObject, CLLocationManagerDelegate {
    static let shared = LocationManager()
    
    private let locationManager = CLLocationManager()
    
    @Published var lastLocation: CLLocation?
    
    override init() {
        super.init()
        locationManager.delegate = self
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
    }
    
    func requestLocation() {
        locationManager.requestWhenInUseAuthorization()
        locationManager.requestLocation()
    }
    
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        guard let location = locations.last else { return }
        lastLocation = location
    }
    
    func locationManager(_ manager: CLLocationManager, didFailWithError error: Error) {
        print("Location error: \(error.localizedDescription)")
    }
}

// Preview
struct FeelingFormView_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            FeelingFormView()
                .preferredColorScheme(.light)
            
            FeelingFormView(feeling: Feeling.sample)
                .preferredColorScheme(.dark)
        }
    }
}
