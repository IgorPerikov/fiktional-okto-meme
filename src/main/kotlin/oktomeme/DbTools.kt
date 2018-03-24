package oktomeme

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import java.sql.SQLException
import java.util.*

object DbTools {
    lateinit var dataSource: HikariDataSource

    val log = LoggerFactory.getLogger(this::class.java)

    fun init() {
        log.info("Start db init script")
        setupConnectionPool()
        createTables()
        insertData()
        log.info("Db init completed")
    }

    private fun setupConnectionPool() {
        val hikariPropertiesStream = DbTools::class.java.classLoader.getResourceAsStream("hikari.properties")
        val hikariProperties = Properties().apply { this.load(hikariPropertiesStream) }
        val config = HikariConfig(hikariProperties)
        dataSource = HikariDataSource(config)
        Database.connect(dataSource)
    }

    private fun createTables() {
        val statement = dataSource.connection.createStatement()
        val query = DbTools::class.java.classLoader.getResource("schema.sql").readText()
        statement.execute(query)
    }

    private fun insertData() {
        var skip = false
        transaction {
            if (Users.selectAll().count() > 0) log.info("Db already contains some data"); skip = true
        }

        if (skip) return

        transaction {
            val igorId = Users.insert {
                it[name] = "Igor"
                it[phone] = "+7912"
            } get Users.id ?: throw SQLException()
            val johnId = Users.insert {
                it[name] = "John"
                it[phone] = "+1911"
            } get Users.id ?: throw SQLException()
            val jamesId = Users.insert {
                it[name] = "James"
                it[phone] = "+2900"
            } get Users.id ?: throw SQLException()

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
