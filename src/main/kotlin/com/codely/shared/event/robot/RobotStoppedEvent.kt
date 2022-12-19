package com.codely.shared.event.robot

import com.codely.shared.event.DomainEvent

data class RobotStoppedEvent(val aggregateId: String) : DomainEvent(aggregateId)