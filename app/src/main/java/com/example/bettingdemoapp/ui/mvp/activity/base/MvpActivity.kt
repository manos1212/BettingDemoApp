package com.example.bettingdemoapp.ui.mvp.activity.base

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.example.bettingdemoapp.BuildConfig
import androidx.viewbinding.ViewBinding
import com.example.bettingdemoapp.ui.mvp.presenter.base.MvpPresenter
import com.example.bettingdemoapp.ui.mvp.view.base.MvpView


/**
 * BaseMvpActivity that extends [CoreActivity] and implements [MvpView].
 * This should be the Parent Activity of all activities that follow **MVP pattern**.
 * It must include the base functionality regarding **MVP pattern**. Avoid adding here unrelated functionality.
 * Core functionality of the project i.e. permissions etc that must be available in every activity
 * must go to [CoreActivity], in order the transition to an other pattern e.g. MVVM-MVI, be relatively smooth.
 */
abstract class MvpActivity<V : MvpView, P : MvpPresenter<V>, VB : ViewBinding>
    : AppCompatActivity(), MvpView {
    protected abstract val binding: VB


    private var presenterRef: P? = null

    override fun isAttached(): Boolean = !isFinishing

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initPresenter()
    }

    protected abstract fun initPresenter()

    protected fun attachPresenter(presenter: P, view: V) {
        presenterRef = presenter.also {
            it.attachView(view)
        }
        loadData()
    }

    override fun setContentView(@LayoutRes layoutResID: Int) {
        super.setContentView(layoutResID)
        initCommonViews()
    }

    override fun setContentView(view: View) {
        super.setContentView(view)
        initCommonViews()
    }

    protected open fun initCommonViews() {
        getPassData()
        initLayout()
        initResources()
    }

    protected open fun initLayout() {
        // Empty implementation, override in subclasses.
    }

    protected open fun initResources() {
        // Empty implementation, override in subclasses.
    }

    protected open fun getPassData() {
        // Empty implementation, override in subclasses.
    }


    override fun onResume() {
        super.onResume()
        if (!BuildConfig.DEBUG) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
        }
    }

    override fun onPause() {
        super.onPause()
        if (!BuildConfig.DEBUG) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenterRef?.detach()
    }

    fun getPresenter() = presenterRef
}