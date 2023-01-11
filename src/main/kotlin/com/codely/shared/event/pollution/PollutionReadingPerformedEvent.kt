package com.codely.shared.event.pollution

import com.codely.shared.event.bus.DomainEvent
import com.google.maps.model.LatLng

data class PollutionReadingPerformedEvent(
    val aggregateId: String,
    val robotId: String,
    val pollution: Int,
    val location: LatLng
) : DomainEvent(aggregateId)
