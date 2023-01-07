package com.codely.shared.event.bus

import org.axonframework.eventhandling.gateway.EventGateway
import org.springframework.stereotype.Component

@Component
class AxonDomainEventPublisher(private val eventGateway: EventGateway): DomainEventPublisher {

    override fun publish(events: List<DomainEvent>) {
        eventGateway.publish(events)
    }
}