package com.codely.robot.domain

import arrow.core.Either
import com.codely.shared.domain.robot.RobotId

interface RobotRepository {
    suspend fun findBy(id: RobotId): Either<Throwable, Robot>
    suspend fun save(robot: Robot): Either<Throwable, Unit>
}
