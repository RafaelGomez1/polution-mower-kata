package com.codely.pollution.domain

import arrow.core.Either

interface PollutionReader {
    suspend fun performReading(): Either<Throwable, Int>
}
