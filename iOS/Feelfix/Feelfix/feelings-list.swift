import SwiftUI

struct FeelingsListView: View {
    @Environment(\.colorScheme) private var colorScheme
    
    @State private var searchText = ""
    @State private var showingAddForm = false
    @State private var selectedFeeling: Feeling?
    @State private var showAlert = false
    @State private var feelingToDelete: Feeling?
    
    // Load feelings from CoreData
    @State private var feelings: [Feeling] = []
    
    private let persistence = PersistenceController.shared
    
    var body: some View {
        NavigationView {
            VStack {
                // Search bar
                SearchBar(text: $searchText, placeholder: "Search feelings...")
                    .padding(.horizontal)
                
                // Feelings list
                List {
                    ForEach(filteredFeelings) { feeling in
                        FeelingRow(feeling: feeling)
                            .contentShape(Rectangle())
                            .onTapGesture {
                                selectedFeeling = feeling
                            }
                            .swipeActions(edge: .trailing) {
                                Button(role: .destructive) {
                                    feelingToDelete = feeling
                                    showAlert = true
                                } label: {
                                    Label("Delete", systemImage: "trash")
                                }
                            }
                    }
                }
                .listStyle(.plain)
                .alert("Delete Feeling", isPresented: $showAlert, actions: {
                    Button("Cancel", role: .cancel) {}
                    Button("Delete", role: .destructive) {
                        if let feeling = feelingToDelete {
                            deleteFeeling(feeling)
                        }
                    }
                }, message: {
                    Text("Are you sure you want to delete this feeling?")
                })
            }
            .navigationTitle("FeelFix")
            .toolbar {
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button(action: {
                        showingAddForm = true
                    }) {
                        Image(systemName: "plus")
                    }
                }
            }
            .onAppear {
                loadFeelings()
            }
            .sheet(isPresented: $showingAddForm) {
                FeelingFormView()
                    .onDisappear {
                        loadFeelings()
                    }
            }
            .sheet(item: $selectedFeeling) { feeling in
                FeelingFormView(feeling: feeling)
                    .onDisappear {
                        loadFeelings()
                    }
            }
        }
    }
    
    // Filtered feelings based on search text
    private var filteredFeelings: [Feeling] {
        if searchText.isEmpty {
            return feelings
        } else {
            return feelings.filter { feeling in
                feeling.feeling.localizedCaseInsensitiveContains(searchText) ||
                feeling.feelingCauseDesc.localizedCaseInsensitiveContains(searchText) ||
                feeling.involvedPeople.localizedCaseInsensitiveContains(searchText) ||
                feeling.status.localizedCaseInsensitiveContains(searchText) ||
                feeling.mainAffectedInstinct.localizedCaseInsensitiveContains(searchText)
            }
        }
    }
    
    // Load feelings from database
    private func loadFeelings() {
        feelings = persistence.getAllFeelings()
    }
    
    // Delete a feeling
    private func deleteFeeling(_ feeling: Feeling) {
        persistence.deleteFeeling(feeling)
        loadFeelings()
    }
}

// Custom search bar
struct SearchBar: View {
    @Binding var text: String
    var placeholder: String
    
    var body: some View {
        HStack {
            Image(systemName: "magnifyingglass")
                .foregroundColor(.gray)
            
            TextField(placeholder, text: $text)
                .foregroundColor(.primary)
            
            if !text.isEmpty {
                Button(action: {
                    text = ""
                }) {
                    Image(systemName: "xmark.circle.fill")
                        .foregroundColor(.gray)
                }
            }
        }
        .padding(8)
        .background(Color(.systemGray6))
        .cornerRadius(10)
    }
}

// Row for each feeling in the list
struct FeelingRow: View {
    let feeling: Feeling
    
    var body: some View {
        VStack(alignment: .leading, spacing: 5) {
            HStack {
                Text(feeling.feeling)
                    .font(.headline)
                    .lineLimit(1)
                Spacer()
                Text("\(feeling.ratingValue)")
                    .font(.subheadline)
                    .padding(5)
                    .background(getRatingColor(feeling.ratingValue))
                    .cornerRadius(5)
            }
            
            HStack {
                Text(feeling.feelingTime, formatter: dateFormatter)
                    .font(.caption)
                    .foregroundColor(.secondary)
                Spacer()
                Text(feeling.status)
                    .font(.caption)
                    .foregroundColor(.secondary)
                    .padding(.horizontal, 8)
                    .padding(.vertical, 3)
                    .background(Color(.systemGray6))
                    .cornerRadius(5)
            }
            
            if !feeling.feelingCauseDesc.isEmpty {
                Text(feeling.feelingCauseDesc)
                    .font(.caption)
                    .foregroundColor(.secondary)
                    .lineLimit(1)
            }
        }
        .padding(.vertical, 5)
    }
    
    // Get color based on rating
    private func getRatingColor(_ rating: Int) -> Color {
        if rating < -5 {
            return Color.red.opacity(0.7)
        } else if rating < 0 {
            return Color.orange.opacity(0.7)
        } else if rating == 0 {
            return Color.gray.opacity(0.7)
        } else if rating < 6 {
            return Color.green.opacity(0.7)
        } else {
            return Color.blue.opacity(0.7)
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

// Preview
struct FeelingsListView_Previews: PreviewProvider {
    static var previews: some View {
        FeelingsListView()
            .environment(\.managedObjectContext, PersistenceController.preview.container.viewContext)
    }
}
