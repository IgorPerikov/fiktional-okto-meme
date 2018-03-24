package oktomeme.db

import oktomeme.ServiceCategory
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object ServiceProviders : IntIdTable("service_providers") {
    val userId = integer("user_id").index(isUnique = false)
    val description = varchar("description", length = 1000)
    val category = enumerationByName("category", 20, ServiceCategory::class.java)
}

object ServiceRequests : IntIdTable("service_requests") {
    val userId = integer("user_id").index(isUnique = false)
    val description = varchar("description", length = 1000)
    val category = enumerationByName("category", 20, ServiceCategory::class.java)
}

object Users : IntIdTable("users") {
    val name = varchar("name", length = 100)
    val phone = varchar("phone", length = 15)
}

class ServiceProvider(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ServiceProvider>(ServiceProviders)

    var userId by ServiceProviders.userId
    var description by ServiceProviders.description
    var category by ServiceProviders.category
}

class ServiceRequest(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ServiceRequest>(ServiceRequests)

    var userId by ServiceRequests.userId
    var description by ServiceRequests.description
    var category by ServiceRequests.category
}

class User(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<User>(Users)

    var name by Users.name
    var phone by Users.phone
}
