package com.example.favorite.remote.utils

import java.io.InputStreamReader

//class FileReader(path: String) {
//
//    val content: String
//
//    init {
//        val reader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(path))
//        content = reader.readText()
//        reader.close()
//    }
//}

class FileReader {
    operator fun invoke(path: String) = this.javaClass.classLoader?.getResource(path)?.readText()
}