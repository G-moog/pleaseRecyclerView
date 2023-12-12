package com.example.myapplication.webview

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.sample.R
import com.example.sample.databinding.ActivityWebViewBinding
import kotlinx.coroutines.launch

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWebViewBinding


    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            // Boolean 반환
            if (it) {
                // 권한 허용
            } else {
                // 권한 거절
            }
        }

    private fun checkPermission() {
        // Android 13 이상 기기에서 이미지 권한을 받을 때는 READ_MEDIA_IMAGES 로 받아야하기 때문에 버전체크 필요
        // 이처럼 버전별로 다른 권한설정이 필요할 때가 있음
        // Android 버전이 올라갈 때마다 공식문서에 권한 관련 변경사항 숙지 요망
        // 만약 설정 안해놨다면 앱 뻗음
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        permissionLauncher.launch(permission)
    }

    private fun showPermissionDialog() {
        AlertDialog.Builder(this) // context
            .setTitle("권한 설정 필요")
            .setMessage("권한 동의가 필요합니다.\n권한 설정 페이지로 이동하시겠습니까?")
            .setPositiveButton("확인") { _, _ ->
                val intent = Intent().apply {
                    action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    data = Uri.fromParts("package", packageName, null)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                startActivity(intent)
            }
            .setNegativeButton("취소") { _, _ -> }
            .create()
            .show()
    }

    private val customWebViewClient = object : WebViewClient() {
        override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
            super.onReceivedError(view, request, error)
        }

        override fun onReceivedHttpError(view: WebView, request: WebResourceRequest, errorResponse: WebResourceResponse) {
            super.onReceivedHttpError(view, request, errorResponse)
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)

        }
    }

    private fun initView() {
        binding.webView.apply {
            // 자바스크립트 셋팅
            settings.javaScriptEnabled = true

            // 브릿지 연결
            addJavascriptInterface(WebAppInterface(), "BridgeName")

            loadUrl("file:///android_asset/sample.html")
        }
    }

    // inner Class 이름은 임의로
    inner class WebAppInterface {

        @JavascriptInterface
        fun showToast(toast: String) {
            if (toast.isNotBlank()) {
                Toast.makeText(this@WebViewActivity, toast, Toast.LENGTH_SHORT).show()
            } else {
                // evaluateJavascript 는 비동기로 호출해야함
                // 아래 코드는 코루틴을 이용
                lifecycleScope.launch {
                    val javascriptFunction = "updateMessage('Sample Message')"
                    binding.webView.evaluateJavascript(javascriptFunction) { // 반환값은 String, 없다면 null
                        Log.e("#####", "it = $it")
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view)

        /*initView()*/
        /*checkPermission()*/
        showPermissionDialog()

/*        binding.webView.webViewClient = customWebViewClient
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl("https://www.naver.com")*/



    }

}