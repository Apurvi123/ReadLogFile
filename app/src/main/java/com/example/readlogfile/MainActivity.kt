package com.example.readlogfile

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.readlogfile.databinding.ActivityMainBinding
import com.example.readlogfile.mvvmbase.MVVMActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.core.parameter.ParametersDefinition

class MainActivity : MVVMActivity<ActivityMainBinding, MainViewModel>(MainViewModel::class) {

    private lateinit var userAdapter: UserAdapter
    private var currentPage = 1

    var isLastPage: Boolean = false
    var isLoading: Boolean = false

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun getParameters(): ParametersDefinition? = null


    override fun getBindingVariable(): Int = BR.mainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObservables()
    }

    private fun initObservables() {
        baseViewModel.apply {
            paginationUsers.observe(this@MainActivity, Observer { event ->
                event?.getContentIfNotHandled()?.let {
                    isLoading = false
                    initRecyclerView(it)
                }
            })
        }
    }

    private fun initRecyclerView(paginationUsers: MutableList<FinalLogInfo>) {
        if (!::userAdapter.isInitialized) {
            userAdapter = UserAdapter()
            users_rv.adapter = userAdapter
            val layoutManager = LinearLayoutManager(this)
            users_rv.layoutManager = layoutManager
            userAdapter.addItems(paginationUsers)

            users_rv.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
                override fun isLastPage(): Boolean {
                    return isLastPage
                }

                override fun isLoading(): Boolean {
                    return isLoading
                }

                override fun loadMoreItems() {
                    isLoading = true
                    userAdapter.showLoadingMoreIndicator()
                    baseViewModel.loadMore(++currentPage, userAdapter.getExistingCount())
                }
            })
        } else {
            userAdapter.hideLoadingMoreIndicator()
            userAdapter.addItems(paginationUsers)
        }
    }
}
