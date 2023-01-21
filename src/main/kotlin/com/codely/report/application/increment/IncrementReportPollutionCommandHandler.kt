package com.codely.report.application.increment

import arrow.core.Either
import com.codely.report.application.IncrementReportPollutionCommand
import com.codely.report.domain.IncrementReportPollutionError
import com.codely.report.domain.PollutionQuantity
import com.codely.report.domain.Report
import com.codely.report.domain.ReportRepository
import com.codely.shared.robot.domain.RobotId

context(ReportRepository)
suspend fun handle(command: IncrementReportPollutionCommand): Either<IncrementReportPollutionError, Report> =
    incrementReportPollution(robotId = RobotId(command.robotId), pollution = PollutionQuantity(command.pollution))
