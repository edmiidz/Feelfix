import SwiftUI
import MapKit

struct FeelingDetailView: View {
    let feeling: Feeling
    @State private var showEditSheet = false
    @State private var showMapSheet = false
    
    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 16) {
                // Header with rating and emoji
                headerView
                    .padding(.bottom, 8)
                
                Divider()
                
                // Main content
                Group {
                    infoRow(title: "Feeling", value: feeling.feeling)
                    
                    infoRow(title: "Time", value: feeling.feelingTime, formatter: dateFormatter)
                    
                    infoRow(title: "Status", value: feeling.status)
                    
                    infoRow(title: "Involved People", value: feeling.involvedPeople)
                    
                    infoRow(title: "Affected Instincts", value: feeling.mainAffectedInstinct)
                    
                    if hasLocation {
                        locationRow
                    }
                }
                
                Divider()
                
                // Description sections
                Group {
                    textSection(title: "What happened?", text: feeling.feelingCauseDesc)
                    
                    textSection(title: "Would have been Better to:", text: feeling.wouldHaveBeenBetter)
                    
                    textSection(title: "Would have been Worse to:", text: feeling.wouldHaveBeenWorse)
                }
                
                Divider()
                
                // Metadata
                Group {
                    infoRow(title: "Created", value: feeling.created, formatter: dateFormatter)
                    
                    infoRow(title: "Last Updated", value: feeling.updatedLast, formatter: dateFormatter)
                }
            }
            .padding()
        }
        .navigationTitle("Feeling Details")
        .toolbar {
            ToolbarItem(placement: .navigationBarTrailing) {
                Button("Edit") {
                    showEditSheet = true
                }
            }
        }
        .sheet(isPresented: $showEditSheet) {
            FeelingFormView(feeling: feeling)
        }
        .sheet(isPresented: $showMapSheet) {
            locationMapView
        }
    }
    
    // MARK: - Subviews
    
    private var headerView: some View {
        HStack {
            VStack(alignment: .leading) {
                Text("Rating")
                    .font(.headline)
                Text("\(feeling.ratingValue)")
                    .font(.largeTitle)
                    .fontWeight(.bold)
                    .foregroundColor(getRatingColor(feeling.ratingValue))
            }
            
            Spacer()
            
            Image(getFeelingImage())
                .resizable()
                .frame(width: 60, height: 60)
        }
    }
    
    private func infoRow<T>(title: String, value: T, formatter: Formatter? = nil) -> some View {
        HStack(alignment: .top) {
            Text(title + ":")
                .font(.headline)
                .frame(width: 120, alignment: .leading)
            
            if let formatter = formatter, let formattable = value as? NSObjectProtocol, formattable.responds(to: #selector(NSObject.description(with:))) {
                Text(formatter.string(for: value) ?? "")
                    .font(.body)
            } else {
                Text("\(value)")
                    .font(.body)
            }
            
            Spacer()
        }
    }
    
    private func textSection(title: String, text: String) -> some View {
        VStack(alignment: .leading, spacing: 8) {
            Text(title)
                .font(.headline)
            
            Text(text)
                .font(.body)
                .padding()
                .frame(maxWidth: .infinity, alignment: .leading)
                .background(Color(.systemGray6))
                .cornerRadius(8)
        }
    }
    
    private var locationRow: some View {
        HStack {
            Text("Location:")
                .font(.headline)
                .frame(width: 120, alignment: .leading)
            
            Text("Map")
                .font(.body)
                .foregroundColor(.blue)
                .onTapGesture {
                    showMapSheet = true
                }
            
            Spacer()
        }
    }
    
    private var locationMapView: some View {
        let coordinate = CLLocationCoordinate2D(
            latitude: feeling.latitude,
            longitude: feeling.longitude
        )
        let region = MKCoordinateRegion(
            center: coordinate,
            span: MKCoordinateSpan(latitudeDelta: 0.05, longitudeDelta: 0.05)
        )
        
        return NavigationView {
            Map(coordinateRegion: .constant(region), annotationItems: [MapAnnotation(coordinate: coordinate)]) { item in
                MapMarker(coordinate: item.coordinate, tint: .red)
            }
            .edgesIgnoringSafeArea(.all)
            .navigationTitle("Feeling Location")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button("Done") {
                        showMapSheet = false
                    }
                }
            }
        }
    }
    
    // MARK: - Helper methods
    
    private var hasLocation: Bool {
        return feeling.latitude != 0 && feeling.longitude != 0
    }
    
    private func getFeelingImage() -> String {
        if feeling.ratingValue <= -6 {
            return "sm5"
        } else if feeling.ratingValue <= -1 {
            return "sm4"
        } else if feeling.ratingValue == 0 {
            return "sm3"
        } else if feeling.ratingValue <= 5 {
            return "sm2"
        } else {
            return "sm1"
        }
    }
    
    private func getRatingColor(_ rating: Int) -> Color {
        if rating < -5 {
            return Color.red
        } else if rating < 0 {
            return Color.orange
        } else if rating == 0 {
            return Color.gray
        } else if rating < 6 {
            return Color.green
        } else {
            return Color.blue
        }
    }
    
    // For location pin on the map
    private struct MapAnnotation: Identifiable {
        let id = UUID()
        let coordinate: CLLocationCoordinate2D
    }
    
    // Date formatter
    private let dateFormatter: DateFormatter = {
        let formatter = DateFormatter()
        formatter.dateStyle = .medium
        formatter.timeStyle = .short
        return formatter
    }()
}

// Preview
struct FeelingDetailView_Previews: PreviewProvider {
    static var previews: some View {
        NavigationView {
            FeelingDetailView(feeling: Feeling.sample)
        }
    }
}
