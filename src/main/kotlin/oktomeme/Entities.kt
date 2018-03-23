package oktomeme

import org.jetbrains.exposed.sql.Table

object ServiceProviders : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val userId = integer("id").index(isUnique = false)
    val description = varchar("description", length = 1000)
    val category = enumerationByName("type", 20, ServiceCategory::class.java)
}

object ServiceRequests : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val userId = integer("id").index(isUnique = false)
    val description = varchar("description", length = 1000)
    val type = enumerationByName("type", 20, ServiceCategory::class.java)
}

object Users : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val name = varchar("name", length = 100)
    val phone = varchar("phone", length = 15)
}
