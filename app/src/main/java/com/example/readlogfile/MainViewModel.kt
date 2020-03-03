package com.example.readlogfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.readlogfile.filedownload.DownloadRepository
import com.example.readlogfile.filedownload.FileDownloadResult
import com.example.readlogfile.mvvmbase.BaseViewModel
import com.example.readlogfile.util.Event
import java.io.File

class MainViewModel(
    private val readFile: ReadFile,
    private val downloadRepository: DownloadRepository
) : BaseViewModel(), FileDownloadResult {

    private var mUser: String = ""
    private var pageList = ArrayList<String>()
    private val occurrencesList = ArrayList<FinalLogInfo>()
    private var paginationUsersList = ArrayList<FinalLogInfo>()

    private var _paginationUsersList = MutableLiveData<Event<MutableList<FinalLogInfo>>>()
    val paginationUsers: LiveData<Event<MutableList<FinalLogInfo>>> by lazy { _paginationUsersList }

    override fun onViewAttached(firstAttach: Boolean) {
        super.onViewAttached(firstAttach)

        showLoadingIndicator()
        downloadRepository.downloadFile(this)

    }

    override fun fileDownloadSuccess(textFile: File) {
        createLogInfoList(readFile.readFile(textFile))
    }

    override fun fileDownloadFailure() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun createLogInfoList(stringList: List<String>) {
        val logInfoList = ArrayList<LogInfo>()
        stringList.forEach {
            val logInfo = LogInfo(
                it.substringBefore("HTTP").substringAfter("GET /"),
                it.substringBefore("- -")
            )
            logInfoList.add(logInfo)
        }
        calculateOccurrences(logInfoList)
    }

    private fun calculateOccurrences(logInfoList: ArrayList<LogInfo>) {
        logInfoList.forEach {
            groupingByConsequitive(it)
        }
        occurrencesList.sortByDescending { it.pages.size }
        loadMore(1, 0)
    }

    private fun groupingByConsequitive(logInfo: LogInfo) {
        if (logInfo.user == mUser) {
            pageList.add(logInfo.page)
        } else {
            if (pageList.isNotEmpty()) {
                val finalLogInfo = FinalLogInfo(mUser, pageList)
                occurrencesList.add(finalLogInfo)
                pageList = arrayListOf()
            }
            mUser = logInfo.user
            groupingByConsequitive(logInfo)
        }
    }

    fun loadMore(pageCount: Int, existingCount: Int) {

        val itemRangeToAdd = pageCount * 10

        for (i in existingCount until itemRangeToAdd) {
            this.paginationUsersList.add(occurrencesList[i])
        }
        hideLoadingIndicator()
        _paginationUsersList.value = Event(this.paginationUsersList)
    }
}