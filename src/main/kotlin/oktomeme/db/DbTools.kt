package oktomeme.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import oktomeme.ServiceCategory
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SchemaUtils.withDataBaseLock
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
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
                SchemaUtils.createMissingTablesAndColumns(Users, ServiceRequests, ServiceProviders)
                if (User.all().count() == 0) {
                    val igorId = Users.insertAndGetId {
                        it[name] = "Igor"
                        it[phone] = "+7912"
                    }.value
                    val johnId = Users.insertAndGetId {
                        it[name] = "John"
                        it[phone] = "+1911"
                    }.value
                    val jamesId = Users.insertAndGetId {
                        it[name] = "James"
                        it[phone] = "+2900"
                    }.value

                    ServiceProviders.insert {
                        it[userId] = igorId
                        it[description] = "I can fix your PC"
                        it[category] = ServiceCategory.HIGHTECH
                    }
                    ServiceProviders.insert {
                        it[userId] = igorId
                        it[description] = "I can fix your car"
                        it[category] = ServiceCategory.TECH
                    }
                    ServiceProviders.insert {
                        it[userId] = igorId
                        it[description] = "I can fix your fridge"
                        it[category] = ServiceCategory.TECH
                    }
                    ServiceProviders.insert {
                        it[userId] = johnId
                        it[description] = "I can teach you how to drive"
                        it[category] = ServiceCategory.LIFESKILLS
                    }

                    ServiceRequests.insert {
                        it[userId] = igorId
                        it[description] = "I want to study icelandic language"
                        it[category] = ServiceCategory.LIFESKILLS
                    }
                    ServiceRequests.insert {
                        it[userId] = jamesId
                        it[description] = "I need my washing machine to be fixed"
                        it[category] = ServiceCategory.TECH
                    }
                    ServiceRequests.insert {
                        it[userId] = jamesId
                        it[description] = "I need someone to paint my walls"
                        it[category] = ServiceCategory.LIFESKILLS
                    }
                    ServiceRequests.insert {
                        it[userId] = jamesId
                        it[description] = "I am looking for a football coach for my children"
                        it[category] = ServiceCategory.LIFESKILLS
                    }
                }
            }
        }
    }
}
