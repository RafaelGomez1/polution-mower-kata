package com.codely.robot.domain

import com.google.maps.model.EncodedPolyline
import com.google.maps.model.LatLng

@JvmInline
value class Route(val points: List<LatLng>) {
    companion object {
        fun fromPolyline(polyline: String) =
            Route(EncodedPolyline(polyline).decodePath())
    }
}
