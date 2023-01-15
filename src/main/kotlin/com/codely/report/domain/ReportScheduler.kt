package com.codely.report.domain

import arrow.core.Either
import com.codely.shared.robot.domain.RobotId

interface ReportScheduler {

    suspend fun schedule(robotId: RobotId): Either<Throwable, Unit>
    suspend fun deleteScheduler(robotId: RobotId): Either<Throwable, Unit>
}
