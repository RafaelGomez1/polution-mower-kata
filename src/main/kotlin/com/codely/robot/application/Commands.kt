package com.codely.robot.application

sealed class RobotCommands

data class StartRobotCommand(val id: String) : RobotCommands()
data class StopRobotCommand(val id: String) : RobotCommands()
data class MoveRobotCommand(val id: String) : RobotCommands()
