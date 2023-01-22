package com.codely.report.domain

import arrow.core.Either

interface ReportGenerator {
    suspend fun generate(report: Report): Either<Throwable, Unit>
}

suspend fun ReportGenerator.generateOrElse(report: Report, onError: (cause: Throwable) -> GenerateReportError): Either<GenerateReportError, Unit> =
    generate(report)
        .mapLeft { error -> onError(error) }
