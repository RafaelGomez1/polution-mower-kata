package com.codely.shared.robot

import com.codely.robot.domain.DistanceTravelled


object DistanceTravelledMother {
    fun invoke(value: Double = Math.random()): DistanceTravelled = DistanceTravelled(value)
}