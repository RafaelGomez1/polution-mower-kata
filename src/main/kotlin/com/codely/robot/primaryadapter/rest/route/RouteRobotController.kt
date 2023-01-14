package com.codely.robot.primaryadapter.rest.route

import com.codely.robot.application.RouteRobotCommand
import com.codely.robot.application.route.handle
import com.codely.robot.domain.RobotRepository
import com.codely.robot.domain.RouteRobotError
import com.codely.shared.response.toServerResponse
import kotlinx.coroutines.runBlocking
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class RouteRobotController(
    private val repository: RobotRepository
) {

    @PatchMapping(value = ["/robots/route/{robotId}"])
    fun route(@PathVariable robotId: String, @RequestBody dto: RouteRobotDTO): ResponseEntity<*> = runBlocking {
        with(repository) {
            handle(RouteRobotCommand(robotId, dto.route))
                .toServerResponse { error ->
                    when (error) {
                        is RouteRobotError.RobotNotFound -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("cause")
                        is RouteRobotError.Unknown -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("cause: ${error.cause}")
                    }
                }
        }
    }
}
