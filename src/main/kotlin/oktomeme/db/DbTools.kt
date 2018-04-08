package oktomeme.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import oktomeme.ServiceCategory
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SchemaUtils.withDataBaseLock
import org.jetbrains.exposed.sql.transactions.transaction
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
        transaction {
            withDataBaseLock {
                SchemaUtils.createMissingTablesAndColumns(
                    UsersTable,
                    ServiceRequestsTable,
                    ServiceProvidersTable
                )
                if (UserEntity.all().count() == 0) {
                    val igor: UserEntity = UserEntity.new {
                        name = "Igor"
                        phone = "+7912"
                    }
                    val john: UserEntity = UserEntity.new {
                        name = "John"
                        phone = "+1911"
                    }
                    val james: UserEntity = UserEntity.new {
                        name = "James"
                        phone = "+2900"
                    }

                    ServiceProviderEntity.new {
                        user = igor
                        description = "I can fix your PC"
                        category = ServiceCategory.HIGHTECH
                    }
                    ServiceProviderEntity.new {
                        user = igor
                        description = "I can fix your car"
                        category = ServiceCategory.TECH
                    }
                    ServiceProviderEntity.new {
                        user = igor
                        description = "I can fix your fridge"
                        category = ServiceCategory.TECH
                    }
                    ServiceProviderEntity.new {
                        user = john
                        description = "I can teach you how to drive"
                        category = ServiceCategory.LIFESKILLS
                    }

                    ServiceRequestEntity.new {
                        user = igor
                        description = "I want to study icelandic language"
                        category = ServiceCategory.LIFESKILLS
                    }
                    ServiceRequestEntity.new {
                        user = james
                        description = "I need my washing machine to be fixed"
                        category = ServiceCategory.TECH
                    }
                    ServiceRequestEntity.new {
                        user = james
                        description = "I need someone to paint my walls"
                        category = ServiceCategory.LIFESKILLS
                    }
                    ServiceRequestEntity.new {
                        user = james
                        description = "I am looking for a football coach for my children"
                        category = ServiceCategory.LIFESKILLS
                    }
                }
            }
        }
    }
}
