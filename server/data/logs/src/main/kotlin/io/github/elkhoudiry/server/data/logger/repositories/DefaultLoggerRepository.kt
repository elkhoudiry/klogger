package io.github.elkhoudiry.server.data.logger.repositories

import io.github.elkhoudiry.server.data.logger.models.Log

class DefaultLoggerRepository : LoggerRepository {
    private val list = ArrayList<Log>()

    override fun getAll(): List<Log> {
        return list
    }

    override fun getById(id: String): Log? {
        return list.find { it.id == id }
    }

    override suspend fun insert(log: Log) {
        list.add(log)
    }

    override suspend fun deleteById(id: String): Boolean {
        return list.removeIf { it.id == id }
    }
}