package com.codely.robot.domain

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import arrow.optics.optics
import com.codely.shared.aggregate.Aggregate
import com.codely.shared.event.robot.RobotCreatedEvent
import com.codely.shared.event.robot.RobotMovedHundredMetersEvent
import com.codely.shared.event.robot.RobotStartedEvent
import com.codely.shared.event.robot.RobotStoppedEvent
import com.codely.shared.robot.domain.Location
import com.codely.shared.robot.domain.RobotId
import com.google.maps.model.LatLng

@optics
data class Robot(
    val id: RobotId,
    val speed: Speed = Speed(2.0),
    val distance: DistanceTravelled = DistanceTravelled(0.0),
    val location: Location?,
    val running: Running = Running(false),
    val route: Route? = null
) : Aggregate() {

    fun stop(): Either<StopRobotError, Robot> =
        if (!running.isActive) {
            StopRobotError.RobotAlreadyStopped.left()
        } else {
            copy(running = Running(false))
                .also { it.record(RobotStoppedEvent(id.value)) }.right()
        }

    fun start(): Either<StartRobotError, Robot> =
        if (running.isActive) {
            StartRobotError.RobotAlreadyStarted.left()
        } else {
            copy(running = Running(true))
                .also { it.record(RobotStartedEvent(id.value)) }.right()
        }

    fun route(route: Route) = copy(route = route)

    fun moveToNextLocation(newLocation: LatLng, travelledDistance: Double): Robot =
        if (distance.value + travelledDistance >= NOTIFICATION_DISTANCE) {
            copy(location = Location(newLocation), distance = DistanceTravelled(distance.value + travelledDistance - NOTIFICATION_DISTANCE))
                .also { it.record(RobotMovedHundredMetersEvent(id.value, location!!.value)) }
        } else {
            copy(location = Location(newLocation), distance = DistanceTravelled(distance.value + travelledDistance))
        }

    companion object {
        private const val NOTIFICATION_DISTANCE = 100.00
        fun create(id: RobotId, speed: Speed, location: Location?, route: Route?): Robot =
            Robot(id = id, speed = speed, location = location, route = route)
                .also { it.record(RobotCreatedEvent(id.value, location?.value)) }
    }
}
