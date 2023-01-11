package com.codely.pollution.secondaryadapter.database

import arrow.core.Either
import com.codely.pollution.domain.PollutionReading
import com.codely.pollution.domain.PollutionReadingId
import com.codely.pollution.domain.PollutionReadingRepository
import org.springframework.stereotype.Repository

@Repository
class InMemoryPollutionRepository : PollutionReadingRepository {
    private val readings = mutableMapOf<PollutionReadingId, PollutionReading>()

    override suspend fun save(reading: PollutionReading): Either<Throwable, Unit> =
        Either.catch { readings.put(reading.id, reading) }
            .map { }
}
