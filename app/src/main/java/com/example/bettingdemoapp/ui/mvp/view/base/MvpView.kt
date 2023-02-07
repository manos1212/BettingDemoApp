package com.example.bettingdemoapp.ui.mvp.view.base

import androidx.annotation.StringRes

/**
 * The root view interface for every mvp view
 */
interface MvpView {

    fun isAttached(): Boolean

    fun showEmpty() {
        // Empty implementation, override in subclasses.
    }

    fun showLoading(show: Boolean = true) {
        // Empty implementation, override in subclasses.
    }

    fun showError(
        errorKey: String,
        @StringRes fallbackStringRes: Int,
    ) {
        // Empty implementation, override in subclasses.
    }

    /**
     * Load the data. Typically invokes the presenter method to load the desired data.
     *
     * **Should not be called from presenter** to prevent infinity loops. The method is declared
     * in the views interface to add support for view state easily.
     *
     */
    fun loadData() {
        // Empty implementation, override in subclasses.
    }

}