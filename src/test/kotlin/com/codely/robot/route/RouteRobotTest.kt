package com.codely.robot.route

import com.codely.robot.AbstractRobotUnitTest
import com.codely.robot.domain.Route
import com.codely.robot.mothers.RobotMother
import com.codely.robot.mothers.RouteRobotDTOMother
import com.codely.robot.primaryadapter.rest.route.RouteRobotController
import com.codely.shared.response.REQUEST_PROCESSED_MESSAGE
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

@ExperimentalCoroutinesApi
class RouteRobotTest : AbstractRobotUnitTest() {

    private val controller: RouteRobotController = RouteRobotController(repository)

    @Test
    fun `should route a robot and return 200`() = runTest {
        // Given
        `robot does exist`(robot)

        // When
        val response = controller.route(robot.id.value, dto)

        // Then
        response.`should be`(HttpStatus.OK, REQUEST_PROCESSED_MESSAGE)
        `assert robot was persisted`(expectedRobot)
    }

    @Test
    fun `should return Not found if the robot does not exist`() = runTest {
        // Given
        `robot does not exist`()

        // When
        val response = controller.route(robot.id.value, dto)

        // Then
        response.`should be`(HttpStatus.NOT_FOUND, "cause")
        `assert robot was not persisted`(expectedRobot)
    }

    private val robot = RobotMother.invoke()
    private val dto = RouteRobotDTOMother.invoke()

    private val expectedRobot = robot.copy(route = Route.fromPolyline(dto.route))
}
