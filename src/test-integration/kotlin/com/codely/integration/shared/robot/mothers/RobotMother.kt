package com.codely.integration.shared.robot.mothers

import com.codely.integration.shared.robot.RobotIdMother
import com.codely.robot.domain.DistanceTravelled
import com.codely.robot.domain.Robot
import com.codely.robot.domain.Route
import com.codely.robot.domain.Running
import com.codely.robot.domain.Speed
import com.codely.robot.mothers.DistanceTravelledMother
import com.codely.robot.mothers.RouteMother
import com.codely.robot.mothers.RunningMother
import com.codely.robot.mothers.SpeedMother
import com.codely.shared.robot.LocationMother
import com.codely.shared.robot.domain.Location
import com.codely.shared.robot.domain.RobotId

object RobotMother {
    fun invoke(
        id: RobotId = RobotIdMother.invoke(),
        speed: Speed = SpeedMother.invoke(),
        distance: DistanceTravelled = DistanceTravelledMother.invoke(),
        location: Location = LocationMother.invoke(),
        running: Running = RunningMother.invoke(),
        route: Route = RouteMother.invoke()
    ): Robot = Robot(id, speed, distance, location, running, route)

    fun withRealPolyline(
        id: RobotId = RobotIdMother.invoke(),
        speed: Speed = SpeedMother.invoke(),
        distance: DistanceTravelled = DistanceTravelledMother.invoke(),
        location: Location = LocationMother.fromPolyline(),
        running: Running = RunningMother.invoke(),
        route: Route = RouteMother.fromPolyline()
    ): Robot = Robot(id, speed, distance, location, running, route)
}
