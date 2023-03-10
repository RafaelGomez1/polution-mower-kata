package com.codely.robot.application.move

import arrow.core.Either
import arrow.core.continuations.either
import arrow.core.left
import com.codely.robot.application.find.findRobot
import com.codely.robot.application.save.saveOrElse
import com.codely.robot.domain.DistanceCalculator
import com.codely.robot.domain.MoveRobotError
import com.codely.robot.domain.Robot
import com.codely.robot.domain.RobotRepository
import com.codely.shared.event.bus.DomainEventPublisher
import com.codely.shared.event.bus.publishEventsOrElse
import com.codely.shared.robot.domain.RobotId
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

context(RobotRepository, DomainEventPublisher, DistanceCalculator)
suspend fun moveRobot(id: RobotId): Either<MoveRobotError, Robot> =
    either {
        var robot = findRobot(
            id = id,
            onUnexpectedError = { MoveRobotError.Unknown(it) },
            onResourceNotFound = { MoveRobotError.RobotNotFound }
        ).bind()

        logger.info { "Robot starting route" }

        robot.route?.points?.forEach { coordinate ->
            robot = robot.moveToNextLocation(coordinate, distanceBetweenLocation(robot.location!!.value, coordinate))
            robot.saveOrElse { MoveRobotError.Unknown(it) }.bind()
            publish(robot.pullDomainEvents())
        } ?: MoveRobotError.RouteNotAssignedToRobot.left().bind()

        logger.info { "Robot finished route" }

        robot
            .endTrip()
            .saveOrElse { MoveRobotError.Unknown(it) }.bind()
            .publishEventsOrElse { MoveRobotError.Unknown(it) }.bind()
    }
