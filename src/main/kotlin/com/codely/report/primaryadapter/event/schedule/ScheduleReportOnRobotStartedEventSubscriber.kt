package com.codely.report.primaryadapter.event.schedule

import com.codely.report.application.ScheduleReportCommand
import com.codely.report.application.schedule.handle
import com.codely.report.domain.ReportScheduler
import com.codely.shared.event.robot.RobotStartedEvent
import kotlinx.coroutines.runBlocking
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
class ScheduleReportOnRobotStartedEventSubscriber(
    private val scheduler: ReportScheduler
) {

    @EventHandler
    fun on(event: RobotStartedEvent) = runBlocking {
        with(scheduler) {
            handle(ScheduleReportCommand(id = event.id))
        }
    }
}
