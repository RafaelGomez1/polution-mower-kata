package com.codely.robot.application.route

import arrow.core.Either
import arrow.core.flatMap
import com.codely.robot.application.find.findRobot
import com.codely.robot.application.save.saveOrElse
import com.codely.robot.domain.Robot
import com.codely.robot.domain.RobotRepository
import com.codely.robot.domain.Route
import com.codely.robot.domain.RouteRobotError
import com.codely.shared.domain.robot.RobotId

context(RobotRepository)
suspend fun routeRobot(id: RobotId, route: Route): Either<RouteRobotError, Robot> =
    findRobot(
        id = id,
        onUnexpectedError = { RouteRobotError.Unknown(it) },
        onResourceNotFound = { RouteRobotError.RobotNotFound }
    )
        .map { robot -> robot.route(route) }
        .flatMap { robot -> robot.saveOrElse( onError = { RouteRobotError.Unknown(it) }) }