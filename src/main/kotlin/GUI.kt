import javafx.application.Application
import javafx.event.EventHandler
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.input.TransferMode
import javafx.scene.paint.Paint
import javafx.scene.shape.Rectangle
import javafx.stage.Stage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope

import kotlinx.coroutines.*
import kotlinx.coroutines.javafx.*


import util.load


/*
Source code comes from http://www.java2s.com/Code/Java/JavaFX/DraganddropfiletoScene.htm
And converted to Kotlin by IntelliJ
*/
class GUI : Application() {

    override fun start(primaryStage: Stage) {
        primaryStage.title = "Study 7-Zip JBinding!"
        val fxml = javaClass.getResource("fxml/Main.fxml")
        val root: Parent = FXMLLoader.load(fxml)
        val scene = Scene(root)
        val filePathsLabel = root.lookup("#FilePathsLabel") as Label
        val statusIndicator = root.lookup("#StatusIndicator") as Rectangle

        scene.onDragOver = EventHandler { event ->
            val db = event.dragboard
            if (db.hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY)
            } else {
                event.consume()
            }
        }

        scene.onDragDropped = EventHandler { event ->
            val db = event.dragboard
            var success = false
            if (db.hasFiles()) {
                success = true
                var filePath: String?

                statusIndicator.fill = Paint.valueOf("GRAY")
                for (file in db.files) { db.files
                    filePath = file.absolutePath
                    println(filePath)
                }
                val firstResult = rawFileAnalyze(db.files)

                filePathsLabel.text = firstResult.paths
                statusIndicator.fill = Paint.valueOf(firstResult.colorName)

                statusIndicator.fill = Paint.valueOf("BLACK")
                var randomValue: Int? = null
                GlobalScope.launch(Dispatchers.Main) {
                    randomValue = loadWrap()
                }
                statusIndicator.fill =
                    if (randomValue!!.rem(2) == 0)
                         Paint.valueOf("WHITE")
                    else Paint.valueOf("PINK")


                println("End a phase")
            } else {
                // This seems not to be reached
                filePathsLabel.text = "No File"
                statusIndicator.fill = Paint.valueOf("Red")
            }
            event.isDropCompleted = success
            event.consume()
        }
        primaryStage.scene = scene
        primaryStage.show()
    }

    suspend fun loadWrap(): Int = withContext(Dispatchers.Default) { load() }
}
