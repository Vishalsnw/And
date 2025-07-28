
# Add project specific ProGuard rules here.

# Keep Firebase classes
-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }

# Keep Hilt classes
-keep class dagger.hilt.** { *; }
-keep class * extends dagger.hilt.android.HiltAndroidApp
-keepclasseswithmembers class * {
    @dagger.hilt.android.AndroidEntryPoint <methods>;
}

# Keep data models
-keep class com.example.goalguru.data.model.** { *; }

# Keep Compose
-keep class androidx.compose.** { *; }

# Keep Retrofit
-keep class retrofit2.** { *; }
-keep class com.google.gson.** { *; }

# Keep Room
-keep class androidx.room.** { *; }

# Keep WorkManager
-keep class androidx.work.** { *; }

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
