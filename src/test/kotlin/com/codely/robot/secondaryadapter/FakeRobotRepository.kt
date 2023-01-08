package com.codely.robot.secondaryadapter

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.codely.robot.domain.Robot
import com.codely.robot.domain.RobotRepository
import com.codely.shared.robot.domain.RobotId

class FakeRobotRepository : RobotRepository {

    private val robots = mutableMapOf<RobotId, Robot>()
    private var shouldFailFinding = false

    fun shouldFailOnFindingBy(result: Boolean) { shouldFailFinding = result }

    fun resetFake() {
        robots.clear()
        shouldFailFinding = false
    }

    override suspend fun findBy(id: RobotId): Either<Throwable, Robot> =
        if (shouldFailFinding) NoSuchElementException().left()
        else robots.getValue(id).right()

    override suspend fun save(robot: Robot): Either<Throwable, Unit> =
        Either.catch { robots.put(robot.id, robot) }
            .map { }

    override suspend fun existBy(id: RobotId): Either<Throwable, Boolean> =
        Either.catch { robots.containsKey(id) }
}
