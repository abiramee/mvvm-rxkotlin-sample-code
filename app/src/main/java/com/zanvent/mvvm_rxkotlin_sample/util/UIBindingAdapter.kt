@file:Suppress("DEPRECATION")

package com.zanvent.mvvm_rxkotlin_sample.util

import android.media.Image
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.zanvent.mvvm_rxkotlin_sample.ui.main.MainActivityViewModel
import javax.inject.Inject
////

@BindingAdapter("setImg")
fun bindImage(imageView: ImageView, imageUrl: String) {
        imageUrl.let {
            Glide.with(imageView.context).load(imageUrl)
                .apply(
                    RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL).centerCrop()
                        .transforms(CenterCrop(), RoundedCorners(1000))
                ).into(imageView)
        }
}
