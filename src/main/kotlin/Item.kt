import javafx.beans.property.*

class Item(isSelected: Boolean, groupID: GroupID, name: String){
    private val select = SimpleBooleanProperty()
    private val name = SimpleStringProperty()
    private val groupID = SimpleIntegerProperty()

    var isSelected:Boolean
        get() = this.onProperty().get()
        set(on) = this.onProperty().set(on)

    init{
        setGroupID(groupID)
        setName(name)
        this.isSelected = isSelected
    }

    fun nameProperty():StringProperty = this.name

    fun getName():String = this.nameProperty().get()

    fun setName(name:String) = this.nameProperty().set(name)

    fun groupIDProperty():IntegerProperty = this.groupID

    fun getGroupID():GroupID = this.groupIDProperty().get()

    fun setGroupID(groupID:GroupID) = this.groupIDProperty().set(groupID)

    fun onProperty():BooleanProperty = this.select

    override fun toString():String = getGroupID().toString() + getName()
}

typealias GroupID = Int
