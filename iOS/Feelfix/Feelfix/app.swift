import SwiftUI

@main
struct FeelFixApp: App {
    // Set up persistence controller
    let persistenceController = PersistenceController.shared
    
    // App body
    var body: some Scene {
        WindowGroup {
            ContentView()
                .environment(\.managedObjectContext, persistenceController.container.viewContext)
        }
    }
}

// Main content view
struct ContentView: View {
    @State private var selectedTab: Tab = .feelings
    
    enum Tab {
        case feelings, settings
    }
    
    var body: some View {
        TabView(selection: $selectedTab) {
            FeelingsListView()
                .tabItem {
                    Label("Feelings", systemImage: "heart.fill")
                }
                .tag(Tab.feelings)
            
            SettingsView()
                .tabItem {
                    Label("Settings", systemImage: "gear")
                }
                .tag(Tab.settings)
        }
    }
}

// Settings view
struct SettingsView: View {
    @AppStorage("displayField1") private var displayField1: String = "Feeling"
    @AppStorage("displayField2") private var displayField2: String = "from1to10"
    @AppStorage("displayField3") private var displayField3: String = "feelingTime"
    @AppStorage("displayField4") private var displayField4: String = "mainAffectedInstinct"
    @AppStorage("rowColorScheme") private var rowColorScheme: String = "Highlight Weekends"
    
    @State private var showingBackupSheet = false
    @State private var showingRestoreSheet = false
    
    private let fieldOptions = [
        ("Feeling", "feeling"),
        ("Rating", "ratingValue"),
        ("Time", "feelingTime"),
        ("Affected Instinct", "mainAffectedInstinct"),
        ("Status", "status"),
        ("People Involved", "involvedPeople"),
        ("What Happened", "feelingCauseDesc"),
        ("Created", "created"),
        ("Updated Last", "updatedLast")
    ]
    
    private let colorSchemeOptions = [
        "Highlight Weekends",
        "Mood Grades"
    ]
    
    var body: some View {
        NavigationView {
            Form {
                Section(header: Text("Search Results Settings")) {
                    Picker("Display Field 1", selection: $displayField1) {
                        ForEach(fieldOptions, id: \.0) { option in
                            Text(option.0).tag(option.1)
                        }
                    }
                    
                    Picker("Display Field 2", selection: $displayField2) {
                        ForEach(fieldOptions, id: \.0) { option in
                            Text(option.0).tag(option.1)
                        }
                    }
                    
                    Picker("Display Field 3", selection: $displayField3) {
                        ForEach(fieldOptions, id: \.0) { option in
                            Text(option.0).tag(option.1)
                        }
                    }
                    
                    Picker("Display Field 4", selection: $displayField4) {
                        ForEach(fieldOptions, id: \.0) { option in
                            Text(option.0).tag(option.1)
                        }
                    }
                }
                
                Section(header: Text("Row Color Scheme")) {
                    Picker("Row Colors", selection: $rowColorScheme) {
                        ForEach(colorSchemeOptions, id: \.self) { option in
                            Text(option)
                        }
                    }
                    .pickerStyle(.menu)
                }
                
                Section(header: Text("Data Backup")) {
                    Button("Backup to iCloud") {
                        showingBackupSheet = true
                    }
                    
                    Button("Restore from iCloud") {
                        showingRestoreSheet = true
                    }
                }
                
                Section(header: Text("About")) {
                    HStack {
                        Text("Version")
                        Spacer()
                        Text("1.0.0")
                            .foregroundColor(.secondary)
                    }
                    
                    HStack {
                        Text("Build")
                        Spacer()
                        Text("1")
                            .foregroundColor(.secondary)
                    }
                }
            }
            .navigationTitle("Settings")
            .sheet(isPresented: $showingBackupSheet) {
                BackupView(isBackup: true)
            }
            .sheet(isPresented: $showingRestoreSheet) {
                BackupView(isBackup: false)
            }
        }
    }
}

// Backup/Restore view placeholder
// (Will be implemented with CloudKit functionality later)
struct BackupView: View {
    @Environment(\.dismiss) private var dismiss
    let isBackup: Bool
    
    @State private var isProcessing = false
    @State private var isComplete = false
    @State private var errorMessage: String?
    
    var body: some View {
        NavigationView {
            VStack {
                if isProcessing {
                    ProgressView()
                        .padding()
                    Text(isBackup ? "Backing up data..." : "Restoring data...")
                } else if isComplete {
                    Image(systemName: "checkmark.circle.fill")
                        .font(.system(size: 60))
                        .foregroundColor(.green)
                        .padding()
                    Text(isBackup ? "Backup Complete" : "Restore Complete")
                } else if let errorMessage = errorMessage {
                    Image(systemName: "exclamationmark.triangle.fill")
                        .font(.system(size: 60))
                        .foregroundColor(.red)
                        .padding()
                    Text("Error: \(errorMessage)")
                } else {
                    Text(isBackup ? 
                         "This will back up your feelings data to iCloud." :
                         "This will restore your feelings data from iCloud.")
                    .padding()
                    
                    Button(isBackup ? "Start Backup" : "Start Restore") {
                        // Simulate the process
                        isProcessing = true
                        
                        // In a real app, this would call CloudKit or similar
                        DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
                            isProcessing = false
                            isComplete = true
                        }
                    }
                    .buttonStyle(.borderedProminent)
                    .padding()
                }
                
                Spacer()
            }
            .navigationTitle(isBackup ? "Backup to iCloud" : "Restore from iCloud")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button("Done") {
                        dismiss()
                    }
                }
            }
        }
    }
}

// Preview
struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
            .environment(\.managedObjectContext, PersistenceController.preview.container.viewContext)
    }
}

struct SettingsView_Previews: PreviewProvider {
    static var previews: some View {
        SettingsView()
    }
}
