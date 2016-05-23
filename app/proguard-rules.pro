# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/liyang/develop/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}


-keepnames class com.sdu.didi.openapi.utils.Utils{}
-keepnames class com.sdu.didi.openapi.location.*{*;}
-keep public class com.sdu.didi.openapi.Methods{}

-keepclassmembers class com.sdu.didi.openapi.Methods{
   public java.lang.String *(java.lang.String);
}

-keepclassmembers class com.sdu.didi.openapi.utils.Utils{
   public static java.lang.String getTimestamp();
   public static java.lang.String getRandomString(int);
}

-keep public enum com.sdu.didi.openapi.DIOpenSDK$** {
    **[] $VALUES;
    public *;
}

-keepclassmembers class com.sdu.didi.openapi.DiDiWebActivity{
   public *;
}

-keepclassmembers class com.sdu.didi.openapi.DIOpenSDK{
   public *;
}

-keep public interface com.sdu.didi.openapi.DIOpenSDK$DDCallBack {*;}

-keep public class com.sdu.didi.uuid.ed {*;}
-keep public class com.sdu.didi.uuid.SigLib {*;}
