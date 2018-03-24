package oktomeme.db

import oktomeme.ServiceCategory
import org.jetbrains.exposed.sql.Table

object ServiceProviders : Table("service_providers") {
    val id = integer("id").autoIncrement().primaryKey()
    val userId = integer("user_id").index(isUnique = false)
    val description = varchar("description", length = 1000)
    val category = enumerationByName("category", 20, ServiceCategory::class.java)
}

object ServiceRequests : Table("service_requests") {
    val id = integer("id").autoIncrement().primaryKey()
    val userId = integer("user_id").index(isUnique = false)
    val description = varchar("description", length = 1000)
    val category = enumerationByName("category", 20, ServiceCategory::class.java)
}

object Users : Table("users") {
    val id = integer("id").autoIncrement().primaryKey()
    val name = varchar("name", length = 100)
    val phone = varchar("phone", length = 15)
}
