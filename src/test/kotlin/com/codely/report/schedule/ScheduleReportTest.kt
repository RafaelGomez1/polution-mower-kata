package com.codely.report.schedule

import com.codely.report.AbstractReportUnitTest
import com.codely.report.primaryadapter.event.schedule.ScheduleReportOnRobotStartedEventSubscriber
import com.codely.shared.event.robot.RobotStartedEvent
import com.codely.shared.robot.RobotIdMother
import io.kotest.assertions.arrow.core.shouldBeRight
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class ScheduleReportTest : AbstractReportUnitTest() {

    private val subscriber = ScheduleReportOnRobotStartedEventSubscriber(scheduler)

    @Test
    fun `should schedule a report on robot started event`() = runTest {
        // When
        subscriber.on(event)
            .shouldBeRight(Unit)

        // Then
        scheduler shouldHaveActiveSchedule true
    }

    private val robotId = RobotIdMother.invoke()
    private val event = RobotStartedEvent(id = robotId.value)
}
