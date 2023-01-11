package com.codely.shared.event.bus

import arrow.core.Either
import com.codely.shared.aggregate.Aggregate

context(DomainEventPublisher)
suspend fun <T : Aggregate, E> T.publishEventsOrElse(onError: (cause: Throwable) -> E): Either<E, T> =
    Either.catch { publish(pullDomainEvents()) }
        .mapLeft { onError(it) }
        .map { this }
