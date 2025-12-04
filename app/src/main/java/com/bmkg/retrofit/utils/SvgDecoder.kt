package com.bmkg.retrofit.utils

import android.graphics.Picture
import android.graphics.drawable.PictureDrawable
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.ResourceDecoder
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.resource.SimpleResource
import com.caverock.androidsvg.SVG
import java.io.IOException
import java.io.InputStream

class SvgDecoder : ResourceDecoder<InputStream, PictureDrawable> {

    override fun handles(source: InputStream, options: Options): Boolean = true

    @Throws(IOException::class)
    override fun decode(
        source: InputStream,
        width: Int,
        height: Int,
        options: Options
    ): Resource<PictureDrawable> {

        return try {
            val svg = SVG.getFromInputStream(source)
            val picture: Picture = svg.renderToPicture()
            SimpleResource(PictureDrawable(picture))
        } catch (e: Exception) {
            throw IOException("Cannot decode SVG", e)
        }
    }
}
