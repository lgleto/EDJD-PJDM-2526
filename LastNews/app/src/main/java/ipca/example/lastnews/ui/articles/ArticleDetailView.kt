package ipca.example.lastnews.ui.articles

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import ipca.example.lastnews.ui.theme.LastNewsTheme

@Composable
fun ArticleDetailView(
    articleUrl: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ){
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    webViewClient = WebViewClient()
                    settings.loadWithOverviewMode = true
                    settings.useWideViewPort = true
                }
            },
            update = { webView ->
                webView.loadUrl(articleUrl)
            }
          )

    }
}

@Preview(showBackground = true)
@Composable
fun ArticleDetailViewPreview() {
    LastNewsTheme {
        ArticleDetailView(
            articleUrl = "https://example.com/article"
        )
    }
}