package com.codely.robot.domain

import arrow.core.Either
import arrow.core.left
import arrow.core.right

@JvmInline
value class Speed private constructor(val value: Double) {

    companion object {
        private const val MINIMUM_ALLOWED_SPEED = 1.0
        private const val MAXIMUM_ALLOWED_SPEED = 3.0

        fun from(speed: Double): Either<InvalidSpeedException, Speed> =
            if (speed < MINIMUM_ALLOWED_SPEED || speed > MAXIMUM_ALLOWED_SPEED) InvalidSpeedException("not a valid speed").left()
            else Speed(speed).right()
    }
}

class InvalidSpeedException(message: String)