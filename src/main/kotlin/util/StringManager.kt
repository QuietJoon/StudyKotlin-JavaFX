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
    return this.toString().substringAfterLast(directoryDelimiter).substringAfterLast(".")
}

fun String.getDirectory(): String {
    return this.toString().substringBeforeLast(directoryDelimiter)
}
