package com.codely.robot.domain

import arrow.optics.optics
import com.codely.shared.aggregate.Aggregate
import com.codely.shared.domain.robot.Location
import com.codely.shared.domain.robot.RobotId
import com.codely.shared.event.robot.RobotStartedEvent
import com.google.maps.model.LatLng

@optics
data class Robot private constructor(
    val id: RobotId,
    val speed: Speed,
    val distance: DistanceTravelled,
    val location: Location,
    val running: Running
) : Aggregate() {

    fun stop() =
        copy(running = Running(false))

    fun start() =
        copy(running = Running(true))
            .also { it.record(RobotStartedEvent(id.value)) }

    fun updateLocation(newLocation: LatLng) = copy(location = Location(newLocation))

    fun addTravelledDistance(travelledDistance: Double) = copy(distance = DistanceTravelled(distance.value + travelledDistance))

    fun resetTravelledDistance(readingDistance: Double) = copy(distance = DistanceTravelled(distance.value - readingDistance))

    companion object {
        fun create(id: RobotId, speed: Speed, location: Location, running: Running): Robot =
            Robot(id, speed, DistanceTravelled(0.0), location, running)
                .also { it.record(RobotStartedEvent(id.value)) }
    }
}