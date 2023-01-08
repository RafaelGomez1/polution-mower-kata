package com.codely.robot.create

import com.codely.robot.domain.Robot
import com.codely.robot.domain.Running
import com.codely.robot.primaryadapter.rest.create.CreateRobotController
import com.codely.robot.secondaryadapter.FakeRobotRepository
import com.codely.shared.configuration.RobotConfiguration
import com.codely.shared.robot.CreateRobotDTOMother
import com.codely.shared.robot.RobotMother
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class CreateRobotTest {

    private val repository = FakeRobotRepository()
    private val configuration = RobotConfiguration(speed = 2.0, delayTimeUnit = 1)
    private val controller: CreateRobotController = CreateRobotController(repository, configuration)

    @BeforeEach
    internal fun tearDown() {
        repository.resetFake()
    }

    @Test
    fun `should create a robot and return 200`() = runTest {
        // When
        val result = controller.create(stoppedRobot.id.value, createRobotDTO)

        // Then
        assertEquals(ResponseEntity.status(200).build(), result)
    }

    @Test
    fun `should return CONFLICT if the robot was already started`() = runTest {
        // Given
        `robot does exist`(startedRobot)

        // When
        val result = controller.create(startedRobot.id.value, createRobotDTO)

        // Then
        assertEquals(ResponseEntity.status(HttpStatus.CONFLICT).build(), result)
    }

    private suspend fun `robot does exist`(robot: Robot) = repository.save(robot)

    private val stoppedRobot = RobotMother.invoke(running = Running(false))
    private val startedRobot = stoppedRobot.copy(running = Running(true))
    private val createRobotDTO = CreateRobotDTOMother.invoke()
}
