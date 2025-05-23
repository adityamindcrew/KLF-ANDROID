package com.vanshika.klf.svg

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.TransitionOptions
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.Option
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.BaseRequestOptions
import com.bumptech.glide.request.RequestListener
import java.io.File
import java.net.URL
import java.util.List

class GlideRequest<TranscodeType> : RequestBuilder<TranscodeType>, Cloneable {

    constructor(transcodeClass: Class<TranscodeType>, other: RequestBuilder<*>) : super(transcodeClass, other)

    constructor(glide: Glide, requestManager: RequestManager, transcodeClass: Class<TranscodeType>, context: Context) : super(glide, requestManager, transcodeClass, context)

    override fun getDownloadOnlyRequest(): GlideRequest<File> {
        return GlideRequest(File::class.java, this).apply(DOWNLOAD_ONLY_OPTIONS) as GlideRequest<File>
    }

    override fun sizeMultiplier(value: Float): GlideRequest<TranscodeType> {
        return super.sizeMultiplier(value) as GlideRequest<TranscodeType>
    }

    override fun useUnlimitedSourceGeneratorsPool(flag: Boolean): GlideRequest<TranscodeType> {
        return super.useUnlimitedSourceGeneratorsPool(flag) as GlideRequest<TranscodeType>
    }

    override fun useAnimationPool(flag: Boolean): GlideRequest<TranscodeType> {
        return super.useAnimationPool(flag) as GlideRequest<TranscodeType>
    }

    override fun onlyRetrieveFromCache(flag: Boolean): GlideRequest<TranscodeType> {
        return super.onlyRetrieveFromCache(flag) as GlideRequest<TranscodeType>
    }

    override fun diskCacheStrategy(strategy: DiskCacheStrategy): GlideRequest<TranscodeType> {
        return super.diskCacheStrategy(strategy) as GlideRequest<TranscodeType>
    }

    override fun priority(priority: Priority): GlideRequest<TranscodeType> {
        return super.priority(priority) as GlideRequest<TranscodeType>
    }

    override fun placeholder(drawable: Drawable?): GlideRequest<TranscodeType> {
        return super.placeholder(drawable) as GlideRequest<TranscodeType>
    }

    override fun placeholder(id: Int): GlideRequest<TranscodeType> {
        return super.placeholder(id) as GlideRequest<TranscodeType>
    }

    override fun fallback(drawable: Drawable?): GlideRequest<TranscodeType> {
        return super.fallback(drawable) as GlideRequest<TranscodeType>
    }

    override fun fallback(id: Int): GlideRequest<TranscodeType> {
        return super.fallback(id) as GlideRequest<TranscodeType>
    }

    override fun error(drawable: Drawable?): GlideRequest<TranscodeType> {
        return super.error(drawable) as GlideRequest<TranscodeType>
    }

    override fun error(id: Int): GlideRequest<TranscodeType> {
        return super.error(id) as GlideRequest<TranscodeType>
    }

    override fun theme(theme: Resources.Theme?): GlideRequest<TranscodeType> {
        return super.theme(theme) as GlideRequest<TranscodeType>
    }

    override fun skipMemoryCache(skip: Boolean): GlideRequest<TranscodeType> {
        return super.skipMemoryCache(skip) as GlideRequest<TranscodeType>
    }

    override fun override(width: Int, height: Int): GlideRequest<TranscodeType> {
        return super.override(width, height) as GlideRequest<TranscodeType>
    }

    override fun override(size: Int): GlideRequest<TranscodeType> {
        return super.override(size) as GlideRequest<TranscodeType>
    }

    override fun signature(key: Key): GlideRequest<TranscodeType> {
        return super.signature(key) as GlideRequest<TranscodeType>
    }

    // Corrected 'set' method: Added : Any constraint to the type parameter Y to ensure non-nullable.
    override fun <Y : Any> set(option: Option<Y>, y: Y): GlideRequest<TranscodeType> {
        return super.set(option, y) as GlideRequest<TranscodeType>
    }

