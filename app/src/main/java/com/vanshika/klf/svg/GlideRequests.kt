package com.vanshika.klf.svg

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.manager.Lifecycle
import com.bumptech.glide.manager.RequestManagerTreeNode
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import java.io.File
import java.net.URL
import java.util.List // This import is for java.util.List, ensure it's correct for your usage

class GlideRequests : RequestManager {

    constructor(glide: Glide, lifecycle: Lifecycle, treeNode: RequestManagerTreeNode, context: Context) : super(glide, lifecycle, treeNode, context)

    fun <ResourceType> asResource(resourceClass: Class<ResourceType>): GlideRequest<ResourceType> {
        return GlideRequest(glide, this, resourceClass, context)
    }

    override fun applyDefaultRequestOptions(options: RequestOptions): GlideRequests {
        return super.applyDefaultRequestOptions(options) as GlideRequests
    }

    override fun setDefaultRequestOptions(options: RequestOptions): GlideRequests {
        return super.setDefaultRequestOptions(options) as GlideRequests
    }

    override fun addDefaultRequestListener(listener: RequestListener<Any>): GlideRequests {
        return super.addDefaultRequestListener(listener) as GlideRequests
    }

    override fun asBitmap(): GlideRequest<Bitmap> {
        return super.asBitmap() as GlideRequest<Bitmap>
    }

    override fun asGif(): GlideRequest<GifDrawable> {
        return super.asGif() as GlideRequest<GifDrawable>
    }

    override fun asDrawable(): GlideRequest<Drawable> {
        return super.asDrawable() as GlideRequest<Drawable>
    }

    override fun load(bitmap: Bitmap?): GlideRequest<Drawable> {
        return super.load(bitmap) as GlideRequest<Drawable>
    }

    override fun load(drawable: Drawable?): GlideRequest<Drawable> {
        return super.load(drawable) as GlideRequest<Drawable>
    }

    override fun load(string: String?): GlideRequest<Drawable> {
        return super.load(string) as GlideRequest<Drawable>
    }

    override fun load(uri: Uri?): GlideRequest<Drawable> {
        return super.load(uri) as GlideRequest<Drawable>
    }

    override fun load(file: File?): GlideRequest<Drawable> {
        return super.load(file) as GlideRequest<Drawable>
    }

    override fun load(id: Int?): GlideRequest<Drawable> {
        return super.load(id) as GlideRequest<Drawable>
    }

    @Deprecated("Deprecated in Java")
    override fun load(url: URL?): GlideRequest<Drawable> {
        return super.load(url) as GlideRequest<Drawable>
    }

    override fun load(bytes: ByteArray?): GlideRequest<Drawable> {
        return super.load(bytes) as GlideRequest<Drawable>
    }

    override fun load(o: Any?): GlideRequest<Drawable> {
        return super.load(o) as GlideRequest<Drawable>
    }

    override fun downloadOnly(): GlideRequest<File> {
        return super.downloadOnly() as GlideRequest<File>
    }

    override fun download(o: Any?): GlideRequest<File> {
        return super.download(o) as GlideRequest<File>
    }

    override fun asFile(): GlideRequest<File> {
        return super.asFile() as GlideRequest<File>
    }

    override fun setRequestOptions(toSet: RequestOptions) {
        if (toSet is GlideOptions) { // Assuming GlideOptions is defined elsewhere
            super.setRequestOptions(toSet)
        } else {
            super.setRequestOptions(GlideOptions().apply(toSet))
        }
    }
}