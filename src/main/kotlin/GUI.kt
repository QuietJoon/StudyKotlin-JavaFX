import javafx.application.Application
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.event.EventHandler
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
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
            event.isDropCompleted = success
            event.consume()
        }
        primaryStage.scene = scene
        primaryStage.show()
    }

    suspend fun loadWrap(): Int = withContext(Dispatchers.Default) { load() }
}

class TAB : Application() {

    override fun start(primaryStage: Stage) {
        primaryStage.title = "Study Tab layout!"
        val fxml = javaClass.getResource("fxml/Tab.fxml")
        val root: Parent = FXMLLoader.load(fxml)
        val scene = Scene(root)
        val tableView = root.lookup("#TableView1") as TableView<Item>
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
            var itemData: ObservableList<Item> = FXCollections.observableArrayList()

            if (db.hasFiles()) {
                success = true

                for (file in db.files)
                    println(file.toString())

                statusIndicator.fill = Paint.valueOf("GRAY")

                val firstResult = rawFileAnalyze(db.files)

                filePathsLabel.text = firstResult.paths
                statusIndicator.fill = Paint.valueOf(firstResult.colorName)

                statusIndicator.fill = Paint.valueOf("BLACK")

                var randomValue: Int
                GlobalScope.launch(Dispatchers.Main) {
                    randomValue = async{load()}.await()
                    if (randomValue == null) error("Fail to wait")

                    statusIndicator.fill =
                            if (randomValue!!.rem(2) == 0)
                                Paint.valueOf("WHITE")
                            else Paint.valueOf("PINK")
                }

                val filePath = db.files.map{it.toString()}

                val itemList = filePath.map {
                    val anItem = Item(false,0,it)
                    anItem.onProperty().addListener{ ov,old_val,new_val ->
                        println(anItem.getName() + "'s CB status changed from '"
                        + old_val + "' to '" + new_val + "'.")
                    }
                    itemData.add(anItem)
                }

                tableView.columns[0] = TableColumn<Item, Boolean>("New")
                //tableView.columns[0].cellValueFactory =  Callback<TableColumn.CellDataFeatures<Item,Boolean>, ObservableValue<Item>>("New")

                tableView.columns[1] = TableColumn<Item, Int>("GID")
                //tableView.columns[0].cellValueFactory = PropertyValueFactory("checked")
                tableView.columns[2] = TableColumn<Item, String>("File path")
                //tableView.columns[0].cellValueFactory = PropertyValueFactory("checked")

                tableView.items = itemData
                //tableView.rowFactory = CheckBoxTableCell<Item,Item>.
                //itemData.

                println("End a case")
            }
            event.isDropCompleted = success
            event.consume()
        }
        primaryStage.scene = scene
        primaryStage.show()
    }
}
