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
import oktomeme.db.User
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.event.Level
import java.net.InetAddress

private val hostname = InetAddress.getLocalHost().hostName

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
                val provider = ServiceProvider.all().shuffled().first()
                val author = User.findById(provider.userId)
                sb.append("${provider.description}, call ${author?.phone} and ask ${author?.name} to find out more")
            }
            call.respondText("\"$hostname: $sb\"")
        }
        get("/requests") {
            val sb = StringBuilder()
            transaction {
                val request = ServiceRequest.all().shuffled().first()
                val author = User.findById(request.userId)
                sb.append("${request.description}, call ${author?.phone} and ask ${author?.name} to find out more")
            }
            call.respondText("\"$hostname: $sb\"")
        }
    }
}
