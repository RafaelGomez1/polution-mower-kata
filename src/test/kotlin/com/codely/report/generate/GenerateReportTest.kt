package com.codely.report.generate

import com.codely.report.AbstractReportUnitTest
import com.codely.report.domain.GenerateReportError
import com.codely.report.mothers.ReportMother
import com.codely.report.primaryadapter.event.generate.GenerateReportOnRobotCompletedRouteSubscriber
import com.codely.shared.event.robot.RobotCompletedRouteEvent
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class GenerateReportTest : AbstractReportUnitTest() {

    private val subscriber = GenerateReportOnRobotCompletedRouteSubscriber(repository, generator)

    @Test
    fun `should generate a report`() = runTest {
        // Given
        `report does exist`(report)

        // When
        subscriber.on(event)
            .shouldBeRight(Unit)

        // Then
        generator shouldHaveBeenGenerated true
    }

    @Test
    fun `should fails if report is not found`() = runTest {
        // Given
        `report does not exist`()

        // When
        subscriber.on(event)
            .shouldBeLeft(GenerateReportError.ReportDoesNotExist)

        // Then
        generator shouldHaveBeenGenerated false
    }

    private val report = ReportMother.invoke()
    private val event = RobotCompletedRouteEvent(report.robotId.value)
}
