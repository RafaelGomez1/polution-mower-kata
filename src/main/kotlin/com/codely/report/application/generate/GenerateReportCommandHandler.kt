package com.codely.report.application.generate

import arrow.core.Either
import com.codely.report.application.GenerateReportCommand
import com.codely.report.domain.GenerateReportError
import com.codely.report.domain.ReportGenerator
import com.codely.report.domain.ReportRepository
import com.codely.shared.robot.domain.RobotId

context(ReportRepository, ReportGenerator)
suspend fun handle(command: GenerateReportCommand): Either<GenerateReportError, Unit> =
    generateReport(robotId = RobotId(command.robotId))
