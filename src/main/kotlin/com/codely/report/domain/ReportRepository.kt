package com.codely.report.domain

import arrow.core.Either
import com.codely.shared.robot.domain.RobotId

interface ReportRepository {
    suspend fun save(report: Report): Either<Throwable, Unit>
    suspend fun existBy(robotId: RobotId): Either<Throwable, Boolean>
    suspend fun findBy(robotId: RobotId): Either<Throwable, Report>
}
