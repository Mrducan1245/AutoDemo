package com.example.autodemo;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Path;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class AutoBilily extends AccessibilityService {

    private final String LAUCHER = "com.syriusrobotics.platform.jarvis.MainFlutterActivity";
    private final  String TAG = "SpeedPicker";
    private AccessibilityNodeInfo goalNode;
    public String eleText = "领淘金币";
    private final MainHandler mainHandler = new MainHandler(this);
    //定时任务管理器
    public static AlarmManager alarmManager;
    private  PowerManager pm;
    private  boolean screenOn = false;
    private boolean isClickInputBtnFirst = true;

    //和闹钟启动有关
    public static long startMills,intervalMills;
    public static PendingIntent pendingIntent;

    //消息处理器
    private class MainHandler extends Handler{
        //弱引用持有AutoBilily服务，GC回收时会被回收掉
        private WeakReference<AutoBilily> weakReference;

        public MainHandler(AutoBilily autoBilily){
            this.weakReference = new WeakReference<>(autoBilily);
        }
        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        public void handleMessage(@NonNull Message msg) {
            AutoBilily autoBilily = weakReference.get();
            super.handleMessage(msg);
            if (null!=autoBilily){
                //执行业务逻辑,当消息为1时就开始抓取目标node并且点击
                if (msg.what == 1){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //说明在什么界面然后，执行相应的操作,bounds:[370,569][711,674]
                            dispatchGestureView(1,540,622);
                            Log.e(TAG,"已经点击了坐标位置");
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
//                            goalNode = findNodeByText((AccessibilityNodeInfo) msg.obj, "+55");
//                            if (goalNode == null) return;
//                            performClick(goalNode);
//                            [370,745][711,989]
                            dispatchGestureView(1,540,833);
                            Log.e(TAG,"已经点击了坐标位置2");
//                            Log.e(TAG,"已经点击了node"+goalNode);
                        }
                    }).start();
                }else if (msg.what == 2){
//                    [811,480][1015,566]
                    dispatchGestureView(1,913,523);
//                    goalNode = findNodeByText((AccessibilityNodeInfo) msg.obj, "明日再来");
//                    if (goalNode == null) return;
////                    performClick(goalNode);
                    Log.e(TAG,"已经点击了坐标3");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            performGlobalAction(GLOBAL_ACTION_HOME);
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            performGlobalAction(GLOBAL_ACTION_LOCK_SCREEN);
                        }
                    }).start();
                }
            }
        }
    }

    public AutoBilily() {


    }

    /**
     * 变化的时候调用这里
     * @param event
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        switch (eventType){
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                if (event.getClassName() == null) return;
                String className = event.getClassName().toString();
//                Log.e("服务","窗口内容有变化,当前界面是："+className);
                //只要界面有变化就获取当前界面的根布局
                AccessibilityNodeInfo rootNode = getRootInActiveWindow();
                AccessibilityNodeInfo webView;
                if (rootNode == null){
                    Log.e(TAG,"rootNode为空");
                    return;
                }
                ArrayList<String> textCollect = judgeUI(rootNode);
                if (textCollect.contains("待命区")){
                    Log.e("界面","前往待命区。。。");
                    return;
                }else if (textCollect.contains("异常处理区")){
                    Log.e("界面","正在前往异常处理区。。。");
                    return;
                } else if (textCollect.contains("打包绑定区")&&textCollect.contains("前往")){
                    Log.e("界面","前往打包绑定区。。。");
                    return;
                }else if (textCollect.contains("扫码绑定") ){
                    Log.e("界面","等待绑定载物箱。。。");
                    webView = returnWebView(rootNode);

                    //如果是第一道这个界面那么就点击按钮
                    if (isClickInputBtnFirst){
                        AccessibilityNodeInfo inputBtn = findNodeByText(webView,"输入");
                        performClick(inputBtn);
                        return;
                    }
                    AccessibilityNodeInfo inputEdt = findNodeByIsFouse(webView);
                    //点击输入按钮并且输入文字
                    try {
                        clickBtnAndInputText(inputEdt);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    isClickInputBtnFirst = false;
                    return;

                }else if (textCollect.contains("前往")&& !textCollect.contains("打包绑定区")){
                    isClickInputBtnFirst = true;
                    Log.e("界面","前往拣货点中。。。。");
                    return;
                }else if (textCollect.contains("输入") && textCollect.contains("异常上报")&& !textCollect.contains("扫码绑定") ){
                    Log.e("界面", "正在拣货点位置待拣货");
                    webView = returnWebView(rootNode);

                    //如果是第一道这个界面那么就点击按钮
                    if (isClickInputBtnFirst){
                        AccessibilityNodeInfo inputBtn = findNodeByText(webView,"输入");
                        performClick(inputBtn);
                        return;
                    }
                    AccessibilityNodeInfo inputEdt = findNodeByIsFouse(webView);
                    //点击输入按钮并且输入文字
                    try {
                        clickBtnAndInputText(inputEdt);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    isClickInputBtnFirst = false;
                    return;
                }else if (textCollect.contains("确定")) {
                    //包含着两个那么直接点击这俩按钮
                    webView = returnWebView(rootNode);
                    AccessibilityNodeInfo confirmBtn = findNodeByText(webView, "确定");
                    performClick(confirmBtn);
                    return;

                }else if (textCollect.contains("完成")){
                    webView = returnWebView(rootNode);
                    AccessibilityNodeInfo completeBtn = findNodeByText(webView, "完成");
                    performClick(completeBtn);
                    return;

                }else if (textCollect.contains("已取下")){
                    webView = returnWebView(rootNode);
                    AccessibilityNodeInfo uploadBtn = findNodeByText(webView, "已取下");
                    performClick(uploadBtn);
                    return;
                }
//                //通过找text找到eleText元素并执行点击
//                if (className.equals(LAUCHER)){
//                    wakeUpAndUnlock();//唤醒屏幕
//                    AccessibilityNodeInfo goalNode = findNodeByText2(rootNode,eleText);
//                    performClick(goalNode);
//                }else if (className.equals("com.taobao.browser.BrowserActivity")){
//                    //当跳转到这个界面时开启新的线程并循环抓取那个元素是不是一直都在，抓到了就把消息发给主线程让它点击那个按钮
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Log.e("LAUCHER","已经进入了run函数");
//                            isLoadGoalNodeSuccessfull("淘宝人生",1);
//                        }
//                    }).start();
//
//                }else if (className.equals("com.taobao.browser.exbrowser.BrowserUpperActivity")){
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            isLoadGoalNodeSuccessfull("找答案",2);
//                        }
//                    }).start();
//                    //重新设置闹钟
//                    startMills += intervalMills;//执行一次闹钟后在重新执行一次
//                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, startMills,pendingIntent);
//                }
                break;
        }
    }

    /**
     * 点击输入按钮并且在输入框里输入文字
     *     * @param inputEdt
     */
    private void clickBtnAndInputText(AccessibilityNodeInfo inputEdt) throws InterruptedException {
        if (inputEdt == null )  {
            return;
        }
        ClipboardManager clipboard = (ClipboardManager)this.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("text", "199103181516");
        clipboard.setPrimaryClip(clip);
        //焦点（n是AccessibilityNodeInfo对象）
        inputEdt.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
        //粘贴进入内容
        inputEdt.performAction(AccessibilityNodeInfo.ACTION_PASTE);
    }

    /**
     * 在webView里找寻目标node，如果找到就通过maninHandler将包含webViewNode的message发送出去
     * @param nodeText
     * @return
     */
    private void isLoadGoalNodeSuccessfull(String nodeText, int what){
        AccessibilityNodeInfo rootNode;
        AccessibilityNodeInfo webViewNode ;
        do {
             rootNode = getRootInActiveWindow();
             webViewNode = returnWebView(rootNode);
            if (webViewNode == null){
                continue;
            }
        }while (!judgeUI(webViewNode).contains(nodeText));
        Message message = Message.obtain();
        message.what = what;
        message.obj = webViewNode;
        mainHandler.sendMessage(message);
        Log.e(TAG,"已开启新的线程去监督webView内容变化");
    }

    /**
     * 当目标node在webViewNode里是，通过text找到对应node
     */
    private AccessibilityNodeInfo findNodeByText(AccessibilityNodeInfo webViewNode,String goalText){
        AccessibilityNodeInfo tempNode ;
        if (webViewNode == null) return null;
        Stack<AccessibilityNodeInfo> nodeStack = new Stack<>();
        nodeStack.add(webViewNode);
        while (!nodeStack.isEmpty()){
            tempNode = nodeStack.pop();
            if (tempNode == null) continue;
            if (tempNode.getText()!=null && tempNode.getText().toString().equals(goalText)){
                Log.e(TAG,"恭喜，找到了目标node"+goalText);
                return tempNode;
            }
            int childount = tempNode.getChildCount();
            if (childount == 0) continue;
            for (int i =0;i<childount;i++){
                nodeStack.push(tempNode.getChild(i));
            }
        }
        Log.e(TAG,"找不到目标node");
        return null;
    }

    /**
     * 当目标node在webViewNode里是，通过控件类型名称找到对应node
     */
    private AccessibilityNodeInfo findNodeByIsFouse(AccessibilityNodeInfo webViewNode){
        AccessibilityNodeInfo tempNode ;
        if (webViewNode == null) return null;
        Stack<AccessibilityNodeInfo> nodeStack = new Stack<>();
        nodeStack.add(webViewNode);
        while (!nodeStack.isEmpty()){
            tempNode = nodeStack.pop();
            if (tempNode == null) continue;
            if (tempNode.isFocused() && tempNode.isClickable()){
                Log.e(TAG,"恭喜，找到了目标输入框node");
                return tempNode;
            }
            int childount = tempNode.getChildCount();
            if (childount == 0) continue;
            for (int i =0;i<childount;i++){
                nodeStack.push(tempNode.getChild(i));
            }
        }
        Log.e(TAG,"找不到目标输入框node");
        return null;
    }


    /**
     *只能通过查找关键字判断界面处于哪个界面,返回哪个关键字与list里对比，再执行相应操作
     * @return
     */
    private ArrayList<String> judgeUI(AccessibilityNodeInfo webViewNode){
        AccessibilityNodeInfo tempNode ;
        ArrayList<String> textCollectFromUI = new ArrayList<>();
        if (webViewNode == null) return textCollectFromUI;
        Stack<AccessibilityNodeInfo> nodeStack = new Stack<>();
        nodeStack.add(webViewNode);
        while (!nodeStack.isEmpty()){
            tempNode = nodeStack.pop();
            if (tempNode == null) continue;
            if (tempNode.getText()!=null){
                textCollectFromUI.add(tempNode.getText().toString());
            }
            int childount = tempNode.getChildCount();
            if (childount == 0) continue;
            for (int i =0;i<childount;i++){
                nodeStack.push(tempNode.getChild(i));
            }
        }
        return  textCollectFromUI;
    }

    private String getNodeText(AccessibilityNodeInfo node){
        if (node.getText() == null){
            return null;
        }
        return node.getText().toString();
    }

    /**
     * 查找到webViewNode
     * @param nowNode
     * @return
     */
    private AccessibilityNodeInfo returnWebView(AccessibilityNodeInfo nowNode){
        if(nowNode==null) return null;

        if(nowNode.getClassName().toString().equals("android.webkit.WebView")){
            return nowNode;
        }
        if(nowNode.getChildCount()==0) return null;

        int size = nowNode.getChildCount();
        AccessibilityNodeInfo webViewNode = null;
        for(int i=0;i<size;i++){
            webViewNode = returnWebView(nowNode.getChild(i));
            if(webViewNode!=null) return webViewNode;
        }
        return null;
    }

    /**
     * 通过text，遍历查找“xx”元素
     * @param rootNode ，eleString
     * @return
     */
    private AccessibilityNodeInfo findNodeByText2( AccessibilityNodeInfo rootNode, String eleText) {
        if (rootNode == null){
            Log.e("stack","rootNode为空");
            return null;
        }
        AccessibilityNodeInfo tempNode = null;
        Stack<AccessibilityNodeInfo> nodeStack = new Stack<>();
        nodeStack.add(rootNode);
        while (!nodeStack.isEmpty()){
            tempNode = nodeStack.pop();
            if (tempNode == null){
                continue;
            }
            List<AccessibilityNodeInfo> list = tempNode.findAccessibilityNodeInfosByText(eleText);
            if (!list.isEmpty()){
                Log.e("stack","恭喜，找到了目标node"+list.get(0));
                return list.get(0);
            }
            int childCount = tempNode.getChildCount();
            if (childCount == 0){
                continue;
            }else {
                for (int i =0;i<childCount;i++){
                    nodeStack.push(tempNode.getChild(i));
                }
            }
        }
        Log.e("stack","抱歉，没有找到目标node");
        return null;
    }

    /**
     *   遍历找到可以点击的父节点
     */
    private void performClick(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null){
            return;
        }
        if ( nodeInfo.isClickable()){
            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            return;
        }
        AccessibilityNodeInfo parentNode = nodeInfo.getParent();
        performClick(parentNode);
    }

    /**
     //     * 实现滑动功能
     //     */
