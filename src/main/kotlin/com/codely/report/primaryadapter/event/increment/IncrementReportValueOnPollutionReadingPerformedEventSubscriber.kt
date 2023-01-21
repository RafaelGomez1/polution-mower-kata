package com.codely.report.primaryadapter.event.increment

import com.codely.report.application.IncrementReportPollutionCommand
import com.codely.report.application.increment.handle
import com.codely.report.domain.ReportRepository
import com.codely.shared.event.pollution.PollutionReadingPerformedEvent
import kotlinx.coroutines.runBlocking
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
class IncrementReportValueOnPollutionReadingPerformedEventSubscriber(private val repository: ReportRepository) {

    @EventHandler
    fun on(event: PollutionReadingPerformedEvent) = runBlocking {
        with(repository) {
            handle(IncrementReportPollutionCommand(robotId = event.robotId, pollution = event.pollution))
        }
    }
}
