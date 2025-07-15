package com.example.calculator_module

class Greeting2 {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        return "Hello CalculatorModule, ${platform.name}!"
    }
}