package com.codely.shared.pollution

import com.codely.pollution.mothers.PollutionLevelMother
import com.codely.pollution.mothers.PollutionReadingIdMother
import com.codely.shared.event.pollution.PollutionReadingPerformedEvent
import com.codely.shared.robot.LocationMother
import com.codely.shared.robot.RobotIdMother
import com.google.maps.model.LatLng

object PollutionReadingPerformedEventMother {

    fun invoke(
        aggregateId: String = PollutionReadingIdMother.invoke().value,
        robotId: String = RobotIdMother.invoke().value,
        pollution: Int = PollutionLevelMother.invoke().pollution,
        location: LatLng = LocationMother.invoke().value
    ) = PollutionReadingPerformedEvent(aggregateId, robotId, pollution, location)
}
