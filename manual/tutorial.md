# 个人项目2
# 中山大学智慧健康服务平台应用开发

---  

---
## 第七周任务
## Broadcast 使用
---
### 静态广播部分 

#### 1. 使用随机数
```java
    Random random = new Random();
    random nextInt(n); //返回一个0到n-1的整数
```

#### 2. 利用bundle 和intent 将图片与文字内容发送出去
```java
    Intent intentBroadcast = new Intent(STATICACTION); //定义Intent
    intentBroadcast.putExtras(bundles);
    sendBroadcast(intentBroadcast);
```

#### 3. 参考代码中的STATICATION 为自己设定的广播名称。由于是静态注册所以需要在AndroidMainfest.xml 中进行注册。
```java
   <receiver android:name=".Receiver.Receiver">
            
         <intent-filter>
                
               <action android:name="com.example.ex4.MyStaticFliter" />
            
         </intent-filter>
        
   </receiver>
```

#### 4. 在静态广播类StaticReceiver 中重写onReceive 方法，当接收到对应广播时进行数据处理，产生通知。
```java
   public void onReceive(Context context, Intent intent) {
        
          if (intent getAction().equals(STATICACTION)) {     //动作检测
            
              Bundle bundle = intent.getExtras();
            
              //TODO:添加Notification部分
        
          }
    
   }
```
---
### 动态广播部分 

#### 1. 实现 BroadcastReceiver 子类（这里命名为DynamicReceiver），并且重写onReceive 方法，修改方法与静态广播类中类似。
```java
   public class DynamicReceiver extends BroadcastReceiver{
        
          private static final String DYNAMICACTION = "com.example.ex4.MyDynamicFliter";    //动态广播的Action字符串

        

          public void onReceive(Context context, Intent intent) {
            
                 if (intent getAction().equals(DYNAMICACTION)) {     //动作检测
                
                     Bundle bundle = intent.getExtras();
                
                     //TODO:添加Notification部分
            
                 }
        
           }
    
    }
```

####2. 注册广播关键代码
```java
   IntentFilter dynamic_filter = new IntentFilter();
    
   dynamic_filter.addAction(DYNAMICACTION);     //添加动态广播的Action
    
   registerReceiver(dynamicReceiver, dynamic_filter);  //注册自定义动态广播消息
```

#### 注销广播关键代码
```java
   unregisterReceiver(dynamicReceiver);
```
其中dynamicReceiver 为我们之前创建的DynamicReceiver 类。用registerReceiver与unregisterReceiver 分别对其进行注册与注销。

####3. 发送方法与静态注册时一直，仅需修改广播名称即可。（使用sendBroadcast(intent)）

####4. 注意在 Android 主界面中将 launchMode 设置为 singleInstance，使得点击Notification 后不会另外新建一个收藏列表。
```java
   <activity android:name=".Activity.CollectionList"
            
          android:launchMode="singleInstance" >
            
          <intent-filter>
                
                <action android:name="android.intent.action.MAIN" />
                
                <category android:name="android.intent.category.LAUNCHER" />
            
          </intent-filter>
        
     </activity>
```
---
###Notification 的使用
Notification 可以提供持久的通知，位于手机最上层的状态通知栏中。用手指按下状态栏，并从手机上方向下滑动，就可以打开状态栏查看提示消息。开发Notification 主要涉及以下3个类：

#### 1. Notification.Builder：用于动态的设置Notification 的一些属性
```java
   Notification.Builder builder = new Notification.Builder(context);
    
   //对Builder进行配置，此处仅选取了几个
    
   builder.setContentTitle("动态广播")   //设置通知栏标题：发件人
            
              .setContentText(bundle.getString("name"))   //设置通知栏显示内容：短信内容
            
              .setTicker("您有一条新消息")   //通知首次出现在通知栏，带上升动画效果的

              .setLargeIcon(bm)   //设置通知大ICON

              .setSmallIcon(R.mipmap.dynamic)   //设置通知小ICON（通知栏）
  
            .setAutoCancle(true);   //设置这个标志当用户单击面板就可以让通知将自动取消
```
思考：大ICON 如何设置？bm 是什么？

####2. NotificationManager：负责将Notification 在状态显示出来和取消
```java
   //获取状态通知栏管理
    
   NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
```

####3. Notification：设置Notification 的相关属性
```java
   //绑定Notification，发送通知请求
    
   Notification notify = builder.build();

   manager.notify(0,notify);
```

####4. 点击notification，就可以跳转到我们intent 中指定的activity。主要使用到setContentIntent 与PendingIntent。

关于Notification，不同版本的API 显示可能会有所不同。

图片的使用方面请尽量使用mipmap 目录下的image asset。否则在某些API 中可能会出现Icon 过大的情况。

---
###Eventbus的使用  
Eventbus可以简化组件之间的沟通。  

####1. 添加依赖
```java
   compile 'org.greenrobot:eventbus:3.0.0'
```
####2. 声明一个事件类(传递食品信息)
```java
   public static class MessageEvent { /* Additional fields if needed */ }
```

####3. 准备订阅者
(1)声明并注释您的订阅方法，可选地指定线程模式(在收藏列表所在Activity声明这个方法)
```java
   @Subscribe(threadMode = ThreadMode.MAIN)  
   public void onMessageEvent(MessageEvent event) {/* Do something */};
```
(2)注册订阅者(注册收藏列表所在Activity为订阅者)
```java
   EventBus.getDefault().register(this); 
```
(3)注销订阅者(退出时要注销订阅者)
```java
   EventBus.getDefault().unregister(this); 
```

####4. 传递事件(点击收藏图标时候，传递食品信息)
```java
   EventBus.getDefault().post(new MessageEvent());
```


---

---



