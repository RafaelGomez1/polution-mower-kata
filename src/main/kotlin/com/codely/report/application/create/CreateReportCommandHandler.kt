package com.codely.report.application.create

import arrow.core.Either
import com.codely.report.application.CreateReportCommand
import com.codely.report.domain.CreateReportError
import com.codely.report.domain.Report
import com.codely.report.domain.ReportId
import com.codely.report.domain.ReportRepository
import com.codely.shared.robot.domain.Location
import com.codely.shared.robot.domain.RobotId
import java.util.*

context(ReportRepository)
suspend fun handle(command: CreateReportCommand): Either<CreateReportError, Report> =
    createReport(
        id = ReportId(command.id),
        robotId = RobotId(command.robotId),
        location = command.location?.let { Location(it) }
    )
