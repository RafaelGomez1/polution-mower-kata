package com.codely.robot.application.route

import com.codely.robot.domain.RouteRobotError
import com.codely.robot.secondaryadapter.FakeRobotRepository
import com.codely.shared.robot.RobotMother
import com.codely.shared.robot.RouteMother
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class RobotRouterTest {

    private val repository = FakeRobotRepository()

    @AfterEach
    internal fun tearDown() {
        repository.resetFake()
    }

    @Test
    fun `should route robot`() = runTest {
        // Given
        `robot does exist`()

        // When
        with(repository) {
            routeRobot(robot.id, newRoute).shouldBeRight(routedRobot)
        }
    }

    @Test
    fun `should return error if robot not found`() = runTest {
        // Given
        `robot does not exist`()

        // When
        with(repository) {
            routeRobot(robot.id, newRoute).shouldBeLeft(RouteRobotError.RobotNotFound)
        }
    }

    private suspend fun `robot does exist`() = repository.save(robot)

    private suspend fun `robot does not exist`() = repository.shouldFailOnFindingBy(true)

    private val robot = RobotMother.invoke()
    private val newRoute = RouteMother.invoke()
    private val routedRobot = robot.copy(route = newRoute)
}
