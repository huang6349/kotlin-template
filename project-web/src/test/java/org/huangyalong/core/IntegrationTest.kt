package org.huangyalong.core

import org.huangyalong.Application
import org.huangyalong.config.AsyncSyncConfiguration
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@TestMethodOrder(OrderAnnotation::class)
@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [AsyncSyncConfiguration::class, Application::class])
@Transactional(propagation = Propagation.REQUIRED)
annotation class IntegrationTest
