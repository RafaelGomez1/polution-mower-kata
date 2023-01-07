package com.codely.shared.event.robot

import com.codely.shared.event.bus.DomainEvent

data class RobotStartedEvent(val aggregateId: String) : DomainEvent(aggregateId)