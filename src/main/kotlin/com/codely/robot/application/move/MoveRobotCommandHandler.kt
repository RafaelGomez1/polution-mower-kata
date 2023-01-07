package com.codely.robot.application.move

import arrow.core.Either
import com.codely.robot.application.MoveRobotCommand
import com.codely.robot.domain.DistanceCalculator
import com.codely.robot.domain.MoveRobotError
import com.codely.robot.domain.Robot
import com.codely.robot.domain.RobotRepository
import com.codely.shared.domain.robot.RobotId
import com.codely.shared.event.bus.DomainEventPublisher

context(RobotRepository, DomainEventPublisher, DistanceCalculator)
suspend fun handle(command: MoveRobotCommand): Either<MoveRobotError, Robot> =
    moveRobot(id = RobotId(command.id))