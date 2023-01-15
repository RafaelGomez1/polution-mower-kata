package com.codely.report.application.exist

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import com.codely.report.domain.ReportRepository
import com.codely.shared.robot.domain.RobotId

context(ReportRepository)
suspend fun <T> guardReportExists(id: RobotId, onResourceAlreadyExists: () -> T, onUnexpectedError: (cause: Throwable) -> T): Either<T, Unit> =
    existBy(id)
        .mapLeft { error -> onUnexpectedError(error) }
        .flatMap { exists ->
            if (exists) {
                onResourceAlreadyExists().left()
            } else {
                Unit.right()
            }
        }
