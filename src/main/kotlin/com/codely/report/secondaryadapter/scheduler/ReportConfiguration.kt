package com.codely.report.secondaryadapter.scheduler

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "pollution.report")
class ReportConfiguration(
    var groupName: String = "",
    var reportRate: Int = 0
)
