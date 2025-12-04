package com.bmkg.retrofit.utils

import android.graphics.drawable.PictureDrawable
import android.widget.ImageView
import com.bumptech.glide.request.target.ImageViewTarget

class SvgSoftwareLayerSetter(view: ImageView) :
    ImageViewTarget<PictureDrawable>(view) {

    override fun setResource(resource: PictureDrawable?) {
        view.setLayerType(ImageView.LAYER_TYPE_SOFTWARE, null)
        view.setImageDrawable(resource)
    }
}
