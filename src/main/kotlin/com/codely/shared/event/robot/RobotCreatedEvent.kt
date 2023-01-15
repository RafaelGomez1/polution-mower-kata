package com.codely.shared.event.robot

import com.codely.shared.event.bus.DomainEvent
import com.google.maps.model.LatLng

data class RobotCreatedEvent(val id: String, val location: LatLng?) : DomainEvent(id)
