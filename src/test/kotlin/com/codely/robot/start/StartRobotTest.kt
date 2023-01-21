package com.codely.robot.start

import com.codely.robot.AbstractRobotUnitTest
import com.codely.robot.domain.Running
import com.codely.robot.mothers.RobotMother
import com.codely.robot.primaryadapter.rest.start.StartRobotController
import com.codely.shared.event.robot.RobotStartedEvent
import com.codely.shared.response.REQUEST_PROCESSED_MESSAGE
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

@ExperimentalCoroutinesApi
class StartRobotTest : AbstractRobotUnitTest() {

    private val controller: StartRobotController = StartRobotController(repository, publisher)

    @Test
    fun `should start a robot and return 200`() = runTest {
        // Given
        `robot does exist`(stoppedRobot)

        // When
        val response = controller.start(stoppedRobot.id.value)

        // Then
        response.`should be`(HttpStatus.OK, REQUEST_PROCESSED_MESSAGE)
        `assert event was published`(expectedEvent)
    }

    @Test
    fun `should return NOT_FOUND if the robot does not exist`() = runTest {
        // Given
        `robot does not exist`()

        // When
        val response = controller.start(stoppedRobot.id.value)

        // Then
        response.`should be`(HttpStatus.NOT_FOUND, "cause")
        `assert event was not published`(expectedEvent)
    }

    @Test
    fun `should return CONFLICT if the robot was already started`() = runTest {
        // Given
        `robot does exist`(startedRobot)

        // When
        val response = controller.start(startedRobot.id.value)

        // Then
        response.`should be`(HttpStatus.CONFLICT, "cause")
        `assert event was not published`(expectedEvent)
    }

    private val stoppedRobot = RobotMother.invoke(running = Running(false))
    private val startedRobot = stoppedRobot.copy(running = Running(true))
    private val expectedEvent = RobotStartedEvent(stoppedRobot.id.value)
}
