package com.codely.robot.primaryadapter.rest.create

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateRobotDTO(
    @JsonProperty(value = "route")
    var route: String
)
