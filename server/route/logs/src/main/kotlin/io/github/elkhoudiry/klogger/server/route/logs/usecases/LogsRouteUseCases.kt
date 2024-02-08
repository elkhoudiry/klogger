package io.github.elkhoudiry.klogger.server.route.logs.usecases

import io.github.elkhoudiry.klogger.server.data.logs.repositories.LoggerRepository

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