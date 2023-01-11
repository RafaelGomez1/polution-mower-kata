package com.codely.robot.fakes

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.codely.robot.domain.Robot
import com.codely.robot.domain.RobotRepository
import com.codely.shared.FakeRepository
import com.codely.shared.robot.domain.RobotId

class FakeRobotRepository : RobotRepository, FakeRepository<Robot> {

    private val robots = mutableMapOf<RobotId, Robot>()
    private var shouldFailFinding = false

    fun shouldFailOnFindingBy(result: Boolean) { shouldFailFinding = result }

    override fun resetFake() {
        robots.clear()
        shouldFailFinding = false
    }

    override fun assertResourceWasPersisted(resource: Robot): Boolean = robots.containsKey(resource.id)
    override fun shouldFailOnFinding(result: Boolean) { shouldFailFinding = result }

    override suspend fun findBy(id: RobotId): Either<Throwable, Robot> =
        if (shouldFailFinding) {
            NoSuchElementException().left()
        } else {
            robots.getValue(id).right()
        }

    override suspend fun save(robot: Robot): Either<Throwable, Unit> =
        Either.catch { robots.put(robot.id, robot) }
            .map { }

    override suspend fun existBy(id: RobotId): Either<Throwable, Boolean> =
        Either.catch { robots.containsKey(id) }
}
