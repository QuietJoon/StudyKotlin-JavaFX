import javafx.application.Application


fun main(args : Array<String>) {
    println("JavaFX")
    Application.launch(GUI().javaClass, *args)
    println("End")
}
