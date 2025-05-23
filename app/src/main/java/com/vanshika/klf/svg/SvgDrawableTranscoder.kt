package com.vanshika.klf.svg

import android.graphics.drawable.PictureDrawable
import android.os.Build
import androidx.annotation.RequiresApi
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.resource.SimpleResource
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder
import com.caverock.androidsvg.SVG

class SvgDrawableTranscoder : ResourceTranscoder<SVG, PictureDrawable> {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun transcode(
        toTranscode: Resource<SVG>,
        options: Options
    ): Resource<PictureDrawable> {
        val svg = toTranscode.get()
        val picture = svg.renderToPicture()
        return SimpleResource(PictureDrawable(picture))
    }
}
