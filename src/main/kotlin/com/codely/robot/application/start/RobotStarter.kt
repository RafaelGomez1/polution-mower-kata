package com.codely.robot.application.start

import arrow.core.Either
import arrow.core.flatMap
import com.codely.robot.application.find.findRobot
import com.codely.robot.application.save.saveOrElse
import com.codely.robot.domain.Robot
import com.codely.robot.domain.RobotRepository
import com.codely.robot.domain.StartRobotError
import com.codely.shared.event.bus.DomainEventPublisher
import com.codely.shared.robot.domain.RobotId

context(RobotRepository, DomainEventPublisher)
suspend fun startRobot(id: RobotId): Either<StartRobotError, Robot> =
    findRobot(
        id = id,
        onUnexpectedError = { StartRobotError.Unknown(it) },
        onResourceNotFound = { StartRobotError.RobotNotFound }
    ).flatMap { robot -> robot.start() }
        .flatMap { robot -> robot.saveOrElse { StartRobotError.Unknown(it) } }
        .map { robot -> publish(robot.pullDomainEvents()).let { robot } }
