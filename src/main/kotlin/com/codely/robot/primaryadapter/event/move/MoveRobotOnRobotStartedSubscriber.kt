package com.codely.robot.primaryadapter.event.move

import com.codely.robot.application.MoveRobotCommand
import com.codely.robot.application.move.handle
import com.codely.robot.domain.DistanceCalculator
import com.codely.robot.domain.RobotRepository
import com.codely.shared.event.bus.DomainEventPublisher
import com.codely.shared.event.robot.RobotStartedEvent
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class MoveRobotOnRobotStartedSubscriber(
    private val repository: RobotRepository,
    private val publisher: DomainEventPublisher,
    private val calculator: DistanceCalculator
) {
    private val logger = KotlinLogging.logger {}

    @EventListener
    fun on(event: RobotStartedEvent) =
        runBlocking {
            with(repository) {
                with(publisher) {
                    with(calculator) {
                        handle(MoveRobotCommand(event.id))
                    }
                }
            }
        }.mapLeft { error -> error.also { logger.error { "Error encountered while moving robot: $error" } } }
}
