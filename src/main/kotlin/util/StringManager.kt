package util

import java.io.File

import directoryDelimiter

fun generateStringFromFileList (strings : List<File>): String {
    val internalString = strings.map{getFullName(it.toString())}.joinToString(separator = "\n")
    return arrayOf("<\n", internalString, "\n>").joinToString(separator = "")
}

fun getFullName(path: String): String {
    return path.toString().substringAfterLast(directoryDelimiter)
}

fun getFileName(path: String): String {
    return path.toString().substringAfterLast(directoryDelimiter).substringBeforeLast(".")
}

fun getExtension(path: String): String {
    return path.toString().substringAfterLast(directoryDelimiter).substringAfterLast(".")
}

fun getDirectory(path: String): String {
    return path.toString().substringBeforeLast(directoryDelimiter)
}
