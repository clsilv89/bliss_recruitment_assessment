package com.caio.modulo1.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.caio.modulo1.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

fun ImageView.loadImageWithUrl(
    imageUrl: String,
    errorHolder: Drawable? = ContextCompat.getDrawable(context, R.drawable.image_view_bg),
    placeholder: Drawable? = context.getCardProgressDrawable(R.color.bg_blue),
    loadFinish: (() -> Unit)? = null,
    loadError: ((exception: Exception?) -> Unit)? = null
) {
    val requestCreator = Picasso.get().load(imageUrl)

    placeholder?.apply {
        requestCreator.placeholder(this)
    }

    errorHolder?.apply {
        requestCreator.error(this)
    }

    requestCreator.into(this, object : Callback {
        override fun onSuccess() {
            loadFinish?.invoke()
        }

        override fun onError(exception: Exception?) {
            loadError?.invoke(exception)
        }
    })
}