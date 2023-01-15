package com.codely.report.primaryadapter.event.create

import com.codely.report.application.CreateReportCommand
import com.codely.report.application.create.handle
import com.codely.report.domain.ReportRepository
import com.codely.shared.event.robot.RobotCreatedEvent
import kotlinx.coroutines.runBlocking
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
class CreateReportOnRobotCreatedEventSubscriber(
    private val repository: ReportRepository
) {

    @EventHandler
    fun on(event: RobotCreatedEvent) = runBlocking {
        with(repository) {
            handle(CreateReportCommand(id = event.id, location = event.location))
        }
    }
}
