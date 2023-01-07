package com.codely.shared.aggregate

import com.codely.shared.event.bus.DomainEvent

abstract class Aggregate(
    private var domainEvents: MutableList<DomainEvent> = ArrayList()
) {
    fun pullDomainEvents(): List<DomainEvent> {
        val events: List<DomainEvent> = domainEvents
        domainEvents = ArrayList()
        return events
    }

    protected fun record(event: DomainEvent) {
        domainEvents.add(event)
    }
}