    // Corrected 'decode' method signature: changed to non-nullable Class<*> and parameter name to resourceClass.
    override fun decode(resourceClass: Class<*>): GlideRequest<TranscodeType> {
        return super.decode(resourceClass) as GlideRequest<TranscodeType>
    }

    override fun encodeFormat(format: Bitmap.CompressFormat): GlideRequest<TranscodeType> {
        return super.encodeFormat(format) as GlideRequest<TranscodeType>
    }

    override fun encodeQuality(value: Int): GlideRequest<TranscodeType> {
        return super.encodeQuality(value) as GlideRequest<TranscodeType>
    }

    override fun frame(value: Long): GlideRequest<TranscodeType> {
        return super.frame(value) as GlideRequest<TranscodeType>
    }

    override fun format(format: DecodeFormat): GlideRequest<TranscodeType> {
        return super.format(format) as GlideRequest<TranscodeType>
    }

    override fun disallowHardwareConfig(): GlideRequest<TranscodeType> {
        return super.disallowHardwareConfig() as GlideRequest<TranscodeType>
    }

    override fun downsample(strategy: DownsampleStrategy): GlideRequest<TranscodeType> {
        return super.downsample(strategy) as GlideRequest<TranscodeType>
    }

    override fun timeout(value: Int): GlideRequest<TranscodeType> {
        return super.timeout(value) as GlideRequest<TranscodeType>
    }

    override fun optionalCenterCrop(): GlideRequest<TranscodeType> {
        return super.optionalCenterCrop() as GlideRequest<TranscodeType>
    }

    override fun centerCrop(): GlideRequest<TranscodeType> {
        return super.centerCrop() as GlideRequest<TranscodeType>
    }

    override fun optionalFitCenter(): GlideRequest<TranscodeType> {
        return super.optionalFitCenter() as GlideRequest<TranscodeType>
    }

    override fun fitCenter(): GlideRequest<TranscodeType> {
        return super.fitCenter() as GlideRequest<TranscodeType>
    }

    override fun optionalCenterInside(): GlideRequest<TranscodeType> {
        return super.optionalCenterInside() as GlideRequest<TranscodeType>
    }

    override fun centerInside(): GlideRequest<TranscodeType> {
        return super.centerInside() as GlideRequest<TranscodeType>
    }

    override fun optionalCircleCrop(): GlideRequest<TranscodeType> {
        return super.optionalCircleCrop() as GlideRequest<TranscodeType>
    }

    override fun circleCrop(): GlideRequest<TranscodeType> {
        return super.circleCrop() as GlideRequest<TranscodeType>
    }

    override fun transform(transformation: Transformation<Bitmap>): GlideRequest<TranscodeType> {
        return super.transform(transformation) as GlideRequest<TranscodeType>
    }

    override fun transform(vararg transformations: Transformation<Bitmap>): GlideRequest<TranscodeType> {
        return super.transform(*transformations) as GlideRequest<TranscodeType>
    }

    @Deprecated("Deprecated in Java")
    override fun transforms(vararg transformations: Transformation<Bitmap>): GlideRequest<TranscodeType> {
        return super.transforms(*transformations) as GlideRequest<TranscodeType>
    }

    override fun optionalTransform(transformation: Transformation<Bitmap>): GlideRequest<TranscodeType> {
        return super.optionalTransform(transformation) as GlideRequest<TranscodeType>
    }

    override fun <Y> optionalTransform(clazz: Class<Y>, transformation: Transformation<Y>): GlideRequest<TranscodeType> {
        return super.optionalTransform(clazz, transformation) as GlideRequest<TranscodeType>
    }

    override fun <Y> transform(clazz: Class<Y>, transformation: Transformation<Y>): GlideRequest<TranscodeType> {
        return super.transform(clazz, transformation) as GlideRequest<TranscodeType>
    }

