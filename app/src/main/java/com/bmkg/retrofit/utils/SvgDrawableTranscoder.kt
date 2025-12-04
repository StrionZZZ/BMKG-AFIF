package com.bmkg.retrofit.utils

import android.graphics.drawable.PictureDrawable
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.resource.SimpleResource
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder

class SvgDrawableTranscoder :
    ResourceTranscoder<PictureDrawable, PictureDrawable> {

    override fun transcode(
        toTranscode: Resource<PictureDrawable>,
        options: Options
    ): Resource<PictureDrawable>? {
        return SimpleResource(toTranscode.get())
    }
}
