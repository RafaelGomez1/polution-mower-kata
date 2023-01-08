package com.codely.robot.application.create

import arrow.core.Either
import arrow.core.flatMap
import com.codely.robot.application.exist.guardRobotExists
import com.codely.robot.application.save.saveOrElse
import com.codely.robot.domain.CreateRobotError
import com.codely.robot.domain.Robot
import com.codely.robot.domain.RobotRepository
import com.codely.robot.domain.Route
import com.codely.robot.domain.Speed
import com.codely.shared.robot.domain.Location
import com.codely.shared.robot.domain.RobotId

context(RobotRepository)
suspend fun createRobot(id: RobotId, speed: Speed, location: Location?, route: Route?): Either<CreateRobotError, Robot> =
    guardRobotExists(
        id = id,
        onUnexpectedError = { CreateRobotError.Unknown(it) },
        onResourceAlreadyExists = { CreateRobotError.RobotAlreadyExists }
    )
        .map { Robot.create(id, speed, location, route) }
        .flatMap { robot -> robot.saveOrElse { CreateRobotError.Unknown(it) } }
