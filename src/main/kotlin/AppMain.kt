import javafx.application.Application
import javafx.stage.Stage

class AppMain : Application() {

    override fun start(primaryStage: Stage) {
        primaryStage.title = "JavaFX"
        primaryStage.width = 800.0
        primaryStage.height = 600.0

        primaryStage.show()
    }

}