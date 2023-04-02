package filemerger

import java.io.Reader

fun listXDMParts(reader: Reader): List<String> {
    val result = mutableListOf<String>()
    reader.forEachLine {
        if (it.length == 36 && it[8] == '-' && it[13] == '-' && it[18] == '-' && it[23] == '-'){
            result.add(it)
        }
    }
    return result
}