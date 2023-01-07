package com.codely.robot.application.start

import arrow.core.Either
import com.codely.robot.application.StartRobotCommand
import com.codely.robot.domain.Robot
import com.codely.robot.domain.RobotRepository
import com.codely.robot.domain.StartRobotError
import com.codely.shared.domain.robot.RobotId
import com.codely.shared.event.bus.DomainEventPublisher

context(RobotRepository, DomainEventPublisher)
suspend fun handle(command: StartRobotCommand): Either<StartRobotError, Robot> =
    startRobot(id = RobotId(command.id))
