package com.codely.pollution.mothers

import com.codely.pollution.domain.PollutionReadingId
import java.util.*

object PollutionReadingIdMother {

    fun invoke(value: String = UUID.randomUUID().toString()) = PollutionReadingId(value)
}
