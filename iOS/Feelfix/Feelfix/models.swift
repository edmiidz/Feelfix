import Foundation
import CoreData

// Main Feeling entity model
struct Feeling: Identifiable {
    var id: UUID = UUID()
    var feeling: String
    var ratingValue: Int
    var feelingTime: Date
    var feelingCauseDesc: String
    var mainAffectedInstinct: String
    var status: String
    var involvedPeople: String
    var wouldHaveBeenBetter: String
    var wouldHaveBeenWorse: String
    var created: Date
    var updatedLast: Date
    var latitude: Double
    var longitude: Double
    
    // Create a sample feeling for previews
    static var sample: Feeling {
        Feeling(
            feeling: "Happiness, Joy",
            ratingValue: 7,
            feelingTime: Date(),
            feelingCauseDesc: "Had a great day at work",
            mainAffectedInstinct: "social",
            status: "New",
            involvedPeople: "Coworkers",
            wouldHaveBeenBetter: "If I had shared more with the team",
            wouldHaveBeenWorse: "If I had stayed silent",
            created: Date(),
            updatedLast: Date(),
            latitude: 37.7749,
            longitude: -122.4194
        )
    }
}

// Define CoreData model extension
extension Feeling {
    // Convert CoreData managed object to our Feeling model
    static func fromManagedObject(_ object: FeelingEntity) -> Feeling {
        return Feeling(
            id: object.id ?? UUID(),
            feeling: object.feeling ?? "",
            ratingValue: Int(object.ratingValue),
            feelingTime: object.feelingTime ?? Date(),
            feelingCauseDesc: object.feelingCauseDesc ?? "",
            mainAffectedInstinct: object.mainAffectedInstinct ?? "",
            status: object.status ?? "New",
            involvedPeople: object.involvedPeople ?? "",
            wouldHaveBeenBetter: object.wouldHaveBeenBetter ?? "",
            wouldHaveBeenWorse: object.wouldHaveBeenWorse ?? "",
            created: object.created ?? Date(),
            updatedLast: object.updatedLast ?? Date(),
            latitude: object.latitude,
            longitude: object.longitude
        )
    }
    
    // Create or update a managed object from this Feeling
    func toManagedObject(context: NSManagedObjectContext) -> FeelingEntity {
        let fetchRequest: NSFetchRequest<FeelingEntity> = FeelingEntity.fetchRequest() as! NSFetchRequest<FeelingEntity>
        fetchRequest.predicate = NSPredicate(format: "id == %@", id as CVarArg)
        
        let existingFeelings = try? context.fetch(fetchRequest)
        let entity = existingFeelings?.first ?? FeelingEntity(context: context)
        
        entity.id = id
        entity.feeling = feeling
        entity.ratingValue = Int16(ratingValue)
        entity.feelingTime = feelingTime
        entity.feelingCauseDesc = feelingCauseDesc
        entity.mainAffectedInstinct = mainAffectedInstinct
        entity.status = status
        entity.involvedPeople = involvedPeople
        entity.wouldHaveBeenBetter = wouldHaveBeenBetter
        entity.wouldHaveBeenWorse = wouldHaveBeenWorse
        entity.created = created
        entity.updatedLast = Date() // Always update the lastUpdated time
        entity.latitude = latitude
        entity.longitude = longitude
        
        return entity
    }
}

// Enum for the status options
enum FeelingStatus: String, CaseIterable {
    case new = "New"
    case pending = "Pending"
    case shared = "Shared"
    case resolved = "Resolved"
    case other = "Other"
}

// Enum for the instinct types
enum InstinctType: String, CaseIterable {
    case sex = "Sex"
    case security = "Security"
    case social = "Social"
}
