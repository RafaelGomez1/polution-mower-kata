package com.codely.pollution.application.read

import arrow.core.Either
import com.codely.pollution.application.ReadPollutionError
import com.codely.pollution.domain.PollutionReader
import com.codely.pollution.domain.PollutionReading
import com.codely.pollution.domain.PollutionReadingDate
import com.codely.pollution.domain.PollutionReadingId
import com.codely.pollution.domain.PollutionReadingRepository
import com.codely.shared.event.bus.DomainEventPublisher
import com.codely.shared.robot.domain.Location
import com.codely.shared.robot.domain.RobotId
import com.google.maps.model.LatLng
import java.time.ZonedDateTime
import java.util.*

context(PollutionReadingRepository, PollutionReader, DomainEventPublisher)
suspend fun handle(command: ReadPollutionCommand): Either<ReadPollutionError, PollutionReading> =
    readPollution(
        id = PollutionReadingId(value = UUID.randomUUID().toString()),
        location = Location(value = command.location),
        robotId = RobotId(value = command.robotId),
        readingDate = PollutionReadingDate(value = ZonedDateTime.now())
    )

sealed class PollutionReadingCommands

data class ReadPollutionCommand(val robotId: String, val location: LatLng) : PollutionReadingCommands()
