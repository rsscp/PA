package tests

import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException

fun getJsonString(url: String): String {
    val client = OkHttpClient()
    val request = Request.Builder()
        .url(url)
        .build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful)
            throw IOException("Unexpected code $response")
        return response.body!!.string()
    }
}