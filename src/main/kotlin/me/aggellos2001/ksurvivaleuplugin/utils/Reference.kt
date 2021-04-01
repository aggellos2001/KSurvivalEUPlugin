package me.aggellos2001.ksurvivaleuplugin.utils

import java.lang.ref.SoftReference
import java.lang.ref.WeakReference

fun <T> T.wrapToWeakReference(): WeakReference<T> {
    return WeakReference<T>(this)
}

fun <T> T.wrapToSoftReference(): SoftReference<T> {
    return SoftReference<T>(this)
}