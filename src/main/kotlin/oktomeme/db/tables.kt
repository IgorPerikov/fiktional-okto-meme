package oktomeme.db

import oktomeme.ServiceCategory
import org.jetbrains.exposed.dao.IntIdTable

object ServiceProvidersTable : IntIdTable("service_providers") {
    val user = reference("user", UsersTable)
    val description = varchar("description", length = 1000)
    val category = enumerationByName("category", 20, ServiceCategory::class.java)
}

object ServiceRequestsTable : IntIdTable("service_requests") {
    val user = reference("user", UsersTable)
    val description = varchar("description", length = 1000)
    val category = enumerationByName("category", 20, ServiceCategory::class.java)
}

object UsersTable : IntIdTable("users") {
    val name = varchar("name", length = 100)
    val phone = varchar("phone", length = 15)
}
