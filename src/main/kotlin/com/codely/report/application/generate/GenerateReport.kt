package com.codely.report.application.generate

import arrow.core.Either
import arrow.core.flatMap
import com.codely.report.application.find.findReportBy
import com.codely.report.domain.GenerateReportError
import com.codely.report.domain.ReportGenerator
import com.codely.report.domain.ReportRepository
import com.codely.report.domain.generateOrElse
import com.codely.shared.robot.domain.RobotId

context(ReportRepository, ReportGenerator)
suspend fun generateReport(robotId: RobotId): Either<GenerateReportError, Unit> =
    findReportBy(
        robotId = robotId,
        onResourceNotFound = { GenerateReportError.ReportDoesNotExist },
        onUnexpectedError = { GenerateReportError.Unknown(it) }
    ).flatMap { report -> generateOrElse(report = report, onError = { GenerateReportError.Unknown(it) }) }
