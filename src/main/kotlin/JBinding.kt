import net.sf.sevenzipjbinding.SevenZip
import net.sf.sevenzipjbinding.SevenZipNativeInitializationException

fun main(args: Array<String>) {
    try {
        SevenZip.initSevenZipFromPlatformJAR()
        println("7-Zip-JBinding library was initialized")
    } catch (e: SevenZipNativeInitializationException) {
        e.printStackTrace()
    }

}