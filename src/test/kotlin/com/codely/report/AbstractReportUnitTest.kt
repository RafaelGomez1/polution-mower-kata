package com.codely.report

import com.codely.report.domain.Report
import com.codely.report.fakes.FakeReportRepository
import com.codely.report.fakes.FakeScheduler
import com.codely.shared.publisher.FakeDomainEventPublisher
import org.junit.jupiter.api.BeforeEach
import kotlin.test.assertFalse
import kotlin.test.assertTrue

abstract class AbstractReportUnitTest {

    val repository = FakeReportRepository()
    val publisher = FakeDomainEventPublisher()
    val scheduler = FakeScheduler()

    @BeforeEach
    internal fun tearDown() {
        repository.resetFake()
        publisher.resetFake()
        scheduler.resetFake()
    }

    // Repository Configurations
    suspend fun `report does exist`(report: Report) = repository.save(report)
    fun `report does not exist`() = repository.shouldFailOnFinding(true)

    // Repository assertions
    fun `assert report was persisted`(report: Report) = assertTrue { repository.assertResourceWasPersisted(report) }
    fun `assert report was not persisted`(report: Report) = assertFalse { repository.assertResourceWasPersisted(report) }
}
