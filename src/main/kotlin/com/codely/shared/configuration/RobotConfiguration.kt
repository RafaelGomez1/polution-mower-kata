package com.codely.shared.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "pollution.robot")
class RobotConfiguration(
    var speed: Double = 0.0,
    var delayTimeUnit: Int = 0
)
