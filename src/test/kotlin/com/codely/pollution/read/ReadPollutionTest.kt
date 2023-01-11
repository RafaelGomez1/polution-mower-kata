package com.codely.pollution.read

import arrow.core.getOrElse
import com.codely.pollution.domain.PollutionReader
import com.codely.pollution.fakes.FakePollutionReadingRepository
import com.codely.pollution.mothers.PollutionReadingMother
import com.codely.pollution.primaryadapter.ReadPollutionOnRobotMovedHundredMetersSubscriber
import com.codely.pollution.secondaryadapter.reader.RandomPollutionReader
import com.codely.shared.pollution.PollutionReadingPerformedEventMother
import com.codely.shared.publisher.FakeDomainEventPublisher
import com.codely.shared.robot.LocationMother
import com.codely.shared.robot.RobotIdMother
import com.codely.shared.robot.RobotMovedHundredMetersEventMother
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue
import kotlin.test.fail

@ExperimentalCoroutinesApi
class ReadPollutionTest {

    private val repository = FakePollutionReadingRepository()
    private val reader: PollutionReader = RandomPollutionReader()
    private val publisher = FakeDomainEventPublisher()

    private val subscriber = ReadPollutionOnRobotMovedHundredMetersSubscriber(repository, reader, publisher)

    @BeforeEach
    internal fun setUp() {
        repository.resetFake()
        publisher.resetFake()
    }

    @Test
    fun `should read pollution level on event`() = runTest {
        // When
        val reading = subscriber.on(event)
            .getOrElse { fail("Expected a pollution reading") }

        // Then
        val expectedEvent = pollutionReadEvent.copy(
            aggregateId = reading.id.value,
            pollution = reading.pollutionLevel.pollution
        )

        assertTrue { repository.assertResourceWasPersisted(reading) }
        assertTrue { publisher.eventWasPublished(expectedEvent) }
    }

    private val event = RobotMovedHundredMetersEventMother.invoke()
    private val reading = PollutionReadingMother.invoke(robotId = RobotIdMother.invoke(event.id), location = LocationMother.invoke(event.location))
    private val pollutionReadEvent = PollutionReadingPerformedEventMother.invoke(
        robotId = reading.robotId.value,
        location = reading.location.value
    )
}
