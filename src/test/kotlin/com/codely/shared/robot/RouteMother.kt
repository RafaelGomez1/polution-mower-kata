package com.codely.shared.robot

import com.codely.robot.domain.Route
import com.google.maps.model.EncodedPolyline
import com.google.maps.model.LatLng

object RouteMother {
    fun fromPolyline(polyline: String): Route {
        return Route(decodePolyline(polyline))
    }

    fun invoke(): Route {
        return Route(listOf(LocationMother.invoke().value))
    }

    private fun decodePolyline(polyline: String): List<LatLng> {
        val encodedPolyline = EncodedPolyline(polyline)
        return encodedPolyline.decodePath()
    }
}
