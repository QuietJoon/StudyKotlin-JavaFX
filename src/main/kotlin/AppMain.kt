import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.stage.Stage

import javafx.event.EventHandler
import javafx.scene.Group
import javafx.scene.input.TransferMode
import javafx.scene.control.Label
import javafx.scene.shape.Rectangle
import javafx.scene.paint.Paint

/*
Source code comes from http://www.java2s.com/Code/Java/JavaFX/DraganddropfiletoScene.htm
And converted to Kotlin by IntelliJ
*/
class AppMain : Application() {

    override fun start(primaryStage: Stage) {
        primaryStage.title = "JavaFX"
        val fxml = javaClass.getResource("fxml/Main.fxml")
        val root: Parent = FXMLLoader.load(fxml)
        val scene = Scene(root)
        val filePathLabel = root.lookup("#FilePathLabel") as Label
        val filePathesLabel = root.lookup("#FilePathesLabel") as Label
        val statusIndicator = root.lookup("#StatusIndicator") as Rectangle

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
                var filePath: String?
                for (file in db.files) {
                    filePath = file.absolutePath
                    println(filePath)
                    if (filePath!=null) {
                        filePathLabel.text = filePath
                    }
                }
                val internalString = db.files.joinToString(separator = "\n")
                filePathesLabel.text = arrayOf("<\n",internalString,"\n>").joinToString(separator = "")
                if (db.files.size == 1) {
                    statusIndicator.fill = Paint.valueOf("Yellow")
                } else {
                    statusIndicator.fill = Paint.valueOf("Green")
                }
            } else {
                // This seems not to be reached
                filePathLabel.text = "No File"
                statusIndicator.fill = Paint.valueOf("Red")
            }
            event.isDropCompleted = success
            event.consume()
        }

        primaryStage.scene = scene
        primaryStage.show()
    }

}
