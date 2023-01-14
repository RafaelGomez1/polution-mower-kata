package com.codely.shared.robot

import com.codely.integration.shared.robot.RobotIdMother
import com.codely.shared.event.robot.RobotMovedHundredMetersEvent
import com.google.maps.model.LatLng

object RobotMovedHundredMetersEventMother {

    fun invoke(
        id: String = RobotIdMother.invoke().value,
        location: LatLng = LocationMother.invoke().value
    ) = RobotMovedHundredMetersEvent(id, location)
}
