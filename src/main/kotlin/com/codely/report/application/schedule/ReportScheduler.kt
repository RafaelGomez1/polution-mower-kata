package com.codely.report.application.schedule

import arrow.core.Either
import com.codely.report.domain.ReportScheduler
import com.codely.report.domain.ScheduleReportError
import com.codely.shared.robot.domain.RobotId

context(ReportScheduler)
suspend fun scheduleReport(id: RobotId): Either<ScheduleReportError, Unit> =
    schedule(id)
        .mapLeft { error -> ScheduleReportError.Unknown(error) }
