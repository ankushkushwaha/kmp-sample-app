package com.example.calculator_module

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform