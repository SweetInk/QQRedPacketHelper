package luckyu.onlyone.com.luckyu;

import java.util.List;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
/* define our sercices extends of AccessibilityService*/
public class ListenerService extends AccessibilityService {

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@SuppressLint("NewApi")
	@Override
	public void onAccessibilityEvent(AccessibilityEvent event) {
		// TODO Auto-generated method stub
		int eventType = event.getEventType();
		// add commentz
		switch (eventType) {
		        case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:  
		        //	event.get
		        	List<CharSequence> l = event.getText();
		        	for(CharSequence str:l){
		        		String content = str.toString();
		        		Log.i("MESSAGE_"+l.size(), content);
		        		
		        		if(event.getParcelableData()!=null&&event.getParcelableData() instanceof Notification){
		        			Log.i("FIND_DATA", "find rootnode");
		        			Notification notification = (Notification) event.getParcelableData();
		        			PendingIntent pendingIntent = notification.contentIntent;
		        			try {
								pendingIntent.send();
								Log.i("SEND_OK","SUCCESS to send pendingIntent	");
							} catch (CanceledException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		        			
		        		}
		        	}break;
		        case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:  
		        {
		        	Log.i("STATE", "WINDOW STATE CHANGED");
		        	  AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();  
		        	         if (nodeInfo != null) {  
		        	        	 
		        	              List<AccessibilityNodeInfo> list = nodeInfo  
		        	                      .findAccessibilityNodeInfosByText("点击拆开");
		        	              
		        	              for (AccessibilityNodeInfo n : list) {
		        	            	  if(n.getParent().getContentDescription()!=null){
		        	            		  	AccessibilityNodeInfo redPacket = n.getParent();
		        	            		  	redPacket.performAction(AccessibilityNodeInfo.ACTION_CLICK);
		        	            		  	 Log.i("Current Node Class ", n.getClassName().toString());
				        	            	  Log.i("Current Node Father C",redPacket.getContentDescription().toString()	);
				        	            	  Log.i("CLICKABLE", redPacket.isClickable()+"   "+"\t"+redPacket.getParent().getClassName());
				        	            	  Log.i("FIND_CLICK", "Click");
				        	            	  Intent mHomeIntent = new Intent(Intent.ACTION_MAIN); 
			        	                         mHomeIntent.addCategory(Intent.CATEGORY_HOME); 
			        	                         mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK 
			        	                                        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED); 
			        	                        getApplicationContext().startActivity(mHomeIntent); 
		        	            	  }else{
		        	            		  Log.e("ERROR", "Nopacket");
		        	            		  Intent mHomeIntent = new Intent(Intent.ACTION_MAIN); 
		        	                         mHomeIntent.addCategory(Intent.CATEGORY_HOME); 
		        	                         mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK 
		        	                                        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED); 
		        	                        getApplicationContext().startActivity(mHomeIntent); 
		        	                         

		        	            	  }
		        	             }  
		        	              if(list.size()<=0){
		        	            	  Log.e("ERROR", "No packet");
	        	            		  Intent mHomeIntent = new Intent(Intent.ACTION_MAIN); 
	        	                         mHomeIntent.addCategory(Intent.CATEGORY_HOME); 
	        	                         mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK 
	        	                                        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED); 
	        	                         getApplicationContext().startActivity(mHomeIntent); 
		        	              }
		        }else{
		        	Log.e("NULL NODE", "No Packet");
		        }
		        	          

		        }break;
		}

	}

	@Override
	public void onInterrupt() {
		// TODO Auto-generated method stub
		
	}



}
