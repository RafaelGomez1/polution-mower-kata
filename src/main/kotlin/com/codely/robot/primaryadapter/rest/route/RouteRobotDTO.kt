package com.codely.robot.primaryadapter.rest.route

import com.fasterxml.jackson.annotation.JsonProperty

data class RouteRobotDTO(
    @JsonProperty(value = "route")
    var route: String
)
