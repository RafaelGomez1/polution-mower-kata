package com.codely.report.secondaryadapter.scheduler

import com.codely.report.domain.ReportRepository
import com.codely.shared.robot.domain.RobotId
import com.fasterxml.jackson.databind.json.JsonMapper
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.springframework.stereotype.Component

@Component
class QuartzReportGenerator(
    private val repository: ReportRepository
) : Job {

    private val logger = KotlinLogging.logger {}

    override fun execute(jobExecutionContext: JobExecutionContext) {
        val jobDetail = jobExecutionContext.jobDetail
        val robotId: RobotId = jobDetail.jobDataMap["robotId"] as RobotId

        runBlocking {
            repository.findBy(robotId)
                .map { report -> logger.info { JsonMapper().writeValueAsString(report) } }
                .mapLeft { error -> logger.error { "report not found for robot ${robotId.value} due to $error" } }
        }
    }
}
