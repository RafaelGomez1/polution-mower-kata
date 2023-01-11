package com.codely.pollution.application.save

import arrow.core.Either
import com.codely.pollution.domain.PollutionReading
import com.codely.pollution.domain.PollutionReadingRepository

context(PollutionReadingRepository)
suspend fun <T> PollutionReading.saveOrElse(onError: (cause: Throwable) -> T): Either<T, PollutionReading> =
    save(this)
        .map { this }
        .mapLeft { error -> onError(error) }
