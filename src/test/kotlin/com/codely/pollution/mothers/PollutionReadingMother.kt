package com.codely.pollution.mothers

import com.codely.pollution.domain.PollutionReading
import com.codely.pollution.domain.PollutionReadingDate
import com.codely.pollution.domain.PollutionReadingId
import com.codely.shared.pollution.PollutionLevel
import com.codely.shared.robot.LocationMother
import com.codely.shared.robot.RobotIdMother
import com.codely.shared.robot.domain.Location
import com.codely.shared.robot.domain.RobotId

object PollutionReadingMother {

    fun invoke(
        id: PollutionReadingId = PollutionReadingIdMother.invoke(),
        location: Location = LocationMother.invoke(),
        robotId: RobotId = RobotIdMother.invoke(),
        pollutionLevel: PollutionLevel = PollutionLevelMother.invoke(),
        pollutionReadingDate: PollutionReadingDate = PollutionReadingDateMother.invoke()
    ) = PollutionReading(id, location, robotId, pollutionLevel, pollutionReadingDate)
}
