package com.codely.report.application.save

import arrow.core.Either
import com.codely.report.domain.Report
import com.codely.report.domain.ReportRepository

context(ReportRepository)
suspend fun <T> Report.saveOrElse(onError: (cause: Throwable) -> T): Either<T, Report> =
    save(this)
        .map { this }
        .mapLeft { error -> onError(error) }
