package com.codely.robot.mothers

import com.codely.robot.domain.DistanceTravelled

object DistanceTravelledMother {
    fun invoke(value: Double = Math.random()): DistanceTravelled = DistanceTravelled(value)
}
