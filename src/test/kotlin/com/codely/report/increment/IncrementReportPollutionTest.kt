package com.codely.report.increment

import com.codely.report.AbstractReportUnitTest
import com.codely.report.domain.IncrementReportPollutionError
import com.codely.report.domain.PollutionQuantity
import com.codely.report.mothers.ReportMother
import com.codely.report.primaryadapter.event.increment.IncrementReportValueOnPollutionReadingPerformedEventSubscriber
import com.codely.shared.event.pollution.PollutionReadingPerformedEvent
import com.codely.shared.robot.LocationMother
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.random.Random

@ExperimentalCoroutinesApi
class IncrementReportPollutionTest : AbstractReportUnitTest() {

    private val subscriber = IncrementReportValueOnPollutionReadingPerformedEventSubscriber(repository)

    @Test
    fun `should increment reports pollution value`() = runTest {
        // Given
        `report does exist`(report)

        // When
        subscriber.on(event)
            .shouldBeRight(expectedReport)

        // Then
        `assert report was persisted`(expectedReport)
    }

    @Test
    fun `should fail if no report exists for the given robot`() = runTest {
        // Given
        `report does not exist`()

        // When
        subscriber.on(event)
            .shouldBeLeft(IncrementReportPollutionError.ReportDoesNotExist)

        // Then
        `assert report was not persisted`(expectedReport)
    }

    private val report = ReportMother.invoke()
    private val event = PollutionReadingPerformedEvent(
        aggregateId = UUID.randomUUID().toString(),
        robotId = report.robotId.value,
        pollution = Random.nextInt(),
        location = LocationMother.invoke().value
    )

    private val expectedReport = report.copy(pollution = PollutionQuantity(report.pollution.value + event.pollution), readings = report.readings.inc())
}
