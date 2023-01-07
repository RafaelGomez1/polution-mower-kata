package com.codely.robot.secondaryadapter.distance

import com.codely.robot.domain.DistanceCalculator
import com.google.maps.model.LatLng
import org.gavaghan.geodesy.Ellipsoid
import org.gavaghan.geodesy.GeodeticCalculator
import org.gavaghan.geodesy.GlobalPosition
import org.springframework.stereotype.Component

@Component
class VincentyDistanceCalculator : DistanceCalculator {

    private val geoCalc = GeodeticCalculator()
    private val reference = Ellipsoid.WGS84

    override fun distanceBetweenLocation(currentLocation: LatLng, nextLocation: LatLng): Double {
        val currentPosition = GlobalPosition(currentLocation.lat, currentLocation.lng, 0.0)
        val nextPosition = GlobalPosition(nextLocation.lat, nextLocation.lng, 0.0)

        return geoCalc.calculateGeodeticCurve(reference, nextPosition, currentPosition).ellipsoidalDistance
    }
}
