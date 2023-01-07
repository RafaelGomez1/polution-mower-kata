package com.codely.robot.application.stop

import com.codely.robot.application.StopRobotCommand
import com.codely.robot.domain.Robot
import com.codely.robot.domain.Running
import com.codely.robot.domain.StopRobotError
import com.codely.robot.secondaryadapter.FakeRobotRepository
import com.codely.shared.event.robot.RobotStoppedEvent
import com.codely.shared.robot.RobotMother
import com.codely.shared.robot.publisher.FakeDomainEventPublisher
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class RobotStopperTest {

    private val repository = FakeRobotRepository()
    private val publisher = FakeDomainEventPublisher()

    @AfterEach
    internal fun tearDown() {
        repository.resetFake()
        publisher.resetFake()
    }

    @Test
    fun `should stop robot`() = runTest {
        // Given
        `robot does exist`(startedRobot)

        // When
        with(publisher) {
            with(repository) {
                handle(stopRobotCommand).shouldBeRight(stoppedRobot)
            }
        }

        assertTrue { publisher.eventWasPublished(expectedEvent) }
    }

    @Test
    fun `should return error if robot not found`() = runTest {
        // Given
        `robot does not exist`()

        // When
        with(publisher) {
            with(repository) {
                handle(stopRobotCommand).shouldBeLeft(StopRobotError.RobotNotFound)
            }
        }
    }

    @Test
    fun `should return error if robot already stopped`() = runTest {
        // Given
        `robot does exist`(stoppedRobot)

        // When
        with(publisher) {
            with(repository) {
                handle(stopRobotCommand).shouldBeLeft(StopRobotError.RobotAlreadyStopped)
            }
        }

        // Then
        assertFalse { publisher.eventsWerePublished() }
    }

    private suspend fun `robot does exist`(robot: Robot) = repository.save(robot)

    private suspend fun `robot does not exist`() = repository.shouldFailOnFindingBy(true)

    private val startedRobot = RobotMother.invoke(running = Running(true))
    private val stoppedRobot = startedRobot.copy(running = Running(false))
    private val stopRobotCommand = StopRobotCommand(startedRobot.id.value)
    private val expectedEvent = RobotStoppedEvent(stoppedRobot.id.value)
}
