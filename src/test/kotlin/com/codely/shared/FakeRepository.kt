package com.codely.shared

interface FakeRepository<T> {
    fun resetFake()
    fun assertResourceWasPersisted(resource: T): Boolean
    fun shouldFailOnFinding(result: Boolean)
}
