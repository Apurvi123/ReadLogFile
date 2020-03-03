package com.example.readlogfile

data class LogInfo(val page: String, val user: String)

data class FinalLogInfo(val user: String, val pages: ArrayList<String>)