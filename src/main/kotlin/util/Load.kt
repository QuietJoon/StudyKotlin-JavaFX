package util

import kotlin.random.Random

fun load(): Int {
    println("Heavy load for 5 seconds")
    Thread.sleep(5000)
    return Random.nextInt()
}
