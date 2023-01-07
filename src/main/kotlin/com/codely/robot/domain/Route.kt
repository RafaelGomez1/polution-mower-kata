package com.codely.robot.domain

import com.google.maps.model.LatLng

@JvmInline
value class Route(val points: List<LatLng>)