    override fun dontTransform(): GlideRequest<TranscodeType> {
        return super.dontTransform() as GlideRequest<TranscodeType>
    }

    override fun dontAnimate(): GlideRequest<TranscodeType> {
        return super.dontAnimate() as GlideRequest<TranscodeType>
    }

    override fun lock(): GlideRequest<TranscodeType> {
        return super.lock() as GlideRequest<TranscodeType>
    }

    override fun autoClone(): GlideRequest<TranscodeType> {
        return super.autoClone() as GlideRequest<TranscodeType>
    }

    override fun apply(options: BaseRequestOptions<*>): GlideRequest<TranscodeType> {
        return super.apply(options) as GlideRequest<TranscodeType>
    }

    override fun transition(options: TransitionOptions<*, in TranscodeType>): GlideRequest<TranscodeType> {
        return super.transition(options) as GlideRequest<TranscodeType>
    }

    override fun listener(listener: RequestListener<TranscodeType>?): GlideRequest<TranscodeType> {
        return super.listener(listener) as GlideRequest<TranscodeType>
    }

    // Corrected 'addListener' method: Parameter is now nullable `RequestListener<TranscodeType>?`.
    override fun addListener(listener: RequestListener<TranscodeType>?): GlideRequest<TranscodeType> {
        return super.addListener(listener) as GlideRequest<TranscodeType>
    }

    override fun error(builder: RequestBuilder<TranscodeType>?): GlideRequest<TranscodeType> {
        return super.error(builder) as GlideRequest<TranscodeType>
    }

    override fun error(o: Any?): GlideRequest<TranscodeType> {
        return super.error(o) as GlideRequest<TranscodeType>
    }

    override fun thumbnail(builder: RequestBuilder<TranscodeType>?): GlideRequest<TranscodeType> {
        return super.thumbnail(builder) as GlideRequest<TranscodeType>
    }

    override fun thumbnail(vararg builders: RequestBuilder<TranscodeType>): GlideRequest<TranscodeType> {
        return super.thumbnail(*builders) as GlideRequest<TranscodeType>
    }

    @Deprecated("Deprecated in Java")
    override fun thumbnail(sizeMultiplier: Float): GlideRequest<TranscodeType> {
        return super.thumbnail(sizeMultiplier) as GlideRequest<TranscodeType>
    }

    override fun load(o: Any?): GlideRequest<TranscodeType> {
        return super.load(o) as GlideRequest<TranscodeType>
    }

    override fun load(bitmap: Bitmap?): GlideRequest<TranscodeType> {
        return super.load(bitmap) as GlideRequest<TranscodeType>
    }

    override fun load(drawable: Drawable?): GlideRequest<TranscodeType> {
        return super.load(drawable) as GlideRequest<TranscodeType>
    }

    override fun load(string: String?): GlideRequest<TranscodeType> {
        return super.load(string) as GlideRequest<TranscodeType>
    }

    override fun load(uri: Uri?): GlideRequest<TranscodeType> {
        return super.load(uri) as GlideRequest<TranscodeType>
    }

    override fun load(file: File?): GlideRequest<TranscodeType> {
        return super.load(file) as GlideRequest<TranscodeType>
    }

    override fun load(id: Int?): GlideRequest<TranscodeType> {
        return super.load(id) as GlideRequest<TranscodeType>
    }

    @Deprecated("Deprecated in Java")
    override fun load(url: URL?): GlideRequest<TranscodeType> {
        return super.load(url) as GlideRequest<TranscodeType>
    }

    override fun load(bytes: ByteArray?): GlideRequest<TranscodeType> {
        return super.load(bytes) as GlideRequest<TranscodeType>
    }

    // Corrected 'clone' method: Explicitly calling super<RequestBuilder>.clone() to resolve ambiguity.
    override fun clone(): GlideRequest<TranscodeType> {
        return super<RequestBuilder>.clone() as GlideRequest<TranscodeType>
    }
}
