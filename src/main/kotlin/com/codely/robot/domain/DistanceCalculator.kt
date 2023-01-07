package com.codely.robot.domain

import com.google.maps.model.LatLng

interface DistanceCalculator {
    fun distanceBetweenLocation(currentLocation: LatLng, nextLocation: LatLng): Double
}
