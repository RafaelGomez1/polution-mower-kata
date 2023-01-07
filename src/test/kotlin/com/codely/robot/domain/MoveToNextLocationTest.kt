package com.codely.robot.domain

import com.codely.shared.robot.LocationMother
import com.codely.shared.robot.RobotMother
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MoveToNextLocationTest {

    @Test
    fun `robot moves to next location and adds RobotMovedHundredMetersEvent if it moves over the notification distance`() {
        val robot = robot.moveToNextLocation(location.value, 95.00)
        assertEquals(5.00, robot.distance.value)
    }

    private val robot = RobotMother.invoke(distance = DistanceTravelled(10.00))
    private val location = LocationMother.invoke()
}
