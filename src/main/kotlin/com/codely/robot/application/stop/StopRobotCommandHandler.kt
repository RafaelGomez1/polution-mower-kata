package com.codely.robot.application.stop

import arrow.core.Either
import com.codely.robot.application.StopRobotCommand
import com.codely.robot.domain.Robot
import com.codely.robot.domain.RobotRepository
import com.codely.robot.domain.StopRobotError
import com.codely.shared.domain.robot.RobotId
import com.codely.shared.event.bus.DomainEventPublisher
import org.axonframework.commandhandling.CommandHandler

context(RobotRepository, DomainEventPublisher)
suspend fun handle(command: StopRobotCommand): Either<StopRobotError, Robot> =
    stopRobot(id = RobotId(command.id))