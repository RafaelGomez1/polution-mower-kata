package com.codely.report.domain

import arrow.optics.optics
import com.codely.shared.robot.domain.Location
import com.codely.shared.robot.domain.RobotId
import java.util.UUID

@optics
data class Report(
    val id: ReportId,
    val robotId: RobotId,
    val readings: NumberOfReading,
    val location: Location? = null,
    val pollution: PollutionQuantity
) {
    companion object {
        fun create(
            robotId: RobotId,
            id: ReportId,
            location: Location? = null,
            readings: NumberOfReading = 0,
            pollution: PollutionQuantity = PollutionQuantity(0)
        ) = Report(id, robotId, readings, location, pollution)
    }
}

typealias NumberOfReading = Int

@JvmInline
value class ReportId(val value: UUID)

@JvmInline
value class PollutionQuantity(val value: Int)
