package com.codely.report.mothers

import com.codely.report.domain.PollutionQuantity
import kotlin.random.Random

object PollutionQuantityMother {

    fun invoke(value: Int = Random.nextInt()) = PollutionQuantity(value)
}
