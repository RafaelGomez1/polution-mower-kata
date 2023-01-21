package com.codely.shared.id

import java.util.UUID

interface IdGenerator {
    suspend fun generate(): UUID
}
