package com.codely.shared.robot

import com.codely.robot.domain.Running
import kotlin.random.Random

object RunningMother {
    fun invoke(value: Boolean = Random.nextBoolean()): Running = Running(value)
}
