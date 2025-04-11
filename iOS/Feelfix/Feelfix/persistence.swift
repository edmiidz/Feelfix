import CoreData
import SwiftUI
import CoreData

// Manages Core Data operations
class PersistenceController {
    // Shared singleton instance
    static let shared = PersistenceController()
    
    // Preview instance with sample data for SwiftUI Previews
    static var preview: PersistenceController = {
        let controller = PersistenceController(inMemory: true)
        
        // Create 10 example feelings
        let viewContext = controller.container.viewContext
        for i in 0..<10 {
            let feeling = FeelingEntity(context: viewContext)
            feeling.id = UUID()
            feeling.feeling = "Sample Feeling \(i+1)"
            feeling.ratingValue = Int16.random(in: -10...10)
            feeling.feelingTime = Date()
            feeling.feelingCauseDesc = "Description \(i+1)"
            feeling.mainAffectedInstinct = ["sex", "security", "social"].randomElement()!
            feeling.status = ["New", "Pending", "Shared", "Resolved"].randomElement()!
            feeling.involvedPeople = "Person \(i+1)"
            feeling.wouldHaveBeenBetter = "Better if \(i+1)"
            feeling.wouldHaveBeenWorse = "Worse if \(i+1)"
            feeling.created = Date()
            feeling.updatedLast = Date()
            feeling.latitude = Double.random(in: 35...45)
            feeling.longitude = Double.random(in: -125...(-115))
        }
        
        do {
            try viewContext.save()
        } catch {
            let nsError = error as NSError
            fatalError("Failed to create preview data: \(nsError)")
        }
        
        return controller
    }()
    
    // Core Data container
    let container: NSPersistentContainer
    
    // Initialize with option for in-memory store (for previews)
    init(inMemory: Bool = false) {
        container = NSPersistentContainer(name: "FeelFix")
        
        if inMemory {
            container.persistentStoreDescriptions.first?.url = URL(fileURLWithPath: "/dev/null")
        }
        
        container.loadPersistentStores { description, error in
            if let error = error as NSError? {
                // In a real app, you would handle this properly
                fatalError("Failed to load Core Data: \(error), \(error.userInfo)")
            }
        }
        
        container.viewContext.automaticallyMergesChangesFromParent = true
        container.viewContext.mergePolicy = NSMergeByPropertyObjectTrumpMergePolicy
    }
    
    // Save the context if there are changes
    func save() {
        let context = container.viewContext
        if context.hasChanges {
            do {
                try context.save()
            } catch {
                let nsError = error as NSError
                print("Error saving context: \(nsError), \(nsError.userInfo)")
            }
        }
    }
    
    // CRUD Operations
    
    // Create a new Feeling
    func createFeeling(_ feeling: Feeling) {
        let _ = feeling.toManagedObject(context: container.viewContext)
        save()
    }
    
    // Get all feelings
    func getAllFeelings() -> [Feeling] {
        let fetchRequest: NSFetchRequest<FeelingEntity> = FeelingEntity.fetchRequest() as! NSFetchRequest<FeelingEntity>
        fetchRequest.sortDescriptors = [NSSortDescriptor(keyPath: \FeelingEntity.feelingTime, ascending: false)]
        
        do {
            let entities = try container.viewContext.fetch(fetchRequest)
            return entities.map { Feeling.fromManagedObject($0) }
        } catch {
            print("Error fetching feelings: \(error)")
            return []
        }
    }
    
    // Search feelings by query string
    func searchFeelings(query: String) -> [Feeling] {
        let fetchRequest: NSFetchRequest<FeelingEntity> = FeelingEntity.fetchRequest() as! NSFetchRequest<FeelingEntity>
        
        // Only apply filter if the query has at least 2 characters
        if query.count > 2 {
            fetchRequest.predicate = NSPredicate(
                format: "feeling CONTAINS[cd] %@ OR feelingCauseDesc CONTAINS[cd] %@ OR involvedPeople CONTAINS[cd] %@ OR status CONTAINS[cd] %@ OR mainAffectedInstinct CONTAINS[cd] %@",
                query, query, query, query, query
            )
        }
        
        fetchRequest.sortDescriptors = [NSSortDescriptor(keyPath: \FeelingEntity.feelingTime, ascending: false)]
        
        do {
            let entities = try container.viewContext.fetch(fetchRequest)
            return entities.map { Feeling.fromManagedObject($0) }
        } catch {
            print("Error searching feelings: \(error)")
            return []
        }
    }
    
    // Update a feeling
    func updateFeeling(_ feeling: Feeling) {
        let _ = feeling.toManagedObject(context: container.viewContext)
        save()
    }
    
    // Delete a feeling
    func deleteFeeling(_ feeling: Feeling) {
        let fetchRequest: NSFetchRequest<FeelingEntity> = FeelingEntity.fetchRequest() as! NSFetchRequest<FeelingEntity>
        fetchRequest.predicate = NSPredicate(format: "id == %@", feeling.id as CVarArg)
        
        do {
            let entities = try container.viewContext.fetch(fetchRequest)
            if let entity = entities.first {
                container.viewContext.delete(entity)
                save()
            }
        } catch {
            print("Error deleting feeling: \(error)")
        }
    }
}
