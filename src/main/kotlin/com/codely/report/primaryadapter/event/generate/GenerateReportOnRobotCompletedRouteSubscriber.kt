package com.codely.report.primaryadapter.event.generate

import com.codely.report.application.GenerateReportCommand
import com.codely.report.application.generate.handle
import com.codely.report.domain.ReportGenerator
import com.codely.report.domain.ReportRepository
import com.codely.shared.event.robot.RobotCompletedRouteEvent
import kotlinx.coroutines.runBlocking
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
class GenerateReportOnRobotCompletedRouteSubscriber(
    private val repository: ReportRepository,
    private val generator: ReportGenerator
) {

    @EventHandler
    fun on(event: RobotCompletedRouteEvent) = runBlocking {
        with(repository) {
            with(generator) {
                handle(GenerateReportCommand(robotId = event.id))
            }
        }
    }
}
