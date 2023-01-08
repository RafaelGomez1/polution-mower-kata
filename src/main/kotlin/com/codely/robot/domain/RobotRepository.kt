package com.codely.robot.domain

import arrow.core.Either
import com.codely.shared.robot.domain.RobotId

interface RobotRepository {
    suspend fun findBy(id: RobotId): Either<Throwable, Robot>
    suspend fun save(robot: Robot): Either<Throwable, Unit>
    suspend fun existBy(id: RobotId): Either<Throwable, Boolean>
}
