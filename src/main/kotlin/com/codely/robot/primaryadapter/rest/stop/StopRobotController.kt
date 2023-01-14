package com.codely.robot.primaryadapter.rest.stop

import com.codely.robot.application.StopRobotCommand
import com.codely.robot.application.stop.handle
import com.codely.robot.domain.RobotRepository
import com.codely.robot.domain.StopRobotError
import com.codely.shared.event.bus.DomainEventPublisher
import com.codely.shared.response.toServerResponse
import kotlinx.coroutines.runBlocking
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class StopRobotController(
    private val repository: RobotRepository,
    private val publisher: DomainEventPublisher
) {

    @PatchMapping("/robots/stop/{robotId}")
    fun stop(@PathVariable robotId: String): ResponseEntity<*> = runBlocking {
        with(repository) {
            with(publisher) {
                handle(StopRobotCommand(robotId))
                    .toServerResponse { error ->
                        when (error) {
                            is StopRobotError.RobotAlreadyStopped -> ResponseEntity.status(HttpStatus.CONFLICT).body("cause")
                            is StopRobotError.RobotNotFound -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("cause")
                            is StopRobotError.Unknown -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("cause: ${error.cause}")
                        }
                    }
            }
        }
    }
}
