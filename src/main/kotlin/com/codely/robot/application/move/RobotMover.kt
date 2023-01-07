package com.codely.robot.application.move

import arrow.core.Either
import arrow.core.continuations.either
import com.codely.robot.application.find.findRobot
import com.codely.robot.application.save.saveOrElse
import com.codely.robot.domain.DistanceCalculator
import com.codely.robot.domain.MoveRobotError
import com.codely.robot.domain.Robot
import com.codely.robot.domain.RobotRepository
import com.codely.shared.domain.robot.RobotId
import com.codely.shared.event.bus.DomainEventPublisher

context(RobotRepository, DomainEventPublisher, DistanceCalculator)
suspend fun moveRobot(id: RobotId): Either<MoveRobotError, Robot> =
    either {
        var robot = findRobot(
            id = id,
            onUnexpectedError = { MoveRobotError.Unknown(it) },
            onResourceNotFound = { MoveRobotError.RobotNotFound }
        ).bind()
        robot.route?.points?.map { coordinate ->
            // Publishes event if the robot moved over 100 m
            robot = robot.moveToNextLocation(coordinate, distanceBetweenLocation(robot.location.value, coordinate))
            robot.saveOrElse { MoveRobotError.Unknown(it) }.bind()
            publish(robot.pullDomainEvents())
        }

        robot.saveOrElse { MoveRobotError.Unknown(it) }.bind()

        // Delete Report Configuration
    }
