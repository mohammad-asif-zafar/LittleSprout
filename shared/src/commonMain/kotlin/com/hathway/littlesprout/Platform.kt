package com.hathway.littlesprout

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform