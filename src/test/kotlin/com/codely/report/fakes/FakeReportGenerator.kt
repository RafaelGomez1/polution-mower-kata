package com.codely.report.fakes

import arrow.core.Either
import com.codely.report.domain.Report
import com.codely.report.domain.ReportGenerator
import kotlin.test.assertEquals

class FakeReportGenerator : ReportGenerator {
    private var reportWasGenerated = false

    override suspend fun generate(report: Report): Either<Throwable, Unit> = Either.catch { reportWasGenerated = true }

    infix fun shouldHaveBeenGenerated(result: Boolean) = assertEquals(result, reportWasGenerated)
    fun resetFake() { reportWasGenerated = false }
}
