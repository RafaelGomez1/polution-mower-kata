package com.codely.integration

import com.codely.shared.Application
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [Application::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
abstract class AbstractContainerTest
