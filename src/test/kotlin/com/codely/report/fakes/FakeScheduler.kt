package com.codely.report.fakes

import arrow.core.Either
import arrow.core.Either.Companion.catch
import com.codely.report.domain.ReportScheduler
import com.codely.shared.robot.domain.RobotId
import kotlin.test.assertEquals

class FakeScheduler : ReportScheduler {
    private var scheduled = false

    override suspend fun schedule(robotId: RobotId): Either<Throwable, Unit> = catch { scheduled = true }

    override suspend fun deleteScheduler(robotId: RobotId): Either<Throwable, Unit> = catch { scheduled = false }

    infix fun FakeScheduler.shouldHaveActiveSchedule(result: Boolean) = assertEquals(result, scheduled)

    fun resetFake() { scheduled = false }
}
