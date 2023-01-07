package com.codely.robot.application.save

import arrow.core.Either
import com.codely.robot.domain.Robot
import com.codely.robot.domain.RobotRepository

context(RobotRepository)
suspend fun <T> Robot.saveOrElse(onError: (cause: Throwable) -> T): Either<T, Robot> =
    save(this)
        .map { this }
        .mapLeft { error -> onError(error) }
