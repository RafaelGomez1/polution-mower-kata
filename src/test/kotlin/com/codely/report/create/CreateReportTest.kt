package com.codely.report.create

import com.codely.report.AbstractReportUnitTest
import com.codely.report.domain.CreateReportError
import com.codely.report.domain.PollutionQuantity
import com.codely.report.domain.ReportId
import com.codely.report.mothers.ReportMother
import com.codely.report.primaryadapter.event.create.CreateReportOnRobotCreatedEventSubscriber
import com.codely.shared.event.robot.RobotCreatedEvent
import com.codely.shared.id.FakeIdGenerator
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import java.util.*

@ExperimentalCoroutinesApi
class CreateReportTest : AbstractReportUnitTest() {

    private val idGenerator = FakeIdGenerator()
    private val subscriber = CreateReportOnRobotCreatedEventSubscriber(repository, idGenerator)

    @Test
    fun `should create a report after robot created event`() = runTest {
        // GIVEN
        `report does not exist`()

        // WHEN
        subscriber.on(event)
            .shouldBeRight(expectedReport)

        // THEN
        `assert report was persisted`(expectedReport)
    }

    @Test
    fun `should fail if report already exists for the given robot`() = runTest {
        // GIVEN
        `report does exist`(previousReport)

        // WHEN
        subscriber.on(event)
            .shouldBeLeft(CreateReportError.ReportAlreadyExists)

        // THEN
        `assert report was not persisted`(expectedReport)
    }

    private val expectedReport = ReportMother.invoke(
        id = ReportId(UUID.fromString("c2556879-cc25-4f00-90dc-145aa5240f87")),
        readings = 0,
        pollution = PollutionQuantity(0)
    )
    private val previousReport = ReportMother.invoke(robotId = expectedReport.robotId)
    private val event = RobotCreatedEvent(id = expectedReport.robotId.value, location = expectedReport.location?.value)
}
