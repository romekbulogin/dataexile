package ru.dataexile.datagateservice.service

import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.dataexile.datagateservice.repository.InstanceRepository
import java.sql.Connection
import java.sql.DriverManager
import kotlin.concurrent.thread
import kotlin.random.Random

@Service
class ExileService(
    private val instanceRepository: InstanceRepository
) {
    private val logger = KotlinLogging.logger { }

    //Testing multithread insert into table
    fun exile() = try {
        val startInstance = instanceRepository.findByTitle("start_db").block()
        for (number in 0..5) {
            thread(start = true) {
                DriverManager.getConnection(startInstance?.url, startInstance?.username, startInstance?.password)
                    .use { connection ->
                        logger.info("Thread #$number: execute")
                        connection.transactionIsolation = Connection.TRANSACTION_SERIALIZABLE
                        for (i in 0..10000000) {
                            connection.prepareStatement("insert into temp_table (title,name,age) values (?,?,?)")
                                .use {
                                    it.setString(1, generateString(Random.nextInt(5, 25)))
                                    it.setString(2, generateString(Random.nextInt(10, 15)))
                                    it.setInt(3, Random.nextInt(0, 100))
                                    it.executeUpdate()
                                }
                        }
                    }

            }
        }

        logger.info("")
        //val endInstance = instanceRepository.findByTitle("end_db")

    } catch (ex: Exception) {
        logger.error(ex.message)
    }

    fun generateString(length: Int): String {
        val text = CharArray(length)
        val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        for (i in 0 until length) {
            text[i] = characters[Random.nextInt(characters.length)]
        }
        return String(text)
    }
}