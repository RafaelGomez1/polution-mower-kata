package com.codely.report.mothers

import com.codely.report.domain.NumberOfReading
import com.codely.report.domain.PollutionQuantity
import com.codely.report.domain.Report
import com.codely.report.domain.ReportId
import com.codely.shared.robot.LocationMother
import com.codely.shared.robot.RobotIdMother
import com.codely.shared.robot.domain.Location
import com.codely.shared.robot.domain.RobotId
import kotlin.random.Random

object ReportMother {

    fun invoke(
        id: ReportId = ReportIdMother.invoke(),
        robotId: RobotId = RobotIdMother.invoke(),
        readings: NumberOfReading = Random.nextInt(),
        location: Location = LocationMother.invoke(),
        pollution: PollutionQuantity = PollutionQuantityMother.invoke()
    ) = Report(id, robotId, readings, location, pollution)
}
