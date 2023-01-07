package com.codely.shared.response

import arrow.core.Either
import org.springframework.http.ResponseEntity

fun <E, R : Any> Either<E, R>.toServerResponse(toError: (E) -> ResponseEntity<Void>): ResponseEntity<Void> =
    fold({ toError(it) }, { ResponseEntity.status(200).build() })
