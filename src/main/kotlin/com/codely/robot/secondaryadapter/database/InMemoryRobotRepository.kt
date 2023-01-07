package com.codely.robot.secondaryadapter.database

import arrow.core.Either
import com.codely.robot.domain.Robot
import com.codely.robot.domain.RobotRepository
import com.codely.shared.domain.robot.RobotId
import org.springframework.stereotype.Repository

@Repository
class InMemoryRobotRepository : RobotRepository {
    private val robots = mutableMapOf<RobotId, Robot>()

    override suspend fun findBy(id: RobotId): Either<Throwable, Robot> =
        Either.catch { robots.getValue(id) }

    override suspend fun save(robot: Robot): Either<Throwable, Unit> =
        Either.catch { robots.put(robot.id, robot) }
            .map {  }
}