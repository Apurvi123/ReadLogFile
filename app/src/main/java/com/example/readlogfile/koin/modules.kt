package com.example.readlogfile.koin

import com.example.readlogfile.MainViewModel
import com.example.readlogfile.ReadFile
import com.example.readlogfile.filedownload.DownloadRepository
import com.example.readlogfile.util.SaveFile
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val readModules = module {
    single { ReadFile() }
    single { SaveFile(androidContext()) }

    single { DownloadRepository(saveFile = get(), apiInterface = get()) }

    viewModel { MainViewModel(readFile = get(), downloadRepository = get()) }

}