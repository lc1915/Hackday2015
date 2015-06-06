package unique.liuchang.accessibilityservice;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

public class MyAccessibilityService extends AccessibilityService {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(1111, new Notification());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    @SuppressLint("NewApi")
    public void onAccessibilityEvent(AccessibilityEvent event) {

        int eventType = event.getEventType();
        switch (eventType) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:// 通知栏事件
                List<CharSequence> texts = event.getText();
                if (!texts.isEmpty()) {
                    for (CharSequence text : texts) {
                        String content = text.toString();
                        if (content.contains("Reminder")) {
                            // 监听到notification，打开通知
                            if (event.getParcelableData() != null
                                    && event.getParcelableData() instanceof Notification) {
                                Notification notification = (Notification) event
                                        .getParcelableData();
                                PendingIntent pendingIntent = notification.contentIntent;
                                try {
                                    pendingIntent.send();
                                } catch (PendingIntent.CanceledException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                String className = event.getClassName().toString();
                Log.e("b", className);
                if (className.equals("unique.liuchang.sendnotification.NotificationVIew")) {
                    AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
                    /*AccessibilityNodeInfo nodeInfo = event.getSource();
                    nodeInfo.findAccessibilityNodeInfosByViewId("unique.liuchang.sendnotification:id/new_button1");*/
                    if (nodeInfo != null) {
                        //List<AccessibilityNodeInfo> list = nodeInfo
                        //        .findAccessibilityNodeInfosByViewId("unique.liuchang.sendnotification:id/new_button1");
                        List<AccessibilityNodeInfo> list = nodeInfo
                                .findAccessibilityNodeInfosByText("new_button1");
                        for (AccessibilityNodeInfo n : list) {
                            n.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        }
                    }
                    //nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
                break;
        }
    }

    @SuppressLint("NewApi")
    private void openPacket() {
        Log.e("a", "aaa");
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        nodeInfo.findAccessibilityNodeInfosByViewId("unique.liuchang.sendnotification:id/new_button1");
        /*if (nodeInfo != null) {
            List<AccessibilityNodeInfo> list = nodeInfo
                    .findAccessibilityNodeInfosByText("Alarm");
            for (AccessibilityNodeInfo n : list) {
                n.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }*/
        nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
    }

    @SuppressLint("NewApi")
    private void getPacket() {
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (rootNode != null) {
            List<AccessibilityNodeInfo> nodeInfos = rootNode
                    .findAccessibilityNodeInfosByText("Alarm");
            for (AccessibilityNodeInfo nodeInfo : nodeInfos) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
    }

    // get the source node of the event
    //AccessibilityNodeInfo nodeInfo = event.getSource();

    // Use the event and node information to determine
    // what action to take

    // take action on behalf of the user
    //nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);

    // recycle the nodeInfo object
    //nodeInfo.recycle();


    @Override
    public void onInterrupt() {

    }
}
