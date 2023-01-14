package com.codely.integration.robot

import com.codely.integration.AbstractContainerTest
import com.codely.integration.shared.robot.mothers.RobotMother
import com.codely.robot.domain.RobotRepository
import com.codely.robot.domain.Running
import com.codely.shared.event.bus.DomainEventPublisher
import com.codely.shared.event.robot.RobotStartedEvent
import com.codely.shared.robot.LocationMother
import io.kotest.assertions.arrow.core.shouldBeRight
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class StartRobotCT(
    @Autowired
    private val publisher: DomainEventPublisher,
    @Autowired
    private val repository: RobotRepository
) : AbstractContainerTest() {

    @Test
    fun `should start an existing robot`() {
        // Given
        `robot exists`()

        // When
        publisher.publish(listOf(event))

        // Then
        `verify robot ended trip`()
    }

    private fun `verify robot ended trip`() {
        runBlocking { repository.findBy(robot.id).map { r -> assertEquals(r.location, expectedRobot.location) } }
    }

    private fun `robot exists`() {
        runBlocking { repository.save(robot).shouldBeRight(Unit) }
    }

    private val robot = RobotMother.withRealPolyline(running = Running(true))
    private val expectedRobot = robot.copy(location = LocationMother.fromPolylineLastPosition())
    private val event = RobotStartedEvent(robot.id.value)
}
