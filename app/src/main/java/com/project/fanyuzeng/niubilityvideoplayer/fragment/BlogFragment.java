package com.project.fanyuzeng.niubilityvideoplayer.fragment;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.project.fanyuzeng.niubilityvideoplayer.R;

/**
 * Created by fanyuzeng on 2017/9/22.
 * Function:
 */
public class BlogFragment extends BaseFragment {

    private WebView mWebView;
    private ProgressBar mProgressBar;
    public static final int MAX_VALUE=100;
    public static final String BLOG_URL="http://m.blog.csdn.net/Blog?username=sinat_33661267";
    @Override
    protected void initDatas() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_blog;
    }

    @Override
    protected void initViews() {
        mWebView = bindViewId(R.id.id_webview);
        mProgressBar = bindViewId(R.id.id_pb_progress);
        mProgressBar.setMax(MAX_VALUE);
        WebSettings settings = mWebView.getSettings();
        settings.setBuiltInZoomControls(true);
        settings.setJavaScriptEnabled(true);
        mWebView.loadUrl(BLOG_URL);
        mWebView.setWebChromeClient(mWebChromeClient);
        mWebView.setWebViewClient(mWebViewClient);

    }

    private WebViewClient mWebViewClient=new WebViewClient(){
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(BLOG_URL);
            return true;
        }
    };
    private WebChromeClient mWebChromeClient=new WebChromeClient(){
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mProgressBar.setProgress(newProgress);
            if (mProgressBar.getProgress()==MAX_VALUE){
                mProgressBar.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }
    };
}
