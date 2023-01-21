package com.codely.shared.id

import java.util.*

class FakeIdGenerator : IdGenerator {
    override suspend fun generate(): UUID = UUID.fromString("c2556879-cc25-4f00-90dc-145aa5240f87")
}
