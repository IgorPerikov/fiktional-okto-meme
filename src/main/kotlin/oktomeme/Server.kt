@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package oktomeme

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.locations.Location
import io.ktor.locations.Locations
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Routing
import oktomeme.db.DbTools
import oktomeme.db.ProvidersService
import oktomeme.db.RequestsService
import org.slf4j.event.Level
import java.net.InetAddress

private val hostname = InetAddress.getLocalHost().hostName

@Location("/providers/{id}")
data class ProviderById(val id: Int)

@Location("/requests/{id}")
data class RequestsById(val id: Int)

fun Application.main() {
    DbTools.init()
    install(DefaultHeaders) {
        header("X-Host-Name", hostname)
    }
    install(Locations)
    install(CallLogging) {
        level = Level.INFO
    }
    install(ContentNegotiation) {
        jackson {

        }
    }
    install(StatusPages) {
        exception<Throwable> { _ ->
            call.respond(HttpStatusCode.NotFound)
        }
    }
    install(Routing) {
        get<ProviderById> { providerRequest ->
            val provider = ProvidersService.getProviderById(providerRequest.id)
            call.respond(provider)
        }
        get<RequestsById> { requestRequest ->
            val request = RequestsService.getRequestById(requestRequest.id)
            call.respond(request)
        }
    }
}
