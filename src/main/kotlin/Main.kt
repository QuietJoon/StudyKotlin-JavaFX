import javafx.application.Application

fun main(args : Array<String>) {
    println("Something other!")
    Application.launch(AppMain().javaClass, *args)
    var count = 0
    var lcount = 100
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
