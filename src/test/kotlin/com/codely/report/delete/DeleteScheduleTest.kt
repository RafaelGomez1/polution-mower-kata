package com.codely.report.delete

import com.codely.report.AbstractReportUnitTest
import com.codely.report.primaryadapter.event.delete.DeleteReportOnRobotCompletedRouteEventSubscriber
import com.codely.shared.event.robot.RobotCompletedRouteEvent
import com.codely.shared.robot.RobotIdMother
import io.kotest.assertions.arrow.core.shouldBeRight
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class DeleteScheduleTest : AbstractReportUnitTest() {

    private val subscriber = DeleteReportOnRobotCompletedRouteEventSubscriber(scheduler)

    @Test
    fun `should delete a scheduled report on robot completed route event`() = runTest {
        // Given
        `report is scheduled`(robotId)

        // When
        subscriber.on(event)
            .shouldBeRight(Unit)

        // Then
        scheduler shouldHaveActiveSchedule false
    }

    private val robotId = RobotIdMother.invoke()
    private val event = RobotCompletedRouteEvent(id = robotId.value)
}
