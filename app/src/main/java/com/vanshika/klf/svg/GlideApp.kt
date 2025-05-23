package com.vanshika.klf.svg

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import java.io.File

object GlideApp {

    fun getPhotoCacheDir(context: Context): File? = Glide.getPhotoCacheDir(context)

    fun getPhotoCacheDir(context: Context, string: String): File? = Glide.getPhotoCacheDir(context, string)

    fun get(context: Context): Glide = Glide.get(context)

    @Deprecated("Use Glide.init(Context, GlideBuilder) instead")
    fun init(glide: Glide) {
        Glide.init(glide)
    }

    fun init(context: Context, builder: GlideBuilder) {
        Glide.init(context, builder)
    }

    fun enableHardwareBitmaps() {
        Glide.enableHardwareBitmaps()
    }

    fun tearDown() {
        Glide.tearDown()
    }

    fun with(context: Context): GlideRequests = Glide.with(context) as GlideRequests

    @Deprecated("Use with(FragmentActivity) instead")
    fun with(activity: Activity): GlideRequests = Glide.with(activity) as GlideRequests

    fun with(activity: FragmentActivity): GlideRequests = Glide.with(activity) as GlideRequests

    fun with(fragment: Fragment): GlideRequests = Glide.with(fragment) as GlideRequests

    @Deprecated("Use androidx.fragment.app.Fragment instead")
    fun with(fragment: android.app.Fragment): GlideRequests = Glide.with(fragment) as GlideRequests

    fun with(view: View): GlideRequests = Glide.with(view) as GlideRequests
}
