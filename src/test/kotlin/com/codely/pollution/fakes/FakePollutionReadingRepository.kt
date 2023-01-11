package com.codely.pollution.fakes

import arrow.core.Either
import com.codely.pollution.domain.PollutionReading
import com.codely.pollution.domain.PollutionReadingId
import com.codely.pollution.domain.PollutionReadingRepository
import com.codely.shared.FakeRepository

class FakePollutionReadingRepository : PollutionReadingRepository, FakeRepository<PollutionReading> {
    private val readings = mutableMapOf<PollutionReadingId, PollutionReading>()

    override fun resetFake() {
        readings.clear()
    }

    override fun shouldFailOnFinding(result: Boolean) { Unit }
    override fun assertResourceWasPersisted(resource: PollutionReading): Boolean = readings.containsKey(resource.id)

    override suspend fun save(reading: PollutionReading): Either<Throwable, Unit> =
        Either.catch { readings.put(reading.id, reading) }
            .map { }
}
