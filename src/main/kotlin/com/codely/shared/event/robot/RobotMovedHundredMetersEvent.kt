package com.codely.shared.event.robot

import com.codely.shared.event.bus.DomainEvent

data class RobotMovedHundredMetersEvent(val id: String) : DomainEvent(id)
