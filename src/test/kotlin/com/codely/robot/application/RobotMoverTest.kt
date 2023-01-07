package com.codely.robot.application

import com.codely.robot.domain.DistanceTravelled
import com.codely.shared.robot.LocationMother
import com.codely.shared.robot.RobotMother
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junit.jupiter.api.Test

class RobotMoverTest {

    @Test
    fun `tbd`() {

    }

    private val robot = RobotMother.invoke(distance = DistanceTravelled(10.00))
    private val location = LocationMother.invoke()
}