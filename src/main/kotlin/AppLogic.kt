import util.*

fun getFirstOrSingleArchivePaths(paths: Array<String>) : Array<String> {
    var firstOrSingle: MutableList<String> = mutableListOf()
    for ( aPath in paths ) {
        if ( aPath.isArchive() ) {
            if (aPath.isSingleVolume()) {
                firstOrSingle.add(aPath)
            }
            if (aPath.isFirstVolume()) {
                firstOrSingle.add(aPath)
            }
        }
    }
    return firstOrSingle.toTypedArray()
}