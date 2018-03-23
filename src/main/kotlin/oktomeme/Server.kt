package oktomeme

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import com.zaxxer.hikari.HikariDataSource
import com.zaxxer.hikari.HikariConfig
import java.util.*


fun Application.main() {
    val hikariPropertiesStream = Application::class.java.classLoader.getResourceAsStream("hikari.properties")
    val hikariProperties = Properties().apply { this.load(hikariPropertiesStream) }
    val config = HikariConfig(hikariProperties)
    val datasource = HikariDataSource(config)
    Database.connect(datasource)

//    transaction {
//        logger.addLogger(StdOutSqlLogger)
//        val username = ServiceProviders.insertAndGetId {
//            it[name] = "Igor"
//            it[phone] = "88005553555"
//            it[description] = "I can repair you computer"
//        } get ServiceProviders.id
//        println(username)
//    }

    install(DefaultHeaders)
    install(CallLogging)
    install(Routing) {
        get("/") {
            call.respondText("Well done so far", ContentType.Text.Html)
        }
    }
}
