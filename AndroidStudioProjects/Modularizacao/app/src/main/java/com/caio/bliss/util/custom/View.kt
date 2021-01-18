package com.caio.bliss.util.custom

import android.view.View
import com.caio.bliss.BuildConfig

fun View.getViewId() : String {
    return context.resources.getResourceName(id)
        .replace(BuildConfig.APPLICATION_ID + ":id/", "")
}