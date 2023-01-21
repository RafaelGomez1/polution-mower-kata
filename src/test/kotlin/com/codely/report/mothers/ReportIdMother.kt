package com.codely.report.mothers

import com.codely.report.domain.ReportId
import java.util.*

object ReportIdMother {

    fun invoke(value: UUID = UUID.randomUUID()) = ReportId(value)
}
