package com.codely.robot.secondaryadapter.delay

import com.codely.robot.domain.DelayCalculator
import com.codely.shared.configuration.RobotConfiguration
import org.springframework.stereotype.Component

@Component
class SpeedDelayCalculator(private val configuration: RobotConfiguration) : DelayCalculator {

    override fun calculateDelay(distance: Double, speed: Double): Long =
        Math.round(distance / speed) * configuration.delayTimeUnit
}
