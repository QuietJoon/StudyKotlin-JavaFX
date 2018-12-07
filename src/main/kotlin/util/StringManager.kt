package util

import java.io.File

fun generateStringFromFileList (strings : List<File>): String {
    val internalString = strings.joinToString(separator = "\n")
    return arrayOf("<\n", internalString, "\n>").joinToString(separator = "")
}
