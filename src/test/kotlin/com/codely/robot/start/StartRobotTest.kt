package com.codely.robot.start

import com.codely.robot.domain.Robot
import com.codely.robot.domain.Running
import com.codely.robot.primaryadapter.rest.start.StartRobotController
import com.codely.robot.secondaryadapter.FakeRobotRepository
import com.codely.shared.event.robot.RobotStartedEvent
import com.codely.shared.publisher.FakeDomainEventPublisher
import com.codely.shared.robot.RobotMother
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class StartRobotTest {

    private val repository = FakeRobotRepository()
    private val publisher = FakeDomainEventPublisher()

    private val controller: StartRobotController = StartRobotController(repository, publisher)

    @AfterEach
    internal fun tearDown() {
        repository.resetFake()
        publisher.resetFake()
    }

    @Test
    fun `should start a robot and return 200`() = runTest {
        // Given
        `robot does exist`(stoppedRobot)

        // When
        val result = controller.start(stoppedRobot.id.value)

        // Then
        assertEquals(ResponseEntity.status(200).build(), result)
        assertTrue { publisher.eventWasPublished(expectedEvent) }
    }

    @Test
    fun `should return NOT_FOUND if the robot does not exist`() = runTest {
        // Given
        `robot does not exist`()

        // When
        val result = controller.start(stoppedRobot.id.value)

        // Then
        assertEquals(ResponseEntity.status(HttpStatus.NOT_FOUND).build(), result)
        assertFalse { publisher.eventWasPublished(expectedEvent) }
    }

    @Test
    fun `should return CONFLICT if the robot was already started`() = runTest {
        // Given
        `robot does exist`(startedRobot)

        // When
        val result = controller.start(startedRobot.id.value)

        // Then
        assertEquals(ResponseEntity.status(HttpStatus.CONFLICT).build(), result)
        assertFalse { publisher.eventWasPublished(expectedEvent) }
    }

    private suspend fun `robot does exist`(robot: Robot) = repository.save(robot)
    private fun `robot does not exist`() = repository.shouldFailOnFindingBy(true)

    private val stoppedRobot = RobotMother.invoke(running = Running(false))
    private val startedRobot = stoppedRobot.copy(running = Running(true))
    private val expectedEvent = RobotStartedEvent(stoppedRobot.id.value)
}
