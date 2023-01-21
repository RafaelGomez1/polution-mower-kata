package com.codely.report.application.find

import arrow.core.Either
import com.codely.report.domain.Report
import com.codely.report.domain.ReportRepository
import com.codely.shared.robot.domain.RobotId

context(ReportRepository)
suspend fun <T> findReportBy(robotId: RobotId, onResourceNotFound: () -> T, onUnexpectedError: (cause: Throwable) -> T): Either<T, Report> =
    findBy(robotId)
        .mapLeft { error ->
            when (error) {
                is NoSuchElementException -> onResourceNotFound()
                else -> onUnexpectedError(error)
            }
        }
