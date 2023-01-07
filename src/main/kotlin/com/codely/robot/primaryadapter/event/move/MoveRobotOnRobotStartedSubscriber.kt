package com.codely.robot.primaryadapter.event.move

import com.codely.robot.domain.DistanceCalculator
import com.codely.robot.domain.RobotRepository
import com.codely.shared.event.bus.DomainEventPublisher
import com.codely.shared.event.robot.RobotStartedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class MoveRobotOnRobotStartedSubscriber(
    private val repository: RobotRepository,
    private val publisher: DomainEventPublisher,
    private val calculator: DistanceCalculator
) {

    @EventListener
    fun on(event: RobotStartedEvent) {
    }
}
