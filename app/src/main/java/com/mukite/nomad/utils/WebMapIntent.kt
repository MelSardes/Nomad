package com.mukite.nomad.utils

import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

const val URL = "https://www.google.com/maps/place/Hotel+R%C3%A9sidence+Nomad/@0.4829693,9.3961718,17z/data=!3m1!4b1!4m9!3m8!1s0x107f25a7a4b117af:0x93a011b88f962c60!5m2!4m1!1i2!8m2!3d0.4829693!4d9.3987521!16s%2Fg%2F1hd_6h_j9?entry=ttu"

@Composable
fun MapScreen(modifier: Modifier = Modifier) {
    var webView: WebView? by remember { mutableStateOf(null) }

//    Column(
//        modifier = modifier.background(MaterialTheme.colorScheme.background)
//    ) {
        AndroidView(
            modifier = modifier,
            factory = { context ->
                WebView(context).apply {
                    webView = this
                    settings.javaScriptEnabled = true
                    settings.loadWithOverviewMode = true
                    settings.useWideViewPort = true
                    webViewClient = WebViewClient()
                }

            },
            update = { view ->
                view.loadDataWithBaseURL(null, getHtmlContent(), "text/html", "UTF-8", null)
            }
        )
//    }
}

fun getHtmlContent(): String {

    val url = "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3989.676573192195!2d9.396171811423875!3d0.4829692995102696!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x107f25a7a4b117af%3A0x93a011b88f962c60!2sHotel%20R%C3%A9sidence%20Nomad!5e0!3m2!1sen!2sga!4v1703258908958!5m2!1sen!2sga"
    return """
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Map</title>
        </head>
        <body style="margin: 0">
            <iframe 
            src="$url" 
            width="100%" height="100%" style="border:0;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
        </body>
        </html>
    """.trimIndent()
}