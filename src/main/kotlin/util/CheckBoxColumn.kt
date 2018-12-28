package util

// Copy from https://takeda-san.hatenablog.com/entry/2017/07/25/232840

import Item
import javafx.beans.property.SimpleBooleanProperty
import javafx.event.Event
import javafx.scene.control.TableColumn
import javafx.scene.control.cell.CheckBoxTableCell
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent


class CheckBoxColumn : TableColumn<Item, Boolean>() {
    init {
        this.cellValueFactory = PropertyValueFactory<Item, Boolean>("select")

        this.setCellFactory { column ->
            val cell = CheckBoxTableCell<Item, Boolean> { index ->
                val selected = SimpleBooleanProperty(
                    this.tableView.items[index].isSelected
                )
                selected.addListener { _, _, n ->
                    this.tableView.items[index].isSelected = n
                    this.tableView.selectionModel.select(index)

                    Event.fireEvent(
                        column.tableView, MouseEvent(
                            MouseEvent.MOUSE_CLICKED, 0.0, 0.0, 0.0, 0.0,
                            MouseButton.PRIMARY, 1, true, true, true, true, true, true, true, true, true, true, null
                        )
                    )
                }
                selected
            }
            cell
        }
    }
}