//    private void scroll(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                int sx = (nowUserEntrance.left+nowUserEntrance.right)>>1;
//                int sy = nowUserEntrance.bottom;
//                int ey = nowUserEntrance.top-21;
//                dispatchGestureScroll(2,sx,sy,sx,ey);
//                sleep(100);
//                Message message = Message.obtain();
//                message.what = 10;
//                MainHandler.sendMessage(message);
//            }
//        }).start();
//    }


    /**
     * 模拟点击事件
     * @param flag 点击的控件类型（1 能量） （0 用户入口）
     * @param x 横坐标
     * @param y 纵坐标
     */
    private boolean dispatchGestureView(final int flag, int x, int y) {
        boolean res = false;
        GestureDescription.Builder builder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            builder = new GestureDescription.Builder();
            Path p = new Path();
            p.moveTo(x, y);
            p.lineTo(x, y);
            builder.addStroke(new GestureDescription.StrokeDescription(p, 0L, 100L));
            GestureDescription gesture = builder.build();
            Log.d("","点击了位置"+"("+x+","+y+")");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            res = dispatchGesture(gesture, new GestureResultCallback(){}, null);
        }
        return res;
    }

    /**
     * 唤醒手机屏幕并解锁
     */
    @SuppressLint("ObsoleteSdkInt")
    public void wakeUpAndUnlock() {
        // 获取电源管理器对象
        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        screenOn = pm.isInteractive();
        Log.e("WakeScreen0","screenOn: " + screenOn);
        if (!screenOn) {
            // 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
            @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(
                    PowerManager.ACQUIRE_CAUSES_WAKEUP |
                            PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "ScreenOn");
            wl.acquire(1000); // 点亮屏幕
            wl.release(); // 释放
        }
        // 屏幕解锁
        KeyguardManager keyguardManager = (KeyguardManager)getSystemService(KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("unLock");
        keyguardLock.reenableKeyguard();
        keyguardLock.disableKeyguard(); // 解锁
    }

    @Override
    public void onInterrupt() {
    }
}