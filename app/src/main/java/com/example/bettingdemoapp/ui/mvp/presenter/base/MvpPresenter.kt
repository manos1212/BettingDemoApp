package com.example.bettingdemoapp.ui.mvp.presenter.base

import androidx.annotation.UiThread
import com.example.bettingdemoapp.domain.model.base.exception.DomainException
import com.example.bettingdemoapp.domain.usecase.base.DataResult
import com.example.bettingdemoapp.ui.mvp.view.base.MvpView
import com.example.bettingdemoapp.ui.mvp.view.base.ViewAction
import kotlinx.coroutines.*
import java.lang.ref.WeakReference

/**
 * The parent presenter class for both Rx and Coroutines Presenters that handles [viewRef].
 */
abstract class MvpPresenter<out V : MvpView>(
    private val uiDispatcher: CoroutineDispatcher = Dispatchers.Main,
    private val bgDispatcher: CoroutineDispatcher = Dispatchers.IO) {
    private var viewRef: WeakReference<@UnsafeVariance V>? = null
    private var job = SupervisorJob()
    protected var uiScope = CoroutineScope(uiDispatcher + job)

    /**
     * Set or attach the view to this presenter
     */
    @UiThread
    fun attachView(view: @UnsafeVariance V) {
        viewRef = WeakReference(view)
    }

    protected fun isViewAttached(): Boolean {
        return viewRef?.get()?.isAttached() ?: false
    }

    @UiThread
    fun detachView() {
        viewRef?.clear()
        viewRef = null
    }

    @UiThread
    open fun detach() {
        detachView()
        uiScope.coroutineContext.cancelChildren()
    }

    @UiThread
    protected suspend fun <T> executeNetworkCall(
        networkCall: suspend () -> DataResult<T>,
    ): DataResult<T> {
        if (!isViewAttached()) {
            return DataResult.Error(
                DomainException(
                    "View is not attached.",
                    IllegalStateException("View is not attached.")
                )
            )
        }
        val dataResult = withContext(bgDispatcher) { networkCall() }
        if (!isViewAttached()) {
            return dataResult
        }
        return dataResult
    }


    /**
     * Convenience fun that executes the passed Action only if the View is attached.
     * If no View is attached, either an exception is thrown (if parameter exceptionIfViewNotAttached
     * is true) or the action is just not executed (no exception thrown).
     * Note that if no view is attached, this will not re-execute the given action if the View gets
     * re-attached.
     *
     * @param exceptionIfViewNotAttached true, if an exception should be thrown if no view is
     * attached while trying to execute the action. false, if no exception should be thrown (the action
     * will not be executed since no view is attached)
     * @param action The {@link ViewAction} that will be executed if a view is attached. This is
     * where you call view.isLoading etc. Use the view reference passed as parameter to {@link
     * ViewAction#run(Object)} and not deprecated method {@link #getView()}
     */
    @JvmOverloads
    protected fun ifViewAttached(
        exceptionIfViewNotAttached: Boolean = false,
        action: ViewAction<@UnsafeVariance V>,
    ) {
        viewRef?.get()?.let { view ->
            action.run(view)
        } ?: run {
            check(!exceptionIfViewNotAttached) { "No View attached to Presenter." }
        }
    }
}
