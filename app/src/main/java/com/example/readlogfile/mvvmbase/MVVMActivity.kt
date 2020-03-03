package com.example.readlogfile.mvvmbase

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.loading_indicator.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.ParametersDefinition
import kotlin.reflect.KClass

abstract class MVVMActivity<T : ViewDataBinding, V : BaseViewModel>(clazz: KClass<V>) :
    AppCompatActivity() {

    private lateinit var mViewDataBinding: T

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun getParameters(): ParametersDefinition?

    val baseViewModel: V by viewModel(clazz, parameters = this.getParameters())

    abstract fun getBindingVariable(): Int

    fun getViewDataBinding(): T = mViewDataBinding

    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
        observerLoadingIndicator()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        baseViewModel.attachView(savedInstanceState == null)
    }

    private fun observerLoadingIndicator() {
        baseViewModel.setLoadingIndicatorVisibility.observe(this, Observer {
            loadingIndicator?.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

    private fun performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mViewDataBinding.setVariable(getBindingVariable(), baseViewModel)
        mViewDataBinding.executePendingBindings()
    }

    fun performDataReBinding() {
        mViewDataBinding.setVariable(getBindingVariable(), baseViewModel)
        mViewDataBinding.executePendingBindings()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        baseViewModel.detachView()
    }

    fun setListenerInstance(context: Context) {
        this.context = context
    }
}