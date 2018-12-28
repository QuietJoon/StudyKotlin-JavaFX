import javafx.application.Application
import javafx.event.EventHandler
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.input.TransferMode
import javafx.scene.paint.Paint
import javafx.scene.shape.Rectangle
import javafx.stage.Stage
import kotlinx.coroutines.GlobalScope

import kotlinx.coroutines.*

import util.load
import util.processRawFilePath

class TAB : Application() {

    private var selectAllCheckBox: CheckBox? = null

    override fun start(primaryStage: Stage) {
        primaryStage.title = "Study Tab layout!"
        primaryStage.isAlwaysOnTop = true
        val fxml = javaClass.getResource("fxml/Tab.fxml")
        val root: Parent = FXMLLoader.load(fxml)
        val scene = Scene(root)
        val itemTable = root.lookup("#TableView1") as TableView<Item>
        val filePathsLabel = root.lookup("#FilePathsLabel") as Label
        val statusIndicator = root.lookup("#StatusIndicator") as Rectangle

        selectAllCheckBox = CheckBox()

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

                // Session #1
                val itemList = db.files.map {
                    val anItem = Item(false,0,it.toString())
                    anItem.onProperty().addListener{ ov,old_val,new_val ->
                        println(anItem.getName() + "'s CB status changed from '"
                        + old_val + "' to '" + new_val + "'.")
                    }
                    anItem
                }

                val theList = itemTable.items
                theList.addAll(itemList)


                // Session #2
                statusIndicator.fill = Paint.valueOf("GRAY")
                filePathsLabel.text = processRawFilePath(db.files).joinToString(separator = "\n")

                var randomValue: Int
                GlobalScope.launch {
                    randomValue = async{load()}.await()
                    if (randomValue == null) error("Fail to wait")

                    statusIndicator.fill =
                            if (randomValue!!.rem(2) == 0)
                                Paint.valueOf("WHITE")
                            else Paint.valueOf("PINK")
                }

                println("End a case")
            }
            event.isDropCompleted = success
            event.consume()
        }
        primaryStage.scene = scene
        primaryStage.show()
    }
}
