package com.codely.pollution.primaryadapter

import com.codely.pollution.application.read.ReadPollutionCommand
import com.codely.pollution.application.read.handle
import com.codely.pollution.domain.PollutionReader
import com.codely.pollution.domain.PollutionReadingRepository
import com.codely.shared.event.bus.DomainEventPublisher
import com.codely.shared.event.robot.RobotMovedHundredMetersEvent
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
class ReadPollutionOnRobotMovedHundredMetersSubscriber(
    private val repository: PollutionReadingRepository,
    private val reader: PollutionReader,
    private val publisher: DomainEventPublisher
) {

    private val logger = KotlinLogging.logger {}

    @EventHandler
    fun on(event: RobotMovedHundredMetersEvent) = runBlocking {
        with(repository) {
            with(reader) {
                with(publisher) {
                    handle(ReadPollutionCommand(event.id, event.location))
                }
            }
        }
    }.mapLeft { error -> error.also { logger.error { "Error encountered while reading pollution: $error" } } }
}
