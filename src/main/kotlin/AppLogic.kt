import java.io.File

import util.*


data class RawFileAnalyzed (
    val paths : String
    , val colorName: String
    , val firstOrSinglePaths: Array<String>)

fun rawFileAnalyze(files: List<File>): RawFileAnalyzed {
    val paths = generateStringFromFileList(files)
    var colorName = if (files.size == 1) "Yellow" else "Green"
    val pathArray = files.map{it.toString()}.toTypedArray()
    val firstOrSinglePaths = getFirstOrSingleArchivePaths(pathArray)

    return RawFileAnalyzed (paths, colorName, firstOrSinglePaths)
}
