package com.bmkg.retrofit.utils

import android.content.Context
import android.graphics.drawable.PictureDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import java.io.InputStream

@GlideModule
class SvgModule : AppGlideModule() {

    override fun registerComponents(
        context: Context,
        glide: Glide,
        registry: Registry
    ) {
        registry
            .register(
                PictureDrawable::class.java,
                PictureDrawable::class.java,
                SvgDrawableTranscoder()
            )
            .append(
                InputStream::class.java,
                PictureDrawable::class.java,
                SvgDecoder()
            )
    }
}
