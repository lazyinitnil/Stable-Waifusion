package com.example.stablewaifusionalpha.core.ext

import com.example.stablewaifusionalpha.domain.model.remote.ImageFormat
import com.example.stablewaifusionalpha.domain.model.remote.ResolutionLevel

fun calculateDimensions(resolution: ResolutionLevel, format: ImageFormat): Pair<Int, Int> {
    val gcd = gcd(format.widthRatio, format.heightRatio)
    val widthRatio = format.widthRatio / gcd
    val heightRatio = format.heightRatio / gcd

    val scaleFactor = resolution.number / minOf(widthRatio, heightRatio)

    val width = widthRatio * scaleFactor
    val height = heightRatio * scaleFactor

    return width to height
}

fun gcd(a: Int, b: Int): Int {
    if (b == 0) return a
    return gcd(b, a % b)
}
