package oktomeme.db

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class ServiceProviderEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ServiceProviderEntity>(ServiceProvidersTable)

    var user by UserEntity referencedOn ServiceProvidersTable.user
    var description by ServiceProvidersTable.description
    var category by ServiceProvidersTable.category
}

class ServiceRequestEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ServiceRequestEntity>(ServiceRequestsTable)

    var user by UserEntity referencedOn ServiceRequestsTable.user
    var description by ServiceRequestsTable.description
    var category by ServiceRequestsTable.category
}

class UserEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserEntity>(UsersTable)

    var name by UsersTable.name
    var phone by UsersTable.phone
}
