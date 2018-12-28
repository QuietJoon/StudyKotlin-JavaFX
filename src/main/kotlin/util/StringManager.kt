package util

import java.io.File

import directoryDelimiter

fun generateStringFromFileList (strings : List<File>): String {
    val internalString = strings.map{it.toString().getFullName()}.joinToString(separator = "\n")
    return arrayOf("<\n", internalString, "\n>").joinToString(separator = "")
}

fun String.getFullName(): String =
    this.substringAfterLast(directoryDelimiter)

fun String.getFileName(): String =
    this.substringAfterLast(directoryDelimiter).substringBeforeLast(".")

fun String.getExtension(): String =
    this.substringAfterLast(directoryDelimiter).substringAfterLast(".","")

fun String.getDirectory(): String =
    this.substringBeforeLast(directoryDelimiter)

fun String.isArchive(): Boolean {
    val archiveExts: Array<String> = arrayOf("rar", "zip", "exe")
    for ( aExt in archiveExts ) {
        if ( this.getExtension() == aExt ) {
            return true
        }
    }
    return false
}
/*
  * null -> SingleVolume
  * 1 -> First Volume
  * otherwise -> Not single nor first volume
 */
fun String.maybePartNumber(): Int? {
    val maybeNumberString = this.substringAfterLast(".part","")
    println(String.format("<maybePartNumber>: %s",maybeNumberString))
    return maybeNumberString.toIntOrNull()
}

fun String.isSingleVolume(): Boolean = getFileName().maybePartNumber() == null

fun String.isFirstVolume(): Boolean = getFileName().maybePartNumber() == 1

fun processRawFilePath(files: List<File>): List<String> {
    var firstOrSingle: MutableList<String> = mutableListOf()
    for ( aPath in files.map{it.toString()}) {
        if ( aPath.isArchive() ) {
            // I knew this can be replaced by single if by using `maybePartNumber`
            // But I want to leave this structure for easy reading
            if (aPath.isSingleVolume()) {
                firstOrSingle.add(aPath)
            } else if (aPath.isFirstVolume()) {
                firstOrSingle.add(aPath)
            }
        }
    }
    return firstOrSingle
}
