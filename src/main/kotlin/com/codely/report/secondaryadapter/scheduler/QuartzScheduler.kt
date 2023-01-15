package com.codely.report.secondaryadapter.scheduler

import arrow.core.Either
import com.codely.report.domain.ReportScheduler
import com.codely.shared.robot.domain.RobotId
import org.quartz.Job
import org.quartz.JobBuilder
import org.quartz.JobDataMap
import org.quartz.JobDetail
import org.quartz.JobKey
import org.quartz.Scheduler
import org.quartz.SimpleScheduleBuilder
import org.quartz.SimpleTrigger
import org.quartz.TriggerBuilder
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

@Component
class QuartzScheduler(
    private val scheduler: Scheduler,
    private val configuration: ReportConfiguration
) : ReportScheduler {

    override suspend fun schedule(robotId: RobotId): Either<Throwable, Unit> =
        Either.catch {
            if (!checkExistingScheduler(robotId)) {
                scheduler.scheduleJob(
                    buildJobDetail(robotId, QuartzReportGenerator::class.java),
                    buildTrigger(robotId, configuration)
                )
            }
        }

    override suspend fun deleteScheduler(robotId: RobotId): Either<Throwable, Unit> =
        Either.catch { scheduler.clear() }

    private fun checkExistingScheduler(id: RobotId): Boolean =
        scheduler.checkExists(JobKey(identityName(REPORT, id)))

    private fun buildJobDetail(id: RobotId, jobClass: Class<out Job>): JobDetail? {
        val jobDataMap = JobDataMap()
        jobDataMap["robotId"] = id
        return JobBuilder
            .newJob(jobClass)
            .withIdentity(identityName(REPORT, id))
            .setJobData(jobDataMap)
            .build()
    }

    private fun buildTrigger(id: RobotId, configuration: ReportConfiguration): SimpleTrigger? =
        TriggerBuilder.newTrigger()
            .withIdentity(identityName(TRIGGER, id))
            .startAt(calculateStartingDate(configuration.reportRate))
            .withSchedule(
                SimpleScheduleBuilder.simpleSchedule()
                    .repeatForever()
                    .withIntervalInMilliseconds(configuration.reportRate.toLong())
            ).build()

    private fun calculateStartingDate(reportRate: Int): Date? {
        val calendar = Calendar.getInstance()
        val date = Date(Instant.now().toEpochMilli())
        calendar.time = date
        calendar.add(Calendar.MILLISECOND, reportRate)
        return calendar.time
    }

    private fun identityName(identityType: String, robotId: RobotId) = identityType + robotId.value + SEPARATOR + configuration.groupName

    companion object {
        private const val REPORT = "Report-"
        private const val SEPARATOR = "-"
        private const val TRIGGER = "Trigger-"
    }
}
