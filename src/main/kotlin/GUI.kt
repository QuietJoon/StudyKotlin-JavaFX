import java.util.*

import javafx.application.Application
import javafx.collections.FXCollections
import javafx.event.ActionEvent
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
import kotlinx.coroutines.selects.whileSelect

import util.*

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
        val newButton = root.lookup("#NewButton") as Button
        val goButton = root.lookup("#GoButton") as Button

        itemTable.columns[1].style = "-fx-alignment: CENTER-RIGHT;"

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
                    anItem.select.addListener{ ov,old_val,new_val ->
                        println(anItem.getName() + "'s CB status changed from '"
                        + old_val + "' to '" + new_val + "'.")
                    }
                    anItem
                }

                itemTable.items = FXCollections.observableArrayList(itemList)


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

        newButton.setOnAction { event -> handleNew(event,itemTable) }
        goButton.setOnAction { event -> handleGo(event,itemTable) }

        primaryStage.scene = scene
        primaryStage.show()
    }

    private fun handleNew(e: ActionEvent, itemTable: TableView<Item>) {
        val groupIDSet: SortedSet<Int> = sortedSetOf()

        for (anItem in itemTable.items) {
            if (anItem.isSelected)
                println(anItem.getName())
            else
                groupIDSet.add(anItem.getGroupID())
        }
        var newID = 0
        for ( idx in groupIDSet ) {
            if (newID < idx) break
            newID++
        }
        for (anItem in itemTable.items) {
            if (anItem.isSelected)
                anItem.setGroupID(newID)
            // TODO: [BUG] This does not update GUI
            anItem.isSelected = false
        }
    }

    private fun handleGo(e: ActionEvent, itemTable: TableView<Item>) {
        val groupIDSet: SortedSet<Int> = sortedSetOf()
        itemTable.items.forEach {
            if (it != null)
                groupIDSet.add(it.getGroupID())
        }

        while ( groupIDSet.last() != groupIDSet.size - 1) {
            var smallestGroupID = 0
            for ( idx in groupIDSet ) {
                if (smallestGroupID < idx) break
                smallestGroupID++
            }
            val finding = groupIDSet.last()
            for ( anItem in itemTable.items ) {
                anItem.setGroupID(smallestGroupID)
            }
        }

        for (anItem in itemTable.items)
            println("${anItem.getName()} ${anItem.getGroupID()}")
    }
}
