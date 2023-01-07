package com.codely.shared.event.bus

interface DomainEventPublisher {
    fun publish(events: List<DomainEvent>)
}
