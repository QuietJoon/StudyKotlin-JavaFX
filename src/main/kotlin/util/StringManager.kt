package util

import java.io.File

import directoryDelimiter

fun generateStringFromFileList (strings : List<File>): String {
    val internalString = strings.map{it.toString().getFullName()}.joinToString(separator = "\n")
    return arrayOf("<\n", internalString, "\n>").joinToString(separator = "")
}

fun String.getFullName(): String {
    return this.toString().substringAfterLast(directoryDelimiter)
}

fun String.getFileName(): String {
    return this.toString().substringAfterLast(directoryDelimiter).substringBeforeLast(".")
}

fun String.getExtension(): String {
    return this.toString().substringAfterLast(directoryDelimiter).substringAfterLast(".","")
}

fun String.getDirectory(): String {
    return this.toString().substringBeforeLast(directoryDelimiter)
}

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

fun String.isSingleVolume(): Boolean {
    return getFileName().maybePartNumber() == null
}

fun String.isFirstVolume(): Boolean {
    return getFileName().maybePartNumber() == 1
}
