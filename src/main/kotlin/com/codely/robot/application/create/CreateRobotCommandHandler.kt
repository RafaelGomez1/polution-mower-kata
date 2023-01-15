package com.codely.robot.application.create

import arrow.core.Either
import com.codely.robot.application.CreateRobotCommand
import com.codely.robot.domain.CreateRobotError
import com.codely.robot.domain.Robot
import com.codely.robot.domain.RobotRepository
import com.codely.robot.domain.Route
import com.codely.robot.domain.Speed
import com.codely.shared.event.bus.DomainEventPublisher
import com.codely.shared.robot.domain.Location
import com.codely.shared.robot.domain.RobotId

context(RobotRepository, DomainEventPublisher)
suspend fun handle(command: CreateRobotCommand): Either<CreateRobotError, Robot> =
    createRobot(
        id = RobotId(command.id),
        speed = Speed(command.speed),
        location = command.route?.let { Location.fromPolyline(it) },
        route = command.route?.let { Route.fromPolyline(it) }
    )
