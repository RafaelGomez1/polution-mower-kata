package com.codely.robot.mothers

import com.codely.robot.domain.Speed
import kotlin.random.Random

object SpeedMother {
    fun invoke(value: Double = Random.nextDouble(1.0, 3.0)) = Speed(value)
}
