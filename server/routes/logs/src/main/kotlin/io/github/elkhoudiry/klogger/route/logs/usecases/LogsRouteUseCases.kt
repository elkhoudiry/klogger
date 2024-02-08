package io.github.elkhoudiry.klogger.route.logs.usecases

import io.github.elkhoudiry.server.data.logger.repositories.LoggerRepository

internal class LogsRouteUseCases(
    logging: LoggerRepository
) {
    val insertLog = InsertLogUseCase(
        logging = logging
    )

    val getById = GetByIdUseCase(
        logging = logging
    )

    val getAll = GetAllUseCase(
        logging = logging
    )

    val deleteById = DeleteByIdUseCase(
        logging = logging
    )
}