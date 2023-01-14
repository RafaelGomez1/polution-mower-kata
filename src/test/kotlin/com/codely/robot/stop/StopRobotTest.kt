package com.codely.robot.stop

import com.codely.robot.AbstractUnitTest
import com.codely.robot.domain.Running
import com.codely.robot.mothers.RobotMother
import com.codely.robot.primaryadapter.rest.stop.StopRobotController
import com.codely.shared.response.REQUEST_PROCESSED_MESSAGE
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

@ExperimentalCoroutinesApi
class StopRobotTest : AbstractUnitTest() {

    private val controller: StopRobotController = StopRobotController(repository, publisher)

    @Test
    fun `should stop a moving robot and return OKAY`() = runTest {
        // Given
        `robot does exist`(activeRobot)

        // When
        val response = controller.stop(activeRobot.id.value)

        // Then
        response.`should be`(HttpStatus.OK, REQUEST_PROCESSED_MESSAGE)
    }

    @Test
    fun `should return NOT_FOUND if robot does not exist`() = runTest {
        // Given
        `robot does not exist`()

        // When
        val response = controller.stop(activeRobot.id.value)

        // Then
        response.`should be`(HttpStatus.NOT_FOUND, "cause")
    }

    @Test
    fun `should return CONFLICT if robot is already stopped`() = runTest {
        // Given
        `robot does exist`(stoppedRobot)

        // When
        val response = controller.stop(stoppedRobot.id.value)

        // Then
        response.`should be`(HttpStatus.CONFLICT, "cause")
    }

    private val activeRobot = RobotMother.invoke(running = Running(true))
    private val stoppedRobot = activeRobot.copy(running = Running(false))
}
