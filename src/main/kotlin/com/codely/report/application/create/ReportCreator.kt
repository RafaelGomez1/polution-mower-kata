package com.codely.report.application.create

import arrow.core.Either
import arrow.core.flatMap
import com.codely.report.application.exist.guardReportExists
import com.codely.report.application.save.saveOrElse
import com.codely.report.domain.CreateReportError
import com.codely.report.domain.Report
import com.codely.report.domain.ReportId
import com.codely.report.domain.ReportRepository
import com.codely.shared.robot.domain.Location
import com.codely.shared.robot.domain.RobotId

context(ReportRepository)
suspend fun createReport(id: ReportId, robotId: RobotId, location: Location?): Either<CreateReportError, Report> =
    guardReportExists(
        id = robotId,
        onResourceAlreadyExists = { CreateReportError.ReportAlreadyExists },
        onUnexpectedError = { error -> CreateReportError.Unknown(error) }
    )
        .map { Report.create(robotId, id, location) }
        .flatMap { report -> report.saveOrElse { error -> CreateReportError.Unknown(error) } }
