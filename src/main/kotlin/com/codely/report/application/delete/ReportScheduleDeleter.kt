package com.codely.report.application.delete

import arrow.core.Either
import com.codely.report.domain.DeleteReportScheduleError
import com.codely.report.domain.ReportScheduler
import com.codely.shared.robot.domain.RobotId

context(ReportScheduler)
suspend fun deleteReportSchedule(id: RobotId): Either<DeleteReportScheduleError, Unit> =
    deleteScheduler(id)
        .mapLeft { error -> DeleteReportScheduleError.Unknown(error) }
