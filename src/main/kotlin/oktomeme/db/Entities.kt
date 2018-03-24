package oktomeme.db

import oktomeme.ServiceCategory
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.Table

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

object Users : Table("users") {
    val id = integer("id").autoIncrement().primaryKey()
    val name = varchar("name", length = 100)
    val phone = varchar("phone", length = 15)
}

class ServiceProvider(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ServiceProvider>(ServiceProviders)

    var userId by ServiceProviders.userId
    var description by ServiceProviders.description
    var category by ServiceProviders.category

    override fun toString(): String {
        return "$userId; $description; $category"
    }
}

class ServiceRequest(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ServiceRequest>(ServiceRequests)

    var userId by ServiceRequests.userId
    var description by ServiceRequests.description
    var category by ServiceRequests.category

    override fun toString(): String {
        return "$userId; $description; $category"
    }
}
