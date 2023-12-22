package com.mukite.nomad.utils

import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat


const val URL = "https://www.google.com/maps/place/Hotel+R%C3%A9sidence+Nomad/@0.4829693,9.3961718,17z/data=!3m1!4b1!4m9!3m8!1s0x107f25a7a4b117af:0x93a011b88f962c60!5m2!4m1!1i2!8m2!3d0.4829693!4d9.3987521!16s%2Fg%2F1hd_6h_j9?entry=ttu"
@Composable
fun WebViewScreen(modifier: Modifier = Modifier) {
    var backEnabled by remember {
        mutableStateOf(true)
    }

    var webView: WebView? = null

    AndroidView(
        modifier = modifier,
        factory = { context ->
            WebView(context).apply {
                layoutParams = android.view.ViewGroup.LayoutParams(
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.javaScriptEnabled = true
                settings.setSupportZoom(true)
                loadUrl(URL)
                webView = this
            }
        },
        update = {
            webView = it
        },
    )



}
@Composable
fun HtmlText(modifier: Modifier = Modifier) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                settings.apply {
                    javaScriptEnabled = true
                    loadWithOverviewMode = true
                    useWideViewPort = true
                    domStorageEnabled = true
                    cacheMode = WebSettings.LOAD_NO_CACHE
                }

                loadUrl("https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3989.676573192195!2d9.396171811423875!3d0.4829692995102696!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x107f25a7a4b117af%3A0x93a011b88f962c60!2sHotel%20R%C3%A9sidence%20Nomad!5e0!3m2!1sen!2sga!4v1703258908958!5m2!1sen!2sga")
            }
        },
        modifier = modifier
    )
}

@Composable
fun IFrameContent(modifier: Modifier = Modifier) {
    // Modify the iframe URL as needed
    val iframeUrl = "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3989.676573192195!2d9.396171811423875!3d0.4829692995102696!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x107f25a7a4b117af%3A0x93a011b88f962c60!2sHotel%20R%C3%A9sidence%20Nomad!5e0!3m2!1sen!2sga!4v1703258908958!5m2!1sen!2sga"

    // AndroidView composable to embed WebView
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                // WebView settings
                settings.apply {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    loadWithOverviewMode = true
                    useWideViewPort = true
                    cacheMode = WebSettings.LOAD_NO_CACHE
                    mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
                }

                // WebView client to handle URL loading
                webViewClient = WebViewClient()

                // WebView chrome client for handling UI elements
                webChromeClient = WebChromeClient()
            }
        },
        modifier = modifier
    ) { webView ->
        // Load the iframe URL
        webView.loadUrl(iframeUrl)
    }
}


@Composable
fun MapView(webViewState: WebViewState = rememberWebViewState()) {
    WebView(
        state = webViewState,
        modifier = Modifier.fillMaxSize(),
        url = "file:///android_asset/map.html"
    )
}