package com.codely.integration.shared.robot

import com.codely.shared.robot.domain.RobotId
import java.util.*

object RobotIdMother {

    fun invoke(value: String = UUID.randomUUID().toString()) = RobotId(value)
}
