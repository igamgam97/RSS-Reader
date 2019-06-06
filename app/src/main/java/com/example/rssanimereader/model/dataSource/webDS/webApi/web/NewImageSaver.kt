package com.example.rssanimereader.model.dataSource.webDS.webApi.web

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.example.rssanimereader.ProvideContextApplication
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.net.MalformedURLException

class NewImageSaver{

    @Throws(MalformedURLException::class)
    private fun downloadImage(urlPath: String): Bitmap {
        val streamFromURLLoader = StreamFromURLLoader()
        streamFromURLLoader(urlPath).inputStream.use {
            return BitmapFactory.decodeStream(it)
        }
    }

    fun saveImageToInternalStorage(urlPath: String, name: String): Uri {
        val bitmap = downloadImage(urlPath)
        val wrapper = ContextWrapper(ProvideContextApplication.applicationContext())
        var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
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