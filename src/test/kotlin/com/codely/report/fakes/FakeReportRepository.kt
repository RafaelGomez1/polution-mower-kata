package com.codely.report.fakes

import arrow.core.Either
import arrow.core.Either.Companion.catch
import com.codely.report.domain.Report
import com.codely.report.domain.ReportRepository
import com.codely.shared.FakeRepository
import com.codely.shared.robot.domain.RobotId

class FakeReportRepository : ReportRepository, FakeRepository<Report> {
    private val reports = mutableMapOf<RobotId, Report>()
    private var shouldFailFinding = false

    override suspend fun save(report: Report): Either<Throwable, Unit> = catch { reports[report.robotId] = report }

    override suspend fun existBy(robotId: RobotId): Either<Throwable, Boolean> = catch { reports.containsKey(robotId) }

    override suspend fun findBy(robotId: RobotId): Either<Throwable, Report> = catch {
        if (shouldFailFinding) {
            throw NoSuchElementException()
        } else {
            reports.getValue(robotId)
        }
    }

    override fun resetFake() {
        reports.clear()
        shouldFailFinding = false
    }

    override fun shouldFailOnFinding(result: Boolean) { shouldFailFinding = true }

    override fun assertResourceWasPersisted(resource: Report): Boolean = reports.containsValue(resource)
}
