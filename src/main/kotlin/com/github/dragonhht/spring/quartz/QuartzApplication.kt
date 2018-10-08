package com.github.dragonhht.spring.quartz

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * Description.
 * User: huang
 * Date: 18-10-8
 */
@SpringBootApplication
open class QuartzApplication

fun main(args: Array<String>) {
    runApplication<QuartzApplication>(*args)
}