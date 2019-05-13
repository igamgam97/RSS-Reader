package com.example.rssanimereader.util

import android.content.Context.MODE_PRIVATE
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.net.Uri
import com.example.rssanimereader.ProvideContextApplication
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream


object ImageSaver {

    fun saveImageToInternalStorage(bitmap: Bitmap, name: String): Uri {
        val wrapper = ContextWrapper(ProvideContextApplication.applicationContext())

        var file = wrapper.getDir("Images", MODE_PRIVATE)

        file = File(file, "$name.jpg")

        try {
            val stream: OutputStream?

            stream = FileOutputStream(file)

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)


            stream.flush()

            stream.close()

        } catch (e: IOException) {
            e.printStackTrace()
        }

        return Uri.parse(file.absolutePath)
    }
}