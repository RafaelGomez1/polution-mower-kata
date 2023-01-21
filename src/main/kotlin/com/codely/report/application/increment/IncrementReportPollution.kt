package com.codely.report.application.increment

import arrow.core.Either
import arrow.core.flatMap
import com.codely.report.application.find.findReportBy
import com.codely.report.application.save.saveOrElse
import com.codely.report.domain.IncrementReportPollutionError
import com.codely.report.domain.PollutionQuantity
import com.codely.report.domain.Report
import com.codely.report.domain.ReportRepository
import com.codely.shared.robot.domain.RobotId

context(ReportRepository)
suspend fun incrementReportPollution(robotId: RobotId, pollution: PollutionQuantity): Either<IncrementReportPollutionError, Report> =
    findReportBy(
        robotId = robotId,
        onResourceNotFound = { IncrementReportPollutionError.ReportDoesNotExist },
        onUnexpectedError = { IncrementReportPollutionError.Unknown(it) }
    )
        .map { report -> report.addPollutionReading(pollution) }
        .flatMap { report -> report.saveOrElse { IncrementReportPollutionError.Unknown(it) } }
