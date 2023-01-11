package com.codely.shared.configuration.bus

import org.axonframework.eventhandling.EventBus
import org.axonframework.eventhandling.SimpleEventBus
import org.axonframework.eventhandling.gateway.DefaultEventGateway
import org.axonframework.eventhandling.gateway.EventGateway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AxonConfiguration {

    @Bean
    fun eventBus(): EventBus = SimpleEventBus.builder().build()

    @Bean
    fun eventGateway(): EventGateway = DefaultEventGateway.builder().eventBus(eventBus()).build()
}
