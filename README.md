# 滴滴开放平台接入文档V1.1.0

##总览
此文档介绍了如何使用`Android Studio`、`Eclipse`引入滴滴开放平台，并使用滴滴开放平台的相关服务。

##如何引入`aar`工程

###使用android studio
####引入
点击`File`->`New`->`New Module`->`import .jar/aar package`，然后选择滴滴提供的aar包点击`Finish`即可。此时滴滴开放平台以Module的形式，需要手动配置Module的依赖关系（在使用滴滴服务Module的依赖中添加滴滴开放平台的Module）.
####配置DiDiWebActivity
滴滴开放平台的服务由DiDiWebActivity提供，一般情况下并不需要关心Mainfest文件中的声明。以下为Sdk中的声明：

```
		<activity
      		android:name=".DiDiWebActivity"
      		android:label="@string/title_activity_di_di_web" />
```
如果您需要指定其他配置可以直接在您的Mainfest文件中添加一个Activity声明。如下：

```
        <activity
            android:name="com.sdu.didi.openapi.DiDiWebActivity"
            android:launchMode="singleTop"
            android:screenOrientation="landscape"/>
```
声明了Activity的`android:launchMode`和`android:screenOrientation`，Android Studio会自动合并Mainfest文件。
####如何更新aar包
在资源管理器中找到滴滴Module所在的文件夹，会看到旧版本的aar文件，直接使用新arr文件覆盖，然后点击工具栏中的`Sync Project With Gradle File`。

###使用eclipse
####引入
使用任意解压缩软件解压缩aar文件得到:

 *  `classes.jar`，sdk的jar包

 将`classes.jar`文件改名为DiDiSdk.jar放入工程的libs文件夹
 
 *  `jni` ，需要放到libs的动态库文件

 将jni里面的`libdidi_secure.so`放到。libs/armabi下
 *  `res`，资源文件

 将res中的文件放入对应文件夹
 *  `AndroidManifest.xml`清单文件

 将清单文件中的所有项复制到工程现有的清单文件中，并去重。
 
####配置DiDiWebActivity
同Android Studio
####如何更新aar包
同引入

##使用滴滴服务

**注意**
权限：由于Android 6.0系统以上引入了新的权限管理模型，所以如果你的应用`targetSdkVersion`>=23,在调用下面3.中所描述的openiapi时需要注意是否已经获取对了对应的权限，不然会发生crash,需要申请的权限有：

```
Manifest.permission.ACCESS_FINE_LOCATION
Manifest.permission.READ_PHONE_STATE
Manifest.permission.WRITE_EXTERNAL_STORAGE
```

定位：由于滴滴的服务依赖于定位，如果您没有主动传入定位信息则滴滴开放平台会自动定位，由于系统定位精度差定位速度慢，推荐您接入了第三方定位SDK.

在传入已有坐标时对应的`maptype`参数如下表：

定位厂商 | 地图类型 |
----|------|---- |
腾讯soso | soso  |
高德 | soso  |
百度 | bd09ll = baidu,默认坐标系为soso  |
系统默认|wgs84|

需要传参数的的Map的key，value都为String,返回值都为Map的key，value都为String,在返回的Map中包含`errorno`和`errormsg`，其中`errorno`为判断返回是否成功的标志，返回0为成功，具体返回值的含义需要根据服务器接口对应文档获取。

1.在入口Activity中调用

```
DIOpenSDK.registerApp(context,"appid","secret")
其中appid和secret需要向开放平台申请
DIOpenSDK.setMapSdkType(DIOpenSDK.MapLocationType.*)(可选)
```
_*应在调用其他滴滴方法之前调用*_


2.直接调用打车业务

```
DIOpenSDK.showDDPage(Context,HashMap<String,String>);
```
Context参数尽量传Activity
其中HashMap为需要传给滴滴开放平台的参数

名称 | 类型 | 说明| 
----|------|---- |
fromlat | string  | 出发地纬度 |
fromlng | string  | 出发地经度	|
tolat | string  | 目的地纬度	|
tolng | string  | 目的地经度	|
biz | string  | 默认选中的业务线类型。1打车，2专车|
fromaddr | string  | 出发地地址|
fromname | string  | 出发地名称|
toaddr | string  | 目的地地址	|
toname | string  | 目的地名称	|
phone | string  | 乘客手机号，方便乘客登录使用，会默认补全到登录框中|
maptype | string  | 经纬度类型 wgs84/baidu/soso|

3.使用滴滴openapi直接调用滴滴接口

在申请appid和secret时，可以同时申请通过滴滴开放平台sdk直接调用滴滴服务器相应接口（需要经过商务协商）或者直接通过服务器和开放平台服务器直接对接。

>*服务器和服务器对接：*
>
>通过在主线程中调用`DIOpenSDK.asynGetTicket(Context context, TicketType typeContext context, TicketType type, DDCallBack callBack)`或者在分线程中调用`DIOpenSDK.syncGetTicket()`获取ticket，回传到第三方服务器用来服务器和服务器对接。
>
>
>*直接调用滴滴服务器API*
>
>通过在分线程中调用`DIOpenSDK.syncCallDDApi(Context context, String apiname, Map<String, String> params)`或者在主线程中调用`DIOpenSDK.asynCallDDApi(Context context, String apiname, Map<String, String> requestParams, DDCallBack callBack)`
>即可调用滴滴服务器api具体可以调用的API列表需要跟商务确认。
>
>*直接调用滴滴功能页面*
>
>通过直接在主线程中调用`DIOpenSDK.openPage(Context context,String page, Map<String, String> extra)`即可打开滴滴提供的指定的功能性页面，比如登录、订单列表等，如果调用成功返回true调用失败返回false。
>
>*调用滴滴司机电话*
>
>当您调用滴滴相应功能获取到司机的加密手机号后需要使用`DIOpenSDK.callPhone(Context context, String phone)`来拨打司机电话。
		

##混淆
请在混淆的配置文件中增加以下配置

```
-keepnames class com.sdu.didi.openapi.utils.Utils{}
-keep public class com.sdu.didi.openapi.Methods{}
-keepnames class com.sdu.didi.openapi.location.*{*;}

-keepclassmembers class com.sdu.didi.openapi.Methods{
   public java.lang.String *(java.lang.String);
}

-keepclassmembers class com.sdu.didi.openapi.utils.Utils{
   public static java.lang.String getTimestamp();
   public static java.lang.String getRandomString(int);
}

-keep public class com.sdu.didi.uuid.ed {*;}
-keep public class com.sdu.didi.uuid.SigLib {*;}

```

##ReleaseNote
滴滴开放平台接入文档V1.1.0


 
	

