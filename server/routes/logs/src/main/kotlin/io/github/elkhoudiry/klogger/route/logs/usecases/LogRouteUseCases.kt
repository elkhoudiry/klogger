package io.github.elkhoudiry.klogger.route.logs.usecases

import io.github.elkhoudiry.server.data.logger.repositories.LoggerRepository

internal class LogRouteUseCases(
    logging: LoggerRepository
) {
    val insertLog = InsertLogUseCase(
        logging = logging
    )
}