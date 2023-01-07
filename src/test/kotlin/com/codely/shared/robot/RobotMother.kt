package com.codely.shared.robot

import com.codely.robot.domain.DistanceTravelled
import com.codely.robot.domain.Robot
import com.codely.robot.domain.Route
import com.codely.robot.domain.Running
import com.codely.robot.domain.Speed
import com.codely.shared.domain.robot.Location
import com.codely.shared.domain.robot.RobotId

object RobotMother {
    fun invoke(
        id: RobotId = RobotIdMother.invoke(),
        speed: Speed = SpeedMother.invoke(),
        distance: DistanceTravelled = DistanceTravelledMother.invoke(),
        location: Location = LocationMother.invoke(),
        running: Running = RunningMother.invoke(),
        route: Route = RouteMother.invoke()
    ): Robot = Robot(id, speed, distance, location, running, route)
}