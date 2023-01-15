package com.codely.report.secondaryadapter.database

import arrow.core.Either
import com.codely.report.domain.Report
import com.codely.report.domain.ReportRepository
import com.codely.shared.robot.domain.RobotId
import org.springframework.stereotype.Repository

@Repository
class InMemoryReportRepository : ReportRepository {
    private val reports = mutableMapOf<RobotId, Report>()

    override suspend fun save(report: Report): Either<Throwable, Unit> =
        Either.catch { reports.put(report.robotId, report) }
            .map { }

    override suspend fun existBy(robotId: RobotId): Either<Throwable, Boolean> =
        Either.catch { reports.containsKey(robotId) }

    override suspend fun findBy(robotId: RobotId): Either<Throwable, Report> =
        Either.catch { reports.getValue(robotId) }
}
