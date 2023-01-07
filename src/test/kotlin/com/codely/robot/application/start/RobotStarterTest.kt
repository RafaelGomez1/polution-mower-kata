package com.codely.robot.application.start

import com.codely.robot.application.StartRobotCommand
import com.codely.robot.domain.Robot
import com.codely.robot.domain.Running
import com.codely.robot.domain.StartRobotError
import com.codely.robot.secondaryadapter.FakeRobotRepository
import com.codely.shared.event.robot.RobotStartedEvent
import com.codely.shared.robot.RobotMother
import com.codely.shared.robot.publisher.FakeDomainEventPublisher
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class RobotStarterTest {

    private val repository = FakeRobotRepository()
    private val publisher = FakeDomainEventPublisher()

    @AfterEach
    internal fun tearDown() {
        repository.resetFake()
        publisher.resetFake()
    }

    @Test
    fun `should start robot`() = runTest {
        // Given
        `robot does exist`(stoppedRobot)

        // When
        with(publisher) {
            with(repository) {
                handle(startRobotCommand).shouldBeRight(startedRobot)
            }
        }

        // Then
        assertTrue { publisher.eventWasPublished(expectedEvent) }
    }

    @Test
    fun `should return error if robot not found`() = runTest {
        // Given
        `robot does not exist`()

        // When
        with(publisher) {
            with(repository) {
                handle(startRobotCommand).shouldBeLeft(StartRobotError.RobotNotFound)
            }
        }
    }

    @Test
    fun `should return error if robot already started`() = runTest {
        // Given
        `robot does exist`(startedRobot)

        // When
        with(publisher) {
            with(repository) {
                handle(startRobotCommand).shouldBeLeft(StartRobotError.RobotAlreadyStarted)
            }
        }

        // Then
        assertFalse { publisher.eventsWerePublished() }
    }

    private suspend fun `robot does exist`(robot: Robot) = repository.save(robot)
    private suspend fun `robot does not exist`() = repository.shouldFailOnFindingBy(true)

    private val stoppedRobot = RobotMother.invoke(running = Running(false))
    private val startedRobot = stoppedRobot.copy(running = Running(true))
    private val startRobotCommand = StartRobotCommand(stoppedRobot.id.value)
    private val expectedEvent = RobotStartedEvent(stoppedRobot.id.value)
}