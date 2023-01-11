package com.codely.pollution.domain

import arrow.optics.optics
import com.codely.shared.aggregate.Aggregate
import com.codely.shared.event.pollution.PollutionReadingPerformedEvent
import com.codely.shared.pollution.PollutionLevel
import com.codely.shared.robot.domain.Location
import com.codely.shared.robot.domain.RobotId

@optics
data class PollutionReading(
    val id: PollutionReadingId,
    val location: Location,
    val robotId: RobotId,
    val pollutionLevel: PollutionLevel,
    val pollutionReadingDate: PollutionReadingDate
) : Aggregate() {

    companion object {
        fun create(
            id: PollutionReadingId,
            location: Location,
            robotId: RobotId,
            pollution: Int,
            date: PollutionReadingDate
        ) = PollutionReading(id, location, robotId, PollutionLevel.toPollutionLevel(pollution), date)
            .also {
                it.record(
                    PollutionReadingPerformedEvent(
                        id.value,
                        robotId.value,
                        it.pollutionLevel.pollution,
                        location.value
                    )
                )
            }
    }
}
