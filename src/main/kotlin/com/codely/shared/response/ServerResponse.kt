package com.codely.shared.response

import arrow.core.Either
import org.springframework.http.ResponseEntity

fun <E> Either<E, Any>.toServerResponse(toError: (E) -> ResponseEntity<Void>): ResponseEntity<Void> =
    fold({ toError(it) }, { ResponseEntity.status(200).build() })
