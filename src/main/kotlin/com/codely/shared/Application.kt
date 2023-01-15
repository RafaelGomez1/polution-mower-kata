package com.codely.shared

import com.codely.report.secondaryadapter.scheduler.ReportConfiguration
import com.codely.shared.configuration.robot.RobotConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@EnableConfigurationProperties(RobotConfiguration::class, ReportConfiguration::class)
@ComponentScan("com.codely")
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
