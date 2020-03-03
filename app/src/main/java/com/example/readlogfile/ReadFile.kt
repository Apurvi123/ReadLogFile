package com.example.readlogfile

import java.io.File

class ReadFile {
    fun readFile(futureStudioIconFile: File) = futureStudioIconFile.bufferedReader().readLines()
}