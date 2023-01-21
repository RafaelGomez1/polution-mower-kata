package com.codely.report.primaryadapter.event.create

import com.codely.report.application.CreateReportCommand
import com.codely.report.application.create.handle
import com.codely.report.domain.ReportRepository
import com.codely.shared.event.robot.RobotCreatedEvent
import com.codely.shared.id.IdGenerator
import kotlinx.coroutines.runBlocking
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component
import java.util.*

@Component
class CreateReportOnRobotCreatedEventSubscriber(
    private val repository: ReportRepository,
    private val idGenerator: IdGenerator
) {

    @EventHandler
    fun on(event: RobotCreatedEvent) = runBlocking {
        with(repository) {
            handle(
                CreateReportCommand(
                    robotId = event.id,
                    location = event.location,
                    id = idGenerator.generate()
                )
            )
        }
    }
}
