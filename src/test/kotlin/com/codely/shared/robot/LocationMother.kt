package com.codely.shared.robot

import com.codely.shared.domain.robot.Location
import com.google.maps.model.LatLng
import kotlin.random.Random

object LocationMother {

    fun invoke(value: LatLng = LatLng(Random.nextDouble(), Random.nextDouble())): Location = Location(value)
}
