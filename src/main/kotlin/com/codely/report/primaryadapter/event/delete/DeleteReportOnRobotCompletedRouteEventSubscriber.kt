package com.codely.report.primaryadapter.event.delete

import com.codely.report.application.DeleteReportScheduleCommand
import com.codely.report.application.delete.handle
import com.codely.report.domain.ReportScheduler
import com.codely.shared.event.robot.RobotCompletedRouteEvent
import kotlinx.coroutines.runBlocking
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
class DeleteReportOnRobotCompletedRouteEventSubscriber(
    private val scheduler: ReportScheduler
) {

    @EventHandler
    fun on(event: RobotCompletedRouteEvent) = runBlocking {
        with(scheduler) {
            handle(DeleteReportScheduleCommand(id = event.id))
        }
    }
}
