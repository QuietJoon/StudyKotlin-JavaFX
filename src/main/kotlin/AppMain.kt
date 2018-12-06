import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.stage.Stage

import javafx.event.EventHandler
import javafx.scene.Group
import javafx.scene.input.TransferMode

/*
Source code comes from http://www.java2s.com/Code/Java/JavaFX/DraganddropfiletoScene.htm
And converted to Kotlin by IntelliJ
*/
class Main : Application() {

    override fun start(primaryStage: Stage) {
        val root = Group()
        val scene = Scene(root, 551.0, 400.0)
        scene.onDragOver = EventHandler { event ->
            val db = event.dragboard
            if (db.hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY)
            } else {
                event.consume()
            }
        }

        // Dropping over surface
        scene.onDragDropped = EventHandler { event ->
            val db = event.dragboard
            var success = false
            if (db.hasFiles()) {
                success = true
                var filePath: String? = null
                for (file in db.files) {
                    filePath = file.absolutePath
                    println(filePath)
                }
            }
            event.isDropCompleted = success
            event.consume()
        }

        primaryStage.scene = scene
        primaryStage.show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(*args)
        }
    }
}

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
