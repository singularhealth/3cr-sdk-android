# @3cr/sdk-android

[After a complete example?](https://github.com/singularhealth/3cr-sdk-android-example)

### Current Version: `1.2.0`
### Prerequisites

- Gradle Groovy or Kotlin DSL for Gradle
- Android Studio

### Project Setup

1. #### Add Package Manager
   We host our releases of the 3Dicom Core Renderer on GitHub and use Jitpack to build the dependencies.

   <CodeGroup>
      <CodeGroupItem title="Kotlin DSL" active>

      ```kts
      // settings.gradle.kts
   
      dependencyResolutionManagement {
          repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
          repositories {
              mavenCentral()
              maven("https://jitpack.io")
          }
      }
      ```

      </CodeGroupItem>

      <CodeGroupItem title="Groovy Gradle">

      ```gradle
      // settings.gradle
   
      dependencyResolutionManagement {
          repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
          repositories {
                  mavenCentral()
                  maven { url 'https://jitpack.io' }
          }
      }
      ```

      </CodeGroupItem>
   </CodeGroup>

2. #### Install Dependency
   To make the Viewer visible to your Application, you will need to add the dependency to your module level gradle file.

   <CodeGroup>
      <CodeGroupItem title="Kotlin DSL (build.gradle.kts)" active>

      ```kts
      // build.gradle.kts
    
      dependencies {
          implementation("com.github.singularhealth:3cr-sdk-android:1.2.0")
      }
    
      ```

      </CodeGroupItem>

      <CodeGroupItem title="Groovy Gradle (build.gradle)">

      ```gradle
      // build.gradle
    
      dependencies {
         implementation 'com.github.singularhealth:3cr-sdk-android:1.2.0'
      }
    
      ```

      </CodeGroupItem>
   </CodeGroup>

3. #### Setup 3CR Viewer Activity
   Create a new Java or Kotlin class that extends `ViewerSdkActivity`. This class will serve as the custom activity where you can implement your own UI and interact with the ViewerSdk.
   When you are ready to add your components to the FrameView, you can call addView from within your activity.

    <CodeGroup>
        <CodeGroupItem title="Kotlin" active>

      ```kotlin
    package com.my.custom.viewer
   
    import android.app.ActivityManager
    import android.content.Context
    import android.os.Bundle
    import android.widget.Button
    import health.singular.viewer3cr.android.sdk.FrontEndInterfaces
    import health.singular.viewer3cr.android.sdk.FrontEndPayload
    import health.singular.viewer3cr.android.sdk.ViewerSdkActivity
    
    const val sampleScanPayload = "{" +
        "\"Version\":\"0.0.1\"," +
        "\"Action\":\"fm_01\"," +
        "\"Interface\":\"file_management\"," +
        "\"Message\":\"{" +
            "\\\"Url\\\":\\\"https://webgl-3dr.singular.health/test_scans/01440d4e-8b04-4b90-bb2c-698535ce16d6/CHEST.3vxl\\\"," +
            "\\\"DecryptionKey\\\":{" +
                "\\\"Iv\\\":\\\"XEloSh+OcO7TG77au6HjPw==\\\"," +
                "\\\"Key\\\":\\\"KUc722X1y4w42M+jCf9a3+6EGz66z7UMWK3m2aMqGxM=\\\"" +
            "}" +
        "}\"," +
        "\"ReturnChannel\":null" +
    "}"
    
    class MyNewActivityWithThe3crViewer : ViewerSdkActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
    
            printOpenGLVersion()
    
            val showMainButton = Button(this).apply {
                text = "LOAD SCAN"
                x = 10f
                y = 500f
    
                setOnClickListener {
                    loadSampleScan()
                }
            }
            addView(showMainButton)
        }
    
        private fun loadSampleScan() {
            // Call this function whenever you would like 3CR to perform an action
            // You will need to supply different arguments based on the documentation
            executePayload(
                FrontEndPayload(
                    FrontEndInterfaces.FILE_MANAGEMENT,
                    "fm_01",
                    sampleScanPayload,
                    "1.0.0",
                )
            )
        }
    
        // This function will be called every time 3CR emits a message to the Front End.
        // override it so you can process the results from 3CR.
        override fun onPayload(jsonPayload: FrontEndPayload?) {
            super.onPayload(jsonPayload)
    
            println(jsonPayload)
   
            // ... Do something with payload.
        }
    
        private fun printOpenGLVersion() {
            val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager;
            val configurationInfo = activityManager.deviceConfigurationInfo
            println("Device Supported OpenGL ES Version = " + configurationInfo.getGlEsVersion())
        }
    }



   ```

   </CodeGroupItem>

   <CodeGroupItem title="Java">

    ```java
    package com.my.custom.viewer;
    
    import android.app.ActivityManager;
    import android.content.Context;
    import android.os.Bundle;
    import androidx.annotation.Nullable;
    import android.widget.Button;
    import health.singular.viewer3cr.android.sdk.FrontEndInterfaces;
    import health.singular.viewer3cr.android.sdk.FrontEndPayload;
    import health.singular.viewer3cr.android.sdk.ViewerSdkActivity;
    
    public class MyNewActivityWithThe3crViewer extends ViewerSdkActivity {

        private static final String SAMPLE_SCAN_PAYLOAD = "{"
            +     "\"Version\":\"0.0.1\","
            +     "\"Action\":\"fm_01\","
            +     "\"Interface\":\"file_management\","
            +     "\"Message\":\"{"
            +         "\\\"Url\\\":\\\"https://webgl-3dr.singular.health/test_scans/01440d4e-8b04-4b90-bb2c-698535ce16d6/CHEST.3vxl\\\","
            +         "\\\"DecryptionKey\\\":{"
            +             "\\\"Iv\\\":\\\"XEloSh+OcO7TG77au6HjPw==\\\","
            +             "\\\"Key\\\":\\\"KUc722X1y4w42M+jCf9a3+6EGz66z7UMWK3m2aMqGxM=\\\""
            +         "}"
            +     "}\","
            +     "\"ReturnChannel\":null"
            + "}";
    
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
       
            printOpenGLVersion();
       
            Button showMainButton = new Button(this);
            showMainButton.setText("LOAD SCAN");
            showMainButton.setX(10f);
            showMainButton.setY(500f);
    
            showMainButton.setOnClickListener(view -> loadSampleScan());
   
            addView(showMainButton);
        }
    
        private void loadSampleScan() {
            executePayload(
                new FrontEndPayload(
                    FrontEndInterfaces.FILE_MANAGEMENT,
                    "fm_01",
                    SAMPLE_SCAN_PAYLOAD,
                    "1.0.0"
                )
            );
        }
    
        @Override
        public void onPayload(FrontEndPayload jsonPayload) {
            super.onPayload(jsonPayload);
       
            System.out.println(jsonPayload);
            // ... Do something with payload.
        }
    
        private void printOpenGLVersion() {
            ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            ActivityManager.DeviceConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
            System.out.println("Device Supported OpenGL ES Version = " + configurationInfo.getGlEsVersion());
        }
    }
    ```

   </CodeGroupItem>
   </CodeGroup>

4. #### Execute payload on Instance
   **Inside your Activity** you are able to call the `executePayload` function.


   <CodeGroup>
        <CodeGroupItem title="Kotlin" active>

   ```kotlin
   // ...
   
   val someJsonStringPayload = "" // Fill this string in with a JSON representation of your payload.
   
   executePayload(
       FrontEndPayload(
           FrontEndInterfaces.FILE_MANAGEMENT,
           "fm_01",
           someJsonStringPayload,
           "1.0.0"
       )
   )
   
   // ...
   ```

   </CodeGroupItem>

   <CodeGroupItem title="Java">

   ```java
   // ...

    val someJsonStringPayload = ""; // Fill this string in with a JSON representation of your payload.
    
    executePayload(
        new FrontEndPayload(
            FrontEndInterfaces.FILE_MANAGEMENT,
            "fm_01",
            someJsonStringPayload,
            "1.0.0"
        )
    );
    
    // ...
   ```

     </CodeGroupItem>

   </CodeGroup>
