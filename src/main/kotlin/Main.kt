import javafx.application.Application

import archive.*

fun main(args : Array<String>) {
    println("JavaFX")
    if (jBindingChecker()) Application.launch(GUI().javaClass, *args)
    println("End")

    var count = 0
    var lcount = 4
    while (lcount>0) {
        Thread.sleep(10L)
        if (count < 100) {
            count++
        } else {
            println(lcount)
            count = 0
            lcount -= 1
        }
    }
}
