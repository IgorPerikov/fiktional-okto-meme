package oktomeme.db

import oktomeme.NotFoundException
import oktomeme.Provider
import oktomeme.Request
import org.jetbrains.exposed.sql.transactions.transaction

object ProvidersService {
    fun getProviderById(id: Int): Provider {
        return transaction {
            val entity = ServiceProviderEntity.findById(id) ?: throw NotFoundException()
            Provider(entity.description, entity.user.phone, entity.user.name)
        }
    }

    fun getProviders(perPage: Int, page: Int): List<Provider> {
        return transaction {
            ServiceProviderEntity.all()
                .limit(perPage, page * perPage)
                .map { Provider(it.description, it.user.phone, it.user.name) }
        }
    }
}

object RequestsService {
    fun getRequestById(id: Int): Request {
        return transaction {
            val entity = ServiceRequestEntity.findById(id) ?: throw NotFoundException()
            Request(entity.description, entity.user.phone, entity.user.name)
        }
    }

    fun getRequests(perPage: Int, page: Int): List<Request> {
        return transaction {
            ServiceRequestEntity.all()
                .limit(perPage, page * perPage)
                .map { Request(it.description, it.user.phone, it.user.name) }
        }
    }
}
