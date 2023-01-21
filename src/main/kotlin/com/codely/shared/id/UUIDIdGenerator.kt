package com.codely.shared.id

import org.springframework.stereotype.Component
import java.util.*

@Component
class UUIDIdGenerator : IdGenerator {
    override suspend fun generate(): UUID = UUID.randomUUID()
}
