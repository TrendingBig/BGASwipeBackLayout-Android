package cn.bingoogolapple.swipebacklayout.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackLayout;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/12/27 下午5:35
 * 描述:
 */
public class BaseActivity extends AppCompatActivity implements BGASwipeBackLayout.PanelSlideListener {
    protected BGASwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initSwipeBackFinish();
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 初始化滑动返回
     */
    private void initSwipeBackFinish() {
        if (isSupportSwipeBack()) {
            mSwipeBackLayout = new BGASwipeBackLayout(this);
            mSwipeBackLayout.attachToActivity(this);
            mSwipeBackLayout.setPanelSlideListener(this);
        }
    }

    /**
     * 是否支持滑动返回
     *
     * @return
     */
    protected boolean isSupportSwipeBack() {
        return true;
    }

    /**
     * 设置滑动返回是否可用
     *
     * @param swipeBackEnable
     */
    protected void setSwipeBackEnable(boolean swipeBackEnable) {
        if (mSwipeBackLayout != null) {
            mSwipeBackLayout.setSwipeBackEnable(swipeBackEnable);
        }
    }

    @Override
    public void onPanelClosed(View view) {
    }

    @Override
    public void onPanelOpened(View view) {
        swipeBackward();
    }

    @Override
    public void onPanelSlide(View view, float v) {
    }

    @Override
    public void onBackPressed() {
        backward();
    }

    /**
     * 跳转到下一个Activity，并且销毁当前Activity
     *
     * @param cls 下一个Activity的Class
     */
    public void forwardAndFinish(Class<?> cls) {
        forward(cls);
        finish();
    }

    /**
     * 跳转到下一个Activity，不销毁当前Activity
     *
     * @param cls 下一个Activity的Class
     */
    public void forward(Class<?> cls) {
        KeyboardUtil.closeKeyboard(this);
        startActivity(new Intent(this, cls));
        executeForwardAnim();
    }

    public void forward(Class<?> cls, int requestCode) {
        forward(new Intent(this, cls), requestCode);
    }

    public void forwardAndFinish(Intent intent) {
        forward(intent);
        finish();
    }

    public void forward(Intent intent) {
        KeyboardUtil.closeKeyboard(this);
        startActivity(intent);
        executeForwardAnim();
    }

    public void forward(Intent intent, int requestCode) {
        KeyboardUtil.closeKeyboard(this);
        startActivityForResult(intent, requestCode);
        executeForwardAnim();
    }

    /**
     * 执行跳转到下一个Activity的动画
     */
    public void executeForwardAnim() {
        overridePendingTransition(R.anim.activity_forward_enter, R.anim.activity_forward_exit);
    }

    /**
     * 回到上一个Activity，并销毁当前Activity
     */
    public void backward() {
        KeyboardUtil.closeKeyboard(this);
        finish();
        executeBackwardAnim();
    }

    /**
     * 滑动返回上一个Activity，并销毁当前Activity
     */
    public void swipeBackward() {
        KeyboardUtil.closeKeyboard(this);
        finish();
        executeSwipeBackAnim();
    }

    /**
     * 回到上一个Activity，并销毁当前Activity（应用场景：欢迎、登录、注册这三个界面）
     *
     * @param cls 上一个Activity的Class
     */
    public void backwardAndFinish(Class<?> cls) {
        KeyboardUtil.closeKeyboard(this);
        startActivity(new Intent(this, cls));
        executeBackwardAnim();
        finish();
    }

    /**
     * 执行回到到上一个Activity的动画
     */
    public void executeBackwardAnim() {
        overridePendingTransition(R.anim.activity_backward_enter, R.anim.activity_backward_exit);
    }

    /**
     * 执行滑动返回到到上一个Activity的动画
     */
    public void executeSwipeBackAnim() {
        overridePendingTransition(R.anim.activity_swipeback_enter, R.anim.activity_swipeback_exit);
    }
}