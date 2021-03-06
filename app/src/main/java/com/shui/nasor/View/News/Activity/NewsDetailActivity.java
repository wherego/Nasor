package com.shui.nasor.View.News.Activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.shui.nasor.Base.BaseNormalActivity;
import com.shui.nasor.R;
import com.shui.nasor.View.viewHelper.ViewHelperController;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * 作者： max_Shui on 2016/12/30.
 * 邮箱：shuicz0505@qq.com
 * ( ゜- ゜)つロ  ( ゜- ゜)つロ  ( ゜- ゜)つロ  ( ゜- ゜)つロ
 * ( ゜- ゜)つロ  ( ゜- ゜)つロ  ( ゜- ゜)つロ  ( ゜- ゜)つロ
 * ( ゜- ゜)つロ  ( ゜- ゜)つロ  ( ゜- ゜)つロ  ( ゜- ゜)つロ
 * 新闻详情页
 */


public class NewsDetailActivity extends BaseNormalActivity {
    @BindView(R.id.normal_toolbar)
    Toolbar newsToolbar;
    @BindView(R.id.weChatWebView)
    WebView newsWebView;
    private MenuItem item;
    private Intent intent;
    private String url,title;
    private boolean isShowLoading=false;//是否正在显示加载中
    private ViewHelperController controller;//加载提示
    @Override
    protected void initEventAndData() {
        intent=getIntent();
        url=intent.getStringExtra("url");
        title=intent.getStringExtra("title");
        setToolbar(newsToolbar,title);
        controller=new ViewHelperController(newsWebView);
        WebSettings settings=newsWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setUseWideViewPort(true);
        newsWebView.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                webView.loadUrl(s);
                return true;
            }
        });
        newsWebView.setWebChromeClient(new WebChromeClient()
        {
            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
                if (i<100)
                {
                    if (!isShowLoading) {
                        //加载中
                        showLoading();
                        isShowLoading=true;
                    }
                }
                if (i==100)
                {
                    //加载完成
                    hiddenLoading();
                }
            }

            @Override
            public void onReceivedTitle(WebView webView, String s) {
                super.onReceivedTitle(webView, s);
                setTitle(s);//设置title
            }
        });
        newsWebView.loadUrl(url);//开始加载
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_menu,menu);
        item=menu.findItem(R.id.action_news_share);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_news_share:
                //分享操作
                onShare(title,title,url);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onShare(String title,String content,String link)
    {
        OnekeyShare oks = new OnekeyShare();
//关闭sso授权
        oks.disableSSOWhenAuthorize();
// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle(title);
// titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl(link);
// text是分享文本，所有平台都需要这个字段
        oks.setText(content);
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
// url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(link);
// comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
// site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
// siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(link);
// 启动分享GUI
        oks.show(this);
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_wechat;
    }

    @Override
    public void onBackPressedSupport() {
        finishAfterTransition();
    }

    /**
     * 返回事件
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (newsWebView.canGoBack())
        {
            newsWebView.goBack();
            return  true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * loading
     * @param toggle
     * @param msg
     */
    private void toggleShowLoading(boolean toggle, String msg) {
        if (null == controller) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            controller.showLoading(msg);
        } else {
            controller.restore();
        }
    }
    private void showLoading() {
        toggleShowLoading(true,null);
    }

    private void hiddenLoading() {
        toggleShowLoading(false,null);
    }


}
