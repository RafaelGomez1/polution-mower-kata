package com.codely.pollution.application.read

import arrow.core.Either
import arrow.core.flatMap
import com.codely.pollution.application.ReadPollutionError
import com.codely.pollution.application.save.saveOrElse
import com.codely.pollution.domain.PollutionReader
import com.codely.pollution.domain.PollutionReading
import com.codely.pollution.domain.PollutionReadingDate
import com.codely.pollution.domain.PollutionReadingId
import com.codely.pollution.domain.PollutionReadingRepository
import com.codely.shared.event.bus.DomainEventPublisher
import com.codely.shared.event.bus.publishEventsOrElse
import com.codely.shared.robot.domain.Location
import com.codely.shared.robot.domain.RobotId

context(PollutionReadingRepository, PollutionReader, DomainEventPublisher)
suspend fun readPollution(id: PollutionReadingId, location: Location, robotId: RobotId, readingDate: PollutionReadingDate): Either<ReadPollutionError, PollutionReading> =
    performReading()
        .mapLeft { error -> ReadPollutionError.Unknown(error) }
        .map { reading -> PollutionReading.create(id, location, robotId, reading, readingDate) }
        .flatMap { pollutionReading -> pollutionReading.saveOrElse { ReadPollutionError.Unknown(it) } }
        .flatMap { pollutionReading -> pollutionReading.publishEventsOrElse { ReadPollutionError.Unknown(it) } }
