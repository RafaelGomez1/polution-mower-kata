package com.codely.robot.domain

interface DelayCalculator {
    fun calculateDelay(distance: Double, speed: Double): Long?
}
