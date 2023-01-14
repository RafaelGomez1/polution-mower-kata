package com.codely.robot.create

import com.codely.robot.AbstractUnitTest
import com.codely.robot.domain.Running
import com.codely.robot.mothers.CreateRobotDTOMother
import com.codely.robot.mothers.RobotMother
import com.codely.robot.primaryadapter.rest.create.CreateRobotController
import com.codely.shared.configuration.robot.RobotConfiguration
import com.codely.shared.response.REQUEST_PROCESSED_MESSAGE
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

@ExperimentalCoroutinesApi
class CreateRobotTest : AbstractUnitTest() {

    private val configuration = RobotConfiguration(speed = 2.0, delayTimeUnit = 1)
    private val controller: CreateRobotController = CreateRobotController(repository, configuration)

    @Test
    fun `should create a robot and return 200`() = runTest {
        // When
        val response = controller.create(stoppedRobot.id.value, createRobotDTO)

        // Then
        response.`should be`(HttpStatus.OK, REQUEST_PROCESSED_MESSAGE)
    }

    @Test
    fun `should return CONFLICT if the robot was already started`() = runTest {
        // Given
        `robot does exist`(startedRobot)

        // When
        val response = controller.create(startedRobot.id.value, createRobotDTO)

        // Then
        response.`should be`(HttpStatus.CONFLICT, "cause")
    }

    private val stoppedRobot = RobotMother.invoke(running = Running(false))
    private val startedRobot = stoppedRobot.copy(running = Running(true))
    private val createRobotDTO = CreateRobotDTOMother.invoke()
}
