package com.codely.robot

import com.codely.robot.domain.Robot
import com.codely.robot.fakes.FakeRobotRepository
import com.codely.shared.event.bus.DomainEvent
import com.codely.shared.publisher.FakeDomainEventPublisher
import org.junit.jupiter.api.BeforeEach
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

abstract class AbstractRobotUnitTest {

    val repository = FakeRobotRepository()
    val publisher = FakeDomainEventPublisher()

    @BeforeEach
    internal fun tearDown() {
        repository.resetFake()
        publisher.resetFake()
    }

    // Repository Configurations
    suspend fun `robot does exist`(robot: Robot) = repository.save(robot)
    fun `robot does not exist`() = repository.shouldFailOnFindingBy(true)

    // Repository assertions
    fun `assert robot was persisted`(robot: Robot) = assertTrue { repository.assertResourceWasPersisted(robot) }
    fun `assert robot was not persisted`(robot: Robot) = assertFalse { repository.assertResourceWasPersisted(robot) }

    // Domain Event assertions
    fun `assert event was not published`(event: DomainEvent) = assertFalse { publisher.eventWasPublished(event) }
    fun `assert event was published`(event: DomainEvent) = assertTrue { publisher.eventWasPublished(event) }

    // Response Entity assertion
    fun `server response should be`(expectedStatus: HttpStatus, expectedResponseMessage: String, response: ResponseEntity<*>) =
        assertEquals(ResponseEntity.status(expectedStatus).body(expectedResponseMessage), response)

    fun ResponseEntity<*>.`should be`(expectedStatus: HttpStatus, expectedResponseMessage: String) =
        assertEquals(ResponseEntity.status(expectedStatus).body(expectedResponseMessage), this)
}
