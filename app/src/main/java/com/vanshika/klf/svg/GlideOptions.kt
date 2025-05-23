package com.vanshika.klf.svg

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.Option
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.BaseRequestOptions
import com.bumptech.glide.request.RequestOptions

class GlideOptions : RequestOptions(), Cloneable {

    companion object {
        // Using nullable properties and lazy initialization to mirror Java's static fields
        private var fitCenterTransform0: GlideOptions? = null
        private var centerInsideTransform1: GlideOptions? = null
        private var centerCropTransform2: GlideOptions? = null
        private var circleCropTransform3: GlideOptions? = null
        private var noTransformation4: GlideOptions? = null
        private var noAnimation5: GlideOptions? = null

        @JvmStatic
        fun sizeMultiplierOf(value: Float): GlideOptions {
            return GlideOptions().sizeMultiplier(value) as GlideOptions
        }

        @JvmStatic
        fun diskCacheStrategyOf(strategy: DiskCacheStrategy): GlideOptions {
            return GlideOptions().diskCacheStrategy(strategy) as GlideOptions
        }

        @JvmStatic
        fun priorityOf(priority: Priority): GlideOptions {
            return GlideOptions().priority(priority) as GlideOptions
        }

        @JvmStatic
        fun placeholderOf(drawable: Drawable?): GlideOptions {
            return GlideOptions().placeholder(drawable) as GlideOptions
        }

        @JvmStatic
        fun placeholderOf(id: Int): GlideOptions {
            return GlideOptions().placeholder(id) as GlideOptions
        }

        @JvmStatic
        fun errorOf(drawable: Drawable?): GlideOptions {
            return GlideOptions().error(drawable) as GlideOptions
        }

        @JvmStatic
        fun errorOf(id: Int): GlideOptions {
            return GlideOptions().error(id) as GlideOptions
        }

        @JvmStatic
        fun skipMemoryCacheOf(skipMemoryCache: Boolean): GlideOptions {
            return GlideOptions().skipMemoryCache(skipMemoryCache) as GlideOptions
        }

        @JvmStatic
        fun overrideOf(width: Int, height: Int): GlideOptions {
            return GlideOptions().override(width, height) as GlideOptions
        }

        @JvmStatic
        fun overrideOf(size: Int): GlideOptions {
            return GlideOptions().override(size)
        }

        @JvmStatic
        fun signatureOf(key: Key): GlideOptions {
            return GlideOptions().signature(key)
        }

        @JvmStatic
        fun fitCenterTransform(): GlideOptions {
            if (fitCenterTransform0 == null) {
                fitCenterTransform0 = GlideOptions().fitCenter().autoClone()
            }
            return fitCenterTransform0!!
        }

        @JvmStatic
        fun centerInsideTransform(): GlideOptions {
            if (centerInsideTransform1 == null) {
                centerInsideTransform1 = GlideOptions().centerInside().autoClone()
            }
            return centerInsideTransform1!!
        }

        @JvmStatic
        fun centerCropTransform(): GlideOptions {
            if (centerCropTransform2 == null) {
                centerCropTransform2 = GlideOptions().centerCrop().autoClone() as GlideOptions
            }
            return centerCropTransform2!!
        }

        @JvmStatic
        fun circleCropTransform(): GlideOptions {
            if (circleCropTransform3 == null) {
                circleCropTransform3 = GlideOptions().circleCrop().autoClone() as GlideOptions
            }
            return circleCropTransform3!!
        }

        @JvmStatic
        fun bitmapTransform(transformation: Transformation<Bitmap>): GlideOptions {
            return GlideOptions().transform(transformation) as GlideOptions
        }

        @JvmStatic
        fun noTransformation(): GlideOptions {
            if (noTransformation4 == null) {
                noTransformation4 = GlideOptions().dontTransform().autoClone() as GlideOptions
            }
            return noTransformation4!!
        }

        @JvmStatic
        fun <T> option(option: Option<T>, t: T): GlideOptions {
            return GlideOptions().set(option, t) as GlideOptions
        }

        @JvmStatic
        fun decodeTypeOf(clazz: Class<*>): GlideOptions {
            return GlideOptions().decode(clazz) as GlideOptions
        }

        @JvmStatic
        fun formatOf(format: DecodeFormat): GlideOptions {
            return GlideOptions().format(format) as GlideOptions
        }

        @JvmStatic
        fun frameOf(value: Long): GlideOptions {
            return GlideOptions().frame(value) as GlideOptions
        }

        @JvmStatic
        fun downsampleOf(strategy: DownsampleStrategy): GlideOptions {
            return GlideOptions().downsample(strategy) as GlideOptions
        }

        @JvmStatic
        fun timeoutOf(value: Int): GlideOptions {
            return GlideOptions().timeout(value) as GlideOptions
        }

        @JvmStatic
        fun encodeQualityOf(value: Int): GlideOptions {
            return GlideOptions().encodeQuality(value) as GlideOptions
        }

        @JvmStatic
        fun encodeFormatOf(format: Bitmap.CompressFormat): GlideOptions {
            return GlideOptions().encodeFormat(format) as GlideOptions
        }

        @JvmStatic
        fun noAnimation(): GlideOptions {
            if (noAnimation5 == null) {
                noAnimation5 = GlideOptions().dontAnimate().autoClone() as GlideOptions
            }
            return noAnimation5!!
        }
    }

    // Overrides for fluent API to return GlideOptions instead of RequestOptions
    // Kotlin's smart cast or explicit cast `as GlideOptions` is used here.

    override fun sizeMultiplier(value: Float): GlideOptions {
        return super.sizeMultiplier(value) as GlideOptions
    }

