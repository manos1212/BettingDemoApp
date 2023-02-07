package com.example.bettingdemoapp.ui.mvp.view.base;

import androidx.annotation.NonNull;

/**
 * A helper interface that represents an Action that is executed to interact with the view.
 * Usually a Presenter should not get any data from View: so calls like view.getUserId() should not be done.
 * Rather write a method in your Presenter that takes the user id as parameter like this:
 * {@code
 * void doSomething(int userId){
 * // do something
 * ...
 * <p>
 * ifViewAttached( view -> view.showSuccessful())
 * }
 *
 * @param <V> The Type of the View
 */
public interface ViewAction<V> {

    /**
     * This method will be invoked to run the action. Implement this method to interact with the view.
     *
     * @param view The reference to the view. Not null.
     */
    void run(@NonNull V view);
}
