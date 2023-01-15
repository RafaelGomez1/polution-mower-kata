package com.codely.report.application.delete

import arrow.core.Either
import com.codely.report.application.DeleteReportScheduleCommand
import com.codely.report.domain.DeleteReportScheduleError
import com.codely.report.domain.ReportScheduler
import com.codely.shared.robot.domain.RobotId

context(ReportScheduler)
suspend fun handle(command: DeleteReportScheduleCommand): Either<DeleteReportScheduleError, Unit> =
    deleteReportSchedule(id = RobotId(command.id))
