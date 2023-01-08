package com.codely.shared.robot.domain

import com.google.maps.model.EncodedPolyline
import com.google.maps.model.LatLng

@JvmInline
value class Location(val value: LatLng) {
    companion object {
        fun fromPolyline(polyline: String) =
            Location(EncodedPolyline(polyline).decodePath()[0])
    }
}
