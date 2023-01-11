package com.codely.shared.pollution

enum class PollutionLevel(val pollution: Int) {
    GOOD(0), MODERATE(51), USG(101), UNHEALTHY(151);

    companion object {
        fun toPollutionLevel(value: Int): PollutionLevel =
            when {
                value < MODERATE.pollution -> GOOD
                value < USG.pollution -> MODERATE
                value < UNHEALTHY.pollution -> USG
                else -> UNHEALTHY
            }
    }
}
