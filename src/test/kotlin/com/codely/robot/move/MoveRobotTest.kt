package com.codely.robot.move

import arrow.core.getOrElse
import com.codely.robot.domain.DistanceCalculator
import com.codely.robot.domain.MoveRobotError
import com.codely.robot.domain.Robot
import com.codely.robot.domain.Running
import com.codely.robot.fakes.FakeRobotRepository
import com.codely.robot.mothers.RobotMother
import com.codely.robot.primaryadapter.event.move.MoveRobotOnRobotStartedSubscriber
import com.codely.robot.secondaryadapter.distance.VincentyDistanceCalculator
import com.codely.shared.event.robot.RobotCompletedRouteEvent
import com.codely.shared.event.robot.RobotStartedEvent
import com.codely.shared.publisher.FakeDomainEventPublisher
import com.codely.shared.robot.domain.Location
import io.kotest.assertions.arrow.core.shouldBeLeft
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class MoveRobotTest {

    private val repository = FakeRobotRepository()
    private val publisher = FakeDomainEventPublisher()
    private val calculator: DistanceCalculator = VincentyDistanceCalculator()

    private val subscriber = MoveRobotOnRobotStartedSubscriber(repository, publisher, calculator)

    @BeforeEach
    internal fun setUp() {
        repository.resetFake()
        publisher.resetFake()
    }

    @Test
    fun `should move a robot until the end of the route`() = runTest {
        // Given
        `robot does exist`(startedRobot)

        // When
        val resultRobot = subscriber.on(event)
            .getOrElse { fail("Expected a working robot") }

        // Then
        assertEquals(expectedRobot.location, resultRobot.location)
        assertTrue { publisher.eventsWerePublished() }
        assertTrue { publisher.eventWasPublished(expectedEvent) }
    }

    @Test
    fun `should fail on Robot Not Found`() = runTest {
        // Given
        `robot does not exist`()

        // When
        subscriber.on(event)
            .shouldBeLeft(MoveRobotError.RobotNotFound)

        // Then
        assertFalse { publisher.eventsWerePublished() }
        assertFalse { publisher.eventWasPublished(expectedEvent) }
    }

    @Test
    fun `should fail when robot has no route assigned`() = runTest {
        // Given
        `robot does exist`(unroutedRobot)

        // When
        subscriber.on(event)
            .shouldBeLeft(MoveRobotError.RouteNotAssignedToRobot)

        // Then
        assertFalse { publisher.eventsWerePublished() }
        assertFalse { publisher.eventWasPublished(expectedEvent) }
    }

    private suspend fun `robot does exist`(robot: Robot) = repository.save(robot)
    private fun `robot does not exist`() = repository.shouldFailOnFindingBy(true)

    private val startedRobot = RobotMother.withRealPolyline(running = Running(true))
    private val event = RobotStartedEvent(startedRobot.id.value)

    private val unroutedRobot = startedRobot.copy(route = null)

    private val expectedLocation = startedRobot.route?.points!!.last()
    private val expectedRobot = startedRobot.copy(location = Location(expectedLocation))
    private val expectedEvent = RobotCompletedRouteEvent(expectedRobot.id.value)
}
