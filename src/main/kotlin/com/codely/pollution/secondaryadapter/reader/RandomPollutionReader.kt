package com.codely.pollution.secondaryadapter.reader

import arrow.core.Either
import com.codely.pollution.domain.PollutionReader
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class RandomPollutionReader : PollutionReader {
    override suspend fun performReading(): Either<Throwable, Int> = Either.catch { Random.nextInt(0, 150) }
}
