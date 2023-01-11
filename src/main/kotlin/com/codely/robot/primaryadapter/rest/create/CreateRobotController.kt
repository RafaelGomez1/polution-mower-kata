package com.codely.robot.primaryadapter.rest.create

import com.codely.robot.application.CreateRobotCommand
import com.codely.robot.application.create.handle
import com.codely.robot.domain.CreateRobotError
import com.codely.robot.domain.RobotRepository
import com.codely.shared.configuration.robot.RobotConfiguration
import com.codely.shared.response.toServerResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CreateRobotController(
    private val repository: RobotRepository,
    private val configuration: RobotConfiguration
) {

    @PostMapping(value = ["/robots/create/{robotId}"])
    suspend fun create(@PathVariable robotId: String, @RequestBody createRobotDto: CreateRobotDTO): ResponseEntity<Void> =
        with(repository) {
            handle(CreateRobotCommand(robotId, configuration.speed, createRobotDto.route))
                .toServerResponse { error ->
                    when (error) {
                        is CreateRobotError.RobotAlreadyExists -> ResponseEntity.status(HttpStatus.CONFLICT).build()
                        is CreateRobotError.Unknown -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
                    }
                }
        }
}
