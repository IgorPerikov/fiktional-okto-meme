package oktomeme.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.slf4j.LoggerFactory
import java.util.*

object DbTools {
    lateinit var dataSource: HikariDataSource

    val log = LoggerFactory.getLogger(this::class.java)

    fun init() {
        log.info("Start db init script")
        setupConnectionPool()
        launchMigrations()
        log.info("Db init completed")
    }

    private fun setupConnectionPool() {
        val hikariPropertiesStream = DbTools::class.java.classLoader.getResourceAsStream("hikari.properties")
        val hikariProperties = Properties().apply { this.load(hikariPropertiesStream) }
        val config = HikariConfig(hikariProperties)
        dataSource = HikariDataSource(config)
        Database.connect(dataSource)
    }

    private fun launchMigrations() {
        val flyway = Flyway()
        flyway.dataSource = dataSource
        flyway.migrate()
    }
}
