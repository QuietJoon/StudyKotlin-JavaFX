import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.stage.Stage

class AppMain : Application() {

    override fun start(primaryStage: Stage) {
        primaryStage.title = "JavaFX"
        val fxml = javaClass.getResource("fxml/Main.fxml")
        val root: Parent = FXMLLoader.load(fxml)
        val scene = Scene(root)

        primaryStage.scene = scene

        primaryStage.show()
    }

}
