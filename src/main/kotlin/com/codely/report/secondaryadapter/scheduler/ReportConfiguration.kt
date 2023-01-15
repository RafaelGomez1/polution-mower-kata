package com.codely.report.secondaryadapter.scheduler

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "pollution.report")
class ReportConfiguration(
    val groupName: String,
    val reportRate: Int
)
