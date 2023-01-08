package com.codely.robot.domain

sealed class StartRobotError {
    object RobotNotFound : StartRobotError()
    object RobotAlreadyStarted : StartRobotError()
    class Unknown(val cause: Throwable) : StartRobotError()
}

sealed class StopRobotError {
    object RobotNotFound : StopRobotError()
    object RobotAlreadyStopped : StopRobotError()
    class Unknown(val cause: Throwable) : StopRobotError()
}

sealed class MoveRobotError {
    object RobotNotFound : MoveRobotError()
    object RouteNotAssignedToRobot : MoveRobotError()
    class Unknown(val cause: Throwable) : MoveRobotError()
}

sealed class CreateRobotError {
    object RobotAlreadyExists : CreateRobotError()
    class Unknown(val cause: Throwable) : CreateRobotError()
}

sealed class RouteRobotError {
    object RobotNotFound : RouteRobotError()
    class Unknown(val cause: Throwable) : RouteRobotError()
}

sealed class SaveRobotError {
    class Unknown(val cause: Throwable) : SaveRobotError()
}
