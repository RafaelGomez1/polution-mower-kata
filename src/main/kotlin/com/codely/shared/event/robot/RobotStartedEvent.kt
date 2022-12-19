package com.codely.shared.event.robot

import com.codely.shared.event.DomainEvent

data class RobotStartedEvent(val aggregateId: String) : DomainEvent(aggregateId)