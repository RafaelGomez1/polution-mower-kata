package com.codely.pollution.domain

import arrow.core.Either

interface PollutionReadingRepository {
    suspend fun save(reading: PollutionReading): Either<Throwable, Unit>
}
