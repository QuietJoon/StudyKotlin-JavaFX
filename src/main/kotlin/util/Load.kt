package util

import kotlin.random.Random

fun load(): Int {
    val weight = 1L
    println("Heavy load for ${weight} seconds")
    Thread.sleep(1000L * weight)
    return Random.nextInt()
}
