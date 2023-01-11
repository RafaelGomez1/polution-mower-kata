package com.codely.pollution.mothers

import com.codely.pollution.domain.PollutionReadingDate
import java.time.ZonedDateTime

object PollutionReadingDateMother {

    fun invoke(value: ZonedDateTime = ZonedDateTime.now()) = PollutionReadingDate(value)
}
