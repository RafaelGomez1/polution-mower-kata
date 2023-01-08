package com.codely.robot.application.start

import arrow.core.Either
import com.codely.robot.application.StartRobotCommand
import com.codely.robot.domain.Robot
import com.codely.robot.domain.RobotRepository
import com.codely.robot.domain.StartRobotError
import com.codely.shared.event.bus.DomainEventPublisher
import com.codely.shared.robot.domain.RobotId

context(RobotRepository, DomainEventPublisher)
suspend fun handle(command: StartRobotCommand): Either<StartRobotError, Robot> =
    startRobot(id = RobotId(command.id))
