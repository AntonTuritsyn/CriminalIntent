package com.android.criminalintent

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlin.math.roundToInt

fun getScaledBitmap(path: String, destWith: Int, destHeight: Int): Bitmap {
//    Чтение размера изображения
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeFile(path, options)
    val srcWidth = options.outWidth.toFloat()
    val srcHeight = options.outHeight.toFloat()
//    Уменьшение масштаба
    val sampleSize = if (srcHeight <= destHeight && srcWidth <= destWith) {
        1
    } else {
        val heightScale = srcHeight / destHeight
        val widthScale = srcWidth / destWith
        minOf(heightScale, widthScale).roundToInt()
    }
//    Создание подходящего изображения
    return BitmapFactory.decodeFile(path, BitmapFactory.Options().apply {
        inSampleSize = sampleSize
    })
}