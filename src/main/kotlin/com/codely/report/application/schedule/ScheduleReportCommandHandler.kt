package com.codely.report.application.schedule

import arrow.core.Either
import com.codely.report.application.ScheduleReportCommand
import com.codely.report.domain.ReportScheduler
import com.codely.report.domain.ScheduleReportError
import com.codely.shared.robot.domain.RobotId

context(ReportScheduler)
suspend fun handle(command: ScheduleReportCommand): Either<ScheduleReportError, Unit> =
    scheduleReport(id = RobotId(command.id))
