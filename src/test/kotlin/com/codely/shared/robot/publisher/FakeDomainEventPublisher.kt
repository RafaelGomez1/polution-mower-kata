package com.codely.shared.robot.publisher

import com.codely.shared.event.bus.DomainEvent
import com.codely.shared.event.bus.DomainEventPublisher

class FakeDomainEventPublisher : DomainEventPublisher {
    private var eventsWerePublished = false
    private var publishedEvents = mutableListOf<DomainEvent>()

    fun resetFake() {
        eventsWerePublished = false
    }

    override fun publish(events: List<DomainEvent>) {
        eventsWerePublished = true
        publishedEvents.addAll(events)
    }

    fun eventsWerePublished() = eventsWerePublished
    fun eventWasPublished(event: DomainEvent) = publishedEvents.contains(event)
}