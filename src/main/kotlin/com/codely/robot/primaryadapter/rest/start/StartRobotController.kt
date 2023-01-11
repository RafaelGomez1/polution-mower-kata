package com.codely.robot.primaryadapter.rest.start

import com.codely.robot.application.StartRobotCommand
import com.codely.robot.application.start.handle
import com.codely.robot.domain.RobotRepository
import com.codely.robot.domain.StartRobotError
import com.codely.shared.event.bus.DomainEventPublisher
import com.codely.shared.response.toServerResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class StartRobotController(
    private val repository: RobotRepository,
    private val publisher: DomainEventPublisher
) {

    @PostMapping("/robots/start/{robotId}")
    suspend fun start(@PathVariable robotId: String): ResponseEntity<Void> =
        with(repository) {
            with(publisher) {
                handle(StartRobotCommand(robotId))
                    .toServerResponse { error ->
                        when (error) {
                            is StartRobotError.RobotNotFound -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                            is StartRobotError.RobotAlreadyStarted -> ResponseEntity.status(HttpStatus.CONFLICT).build()
                            is StartRobotError.Unknown -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
                        }
                    }
            }
        }
}
