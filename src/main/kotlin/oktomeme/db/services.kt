package oktomeme.db

import oktomeme.Provider
import oktomeme.Request
import org.jetbrains.exposed.sql.transactions.transaction

object ProvidersService {
    fun getProviderById(id: Int): Provider? {
        return transaction {
            val entity = ServiceProviderEntity.findById(id)
            if (entity == null) {
                null
            } else {
                Provider(entity.description, entity.user.phone, entity.user.name)
            }
        }
    }
}

object RequestsService {
    fun getRequestById(id: Int): Request? {
        return transaction {
            val entity = ServiceRequestEntity.findById(id)
            if (entity == null) {
                null
            } else {
                Request(entity.description, entity.user.phone, entity.user.name)
            }
        }
    }
}
