//package ru.yofik.athena.common.data.utils
//
//import androidx.test.platform.app.InstrumentationRegistry
//import timber.log.Timber
//import java.io.IOException
//import java.io.InputStream
//
//object JsonReader {
//    fun getNetworkResponse(path: String): String {
//        try {
//            val context = InstrumentationRegistry.getInstrumentation().context
//            val jsonStream: InputStream = context.assets.open("network-responses/$path")
//            return String(jsonStream.readBytes())
//        } catch (exception: IOException) {
//            Timber.e(exception, "Error reading network response json asset")
//            throw exception
//        }
//    }
//}
