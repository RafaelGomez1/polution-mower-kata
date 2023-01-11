package com.codely.pollution.mothers

import com.codely.shared.pollution.PollutionLevel
import kotlin.random.Random

object PollutionLevelMother {

    fun invoke(value: Int = Random.nextInt(0, 100)): PollutionLevel = PollutionLevel.toPollutionLevel(value)
}