    override fun useUnlimitedSourceGeneratorsPool(flag: Boolean): GlideOptions {
        return super.useUnlimitedSourceGeneratorsPool(flag) as GlideOptions
    }

    override fun useAnimationPool(flag: Boolean): GlideOptions {
        return super.useAnimationPool(flag) as GlideOptions
    }

    override fun onlyRetrieveFromCache(flag: Boolean): GlideOptions {
        return super.onlyRetrieveFromCache(flag) as GlideOptions
    }

    override fun diskCacheStrategy(strategy: DiskCacheStrategy): GlideOptions {
        return super.diskCacheStrategy(strategy) as GlideOptions
    }

    override fun priority(priority: Priority): GlideOptions {
        return super.priority(priority) as GlideOptions
    }

    override fun placeholder(drawable: Drawable?): GlideOptions {
        return super.placeholder(drawable) as GlideOptions
    }

    override fun placeholder(id: Int): GlideOptions {
        return super.placeholder(id) as GlideOptions
    }

    override fun fallback(drawable: Drawable?): GlideOptions {
        return super.fallback(drawable) as GlideOptions
    }

    override fun fallback(id: Int): GlideOptions {
        return super.fallback(id) as GlideOptions
    }

    override fun error(drawable: Drawable?): GlideOptions {
        return super.error(drawable) as GlideOptions
    }

    override fun error(id: Int): GlideOptions {
        return super.error(id) as GlideOptions
    }

    override fun theme(theme: Resources.Theme?): GlideOptions {
        return super.theme(theme) as GlideOptions
    }

    override fun skipMemoryCache(skip: Boolean): GlideOptions {
        return super.skipMemoryCache(skip) as GlideOptions
    }

    override fun override(width: Int, height: Int): GlideOptions {
        return super.override(width, height) as GlideOptions
    }

    override fun override(size: Int): GlideOptions {
        return super.override(size) as GlideOptions
    }

    override fun signature(key: Key): GlideOptions {
        return super.signature(key) as GlideOptions
    }

    override fun clone(): GlideOptions {
        return super<RequestOptions>.clone() as GlideOptions
    }

    @Suppress("UNCHECKED_CAST")
    override fun <Y> set(option: Option<Y>, y: Y): GlideOptions {
        return super.set(option, y as (Y & Any)) as GlideOptions
    }

    override fun decode(clazz: Class<*>): GlideOptions {
        return super.decode(clazz) as GlideOptions
    }

    override fun encodeFormat(format: Bitmap.CompressFormat): GlideOptions {
        return super.encodeFormat(format) as GlideOptions
    }

    override fun encodeQuality(value: Int): GlideOptions {
        return super.encodeQuality(value) as GlideOptions
    }

    override fun frame(value: Long): GlideOptions {
        return super.frame(value) as GlideOptions
    }

    override fun format(format: DecodeFormat): GlideOptions {
        return super.format(format) as GlideOptions
    }

    override fun disallowHardwareConfig(): GlideOptions {
        return super.disallowHardwareConfig() as GlideOptions
    }

    override fun downsample(strategy: DownsampleStrategy): GlideOptions {
        return super.downsample(strategy) as GlideOptions
    }

    override fun timeout(value: Int): GlideOptions {
        return super.timeout(value) as GlideOptions
    }

    override fun optionalCenterCrop(): GlideOptions {
        return super.optionalCenterCrop() as GlideOptions
    }

    override fun centerCrop(): GlideOptions {
        return super.centerCrop() as GlideOptions
    }

    override fun optionalFitCenter(): GlideOptions {
        return super.optionalFitCenter() as GlideOptions
    }

    override fun fitCenter(): GlideOptions {
        return super.fitCenter() as GlideOptions
    }

    override fun optionalCenterInside(): GlideOptions {
        return super.optionalCenterInside() as GlideOptions
    }

    override fun centerInside(): GlideOptions {
        return super.centerInside() as GlideOptions
    }

    override fun optionalCircleCrop(): GlideOptions {
        return super.optionalCircleCrop() as GlideOptions
    }

    override fun circleCrop(): GlideOptions {
        return super.circleCrop() as GlideOptions
    }

    override fun transform(transformation: Transformation<Bitmap>): GlideOptions {
        return super.transform(transformation) as GlideOptions
    }

    override fun transform(vararg transformations: Transformation<Bitmap>): GlideOptions {
        return super.transform(*transformations) as GlideOptions
    }

    @Deprecated("Deprecated in Java, also deprecated here.")
    override fun transforms(vararg transformations: Transformation<Bitmap>): GlideOptions {
        return super.transforms(*transformations) as GlideOptions
    }

    override fun optionalTransform(transformation: Transformation<Bitmap>): GlideOptions {
        return super.optionalTransform(transformation) as GlideOptions
    }

    override fun <Y> optionalTransform(clazz: Class<Y>, transformation: Transformation<Y>): GlideOptions {
        return super.optionalTransform(clazz, transformation) as GlideOptions
    }

    override fun <Y> transform(clazz: Class<Y>, transformation: Transformation<Y>): GlideOptions {
        return super.transform(clazz, transformation) as GlideOptions
    }

    override fun dontTransform(): GlideOptions {
        return super.dontTransform() as GlideOptions
    }

    override fun dontAnimate(): GlideOptions {
        return super.dontAnimate() as GlideOptions
    }

    override fun apply(options: BaseRequestOptions<*>): GlideOptions {
        return super.apply(options) as GlideOptions
    }

    override fun lock(): GlideOptions {
        return super.lock() as GlideOptions
    }

    override fun autoClone(): GlideOptions {
        return super.autoClone() as GlideOptions
    }
}