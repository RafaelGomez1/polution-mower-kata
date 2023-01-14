package com.codely.robot.application.route

import arrow.core.Either
import com.codely.robot.application.RouteRobotCommand
import com.codely.robot.domain.Robot
import com.codely.robot.domain.RobotRepository
import com.codely.robot.domain.Route
import com.codely.robot.domain.RouteRobotError
import com.codely.shared.robot.domain.RobotId

context(RobotRepository)
suspend fun handle(command: RouteRobotCommand): Either<RouteRobotError, Robot> =
    routeRobot(id = RobotId(command.id), route = Route.fromPolyline(command.route))
