package com.codely.robot.application.stop

import arrow.core.Either
import arrow.core.flatMap
import com.codely.robot.application.find.findRobot
import com.codely.robot.application.save.saveOrElse
import com.codely.robot.domain.Robot
import com.codely.robot.domain.RobotRepository
import com.codely.robot.domain.StopRobotError
import com.codely.shared.event.bus.DomainEventPublisher
import com.codely.shared.robot.domain.RobotId

context(RobotRepository, DomainEventPublisher)
suspend fun stopRobot(id: RobotId): Either<StopRobotError, Robot> =
    findRobot(
        id = id,
        onUnexpectedError = { StopRobotError.Unknown(it) },
        onResourceNotFound = { StopRobotError.RobotNotFound }
    ).flatMap { robot -> robot.stop() }
        .flatMap { robot -> robot.saveOrElse(onError = { StopRobotError.Unknown(it) }) }
        .map { robot -> publish(robot.pullDomainEvents()).let { robot } }
