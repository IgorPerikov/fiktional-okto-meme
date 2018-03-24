package oktomeme

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import oktomeme.db.DbTools
import oktomeme.db.ServiceProvider
import oktomeme.db.ServiceRequest
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.event.Level

fun Application.main() {
    DbTools.init()
    install(DefaultHeaders)
    install(CallLogging) {
        level = Level.INFO
    }
    install(Routing) {
        get("/providers") {
            val sb = StringBuilder()
            transaction {
                val first = ServiceProvider.all().shuffled().first()
                sb.append(first.toString())
            }
            call.respondText('"' + sb.toString() + '"')
        }
        get("/requests") {
            val sb = StringBuilder()
            transaction {
                val first = ServiceRequest.all().shuffled().first()
                sb.append(first.toString())
            }
            call.respondText('"' + sb.toString() + '"')
        }
    }
}
