package com.codely.robot.application.find

import arrow.core.Either
import com.codely.robot.domain.Robot
import com.codely.robot.domain.RobotRepository
import com.codely.shared.robot.domain.RobotId

context(RobotRepository)
suspend fun <T> findRobot(id: RobotId, onResourceNotFound: () -> T, onUnexpectedError: (cause: Throwable) -> T): Either<T, Robot> =
    findBy(id)
        .mapLeft { error ->
            if (error is NoSuchElementException) {
                onResourceNotFound()
            } else {
                onUnexpectedError(error)
            }
        }
