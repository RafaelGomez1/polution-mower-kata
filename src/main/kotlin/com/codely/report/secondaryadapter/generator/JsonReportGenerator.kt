package com.codely.report.secondaryadapter.generator

import arrow.core.Either
import arrow.core.right
import com.codely.report.domain.Report
import com.codely.report.domain.ReportGenerator
import com.fasterxml.jackson.databind.json.JsonMapper
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class JsonReportGenerator : ReportGenerator {

    private val logger = KotlinLogging.logger {}

    override suspend fun generate(report: Report): Either<Throwable, Unit> =
        Unit.right()
            .also { logger.info { JsonMapper().writeValueAsString(report) } }
}
