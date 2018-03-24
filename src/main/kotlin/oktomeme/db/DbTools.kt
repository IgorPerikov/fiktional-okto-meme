package oktomeme.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.slf4j.LoggerFactory

object DbTools {
    lateinit var dataSource: HikariDataSource

    private const val DEFAULT_JDBC_URL: String =
        "jdbc:postgresql://localhost:5432/postgres?user=root&password=root&ssl=false"
    private val log = LoggerFactory.getLogger(this::class.java)

    fun init() {
        log.info("Start db init script")
        setupConnectionPool()
        launchMigrations()
        log.info("Db init completed")
    }

    private fun setupConnectionPool() {
        val config = HikariConfig()
        config.jdbcUrl = System.getenv("JDBC_URL") ?: DEFAULT_JDBC_URL
        config.maximumPoolSize = 10

        dataSource = HikariDataSource(config)
        Database.connect(dataSource)
    }

    private fun launchMigrations() {
        val flyway = Flyway()
        flyway.dataSource = dataSource
        flyway.migrate()
    }
}
