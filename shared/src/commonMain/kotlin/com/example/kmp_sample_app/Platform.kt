package com.example.kmp_sample_app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform