package com.codely.shared.event.bus

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Formatter
import java.util.UUID

abstract class DomainEvent(
    private var aggregateId: String,
    private var eventId: String = UUID.randomUUID().toString(),
    private var occurredOn: String = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
) {
    fun aggregateId(): String {
        return aggregateId
    }

    fun eventId(): String {
        return eventId
    }

    fun occurredOn(): String {
        return occurredOn
    }
}
