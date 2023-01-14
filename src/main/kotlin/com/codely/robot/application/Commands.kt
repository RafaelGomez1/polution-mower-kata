package com.codely.robot.application

sealed class RobotCommands

data class StartRobotCommand(val id: String) : RobotCommands()
data class StopRobotCommand(val id: String) : RobotCommands()
data class MoveRobotCommand(val id: String) : RobotCommands()
data class RouteRobotCommand(val id: String, val route: String) : RobotCommands()

data class CreateRobotCommand(
    val id: String,
    val speed: Double,
    val route: String?
) : RobotCommands()
