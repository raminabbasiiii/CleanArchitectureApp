package com.raminabbasiiii.cleanarchitectureapp.presentation.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.raminabbasiiii.cleanarchitectureapp.R

fun Activity.showRetry(
    visibility: Boolean,
    message: String?) {
    findViewById<TextView>(R.id.tv_error).isVisible = visibility
    findViewById<Button>(R.id.btn_retry).isVisible = visibility
    findViewById<TextView>(R.id.tv_error).text = message
}

fun Activity.showProgressBar(visibility: Boolean) {
    findViewById<ProgressBar>(R.id.progress_bar).isVisible = visibility
}

fun View.hideKeyboard(context: Context) {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.showSnackBar(@StringRes messageRes: Int, length: Int = Snackbar.LENGTH_LONG, f: Snackbar.() -> Unit) {
    val snackBar = Snackbar.make(this, resources.getString(messageRes), length)
    snackBar.f()
    snackBar.show()
}


