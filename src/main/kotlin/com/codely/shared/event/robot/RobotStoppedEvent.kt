package com.codely.shared.event.robot

import com.codely.shared.event.bus.DomainEvent

data class RobotStoppedEvent(val aggregateId: String) : DomainEvent(aggregateId)
