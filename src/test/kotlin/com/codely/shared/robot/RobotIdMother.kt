package com.codely.shared.robot

import com.codely.shared.domain.robot.RobotId
import java.util.*

object RobotIdMother {

    fun invoke(value: String = UUID.randomUUID().toString()) = RobotId(value)
}