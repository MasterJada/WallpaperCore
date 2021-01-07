# WallpaperCore
[![](https://jitpack.io/v/MasterJada/android-example.svg)](https://jitpack.io/#MasterJada/android-example)


Simple library to create Wallpaper.

In library there are SuperWallpaperService it's high level of works and it's must declared in manifest with spetial intent filters. 
On second layer are the SuperEngine it's holds Scene and GameThread, it's must be inner class of SuperWallpaperService and must create Game thread and Scene.
On the third level we have Scene, and Scene it's level when we work with layers. In Scene we have onDraw method it's called every time when elapsed time is over  (We can setup it when create GameThread).

 ### Usage
 #### Setup
 In top level gradle file
 
 ``` gradle
 allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
      credentials { username authToken }
		}
	}
 ```
 In project level build.gradle
 
 ```gradle
 dependencies {
	        implementation 'com.github.MasterJada:android-example:${latest_version}'
	}
 ```
#### Implementation
##### Service
First of all need to implement service extended from [SuperWallPaperService](https://github.com/MasterJada/WallpaperCore/blob/master/wallpapercore/src/main/java/dev/jetlaunch/wallpapercore/SuperWallPaperService.kt) and create **inner class** extended from [SuperEngine](https://github.com/MasterJada/WallpaperCore/blob/412a572e0a58db0acebe41eb248a79c100768704/wallpapercore/src/main/java/dev/jetlaunch/wallpapercore/SuperWallPaperService.kt#L9). Inside _IEngine_ ned override 2 methods:
_provideGameThread()_ used to provide Game trhread! In this thread will happend all magic.
```kotlin
override fun provideGameThread(): GameThread {
            return createGameThread(this)
        }
```

_provideScene()_ used to provide *Scene*, Scene is main object which contains *layers* 

```kotlin
 override fun provideScene(): SuperScene {
            return SampleScene()
        }
```

In manifest 
```xml
     <service
            android:name="${serviceName}"
            android:enabled="true"
            android:exported="true"
            android:permission="android.permission.BIND_WALLPAPER">
            <meta-data
                android:name="android.service.wallpaper"
                android:resource="@xml/${configName}" />
            <intent-filter>
                <action android:name="android.service.wallpaper.WallpaperService" />
            </intent-filter>
        </service>
```

In xml need create new xml config file:
```xml
<?xml version="1.0" encoding="utf-8"?>
<wallpaper xmlns:android="http://schemas.android.com/apk/res/android"
    android:thumbnail="@drawable/${your_image}"
    android:description="@string/${your_description}"
    />
```
##### Scene 
Scene must be extended from (SuperScene)[../blob/master/wallpapercore/src/main/java/dev/jetlaunch/wallpapercore/SuperScene.kt]. It's simple holder for layers and need to update each of them and keep in right order:
```kotlin
class SampleScene: SuperScene() {
    override fun setupLayers() {
        layers.add(BackgroundLayer())
    }
}
```

#### Layer
Layer it's smallest part of library and used to draw something and to catch user interaption. Layer must be extendet from ILayer and override 3 methods:

```kotlin
class BackgroundLayer: ILayer {
    // in this method you can draw 
    override fun draw(canvas: Canvas) {
        canvas.drawColor(Color.BLACK)
    }
  // when size changed and width && height will be > 0 it's called
    override fun init(width: Int, height: Int) {

    }
    //to catch user interaption use
    override fun onTouch(motionEvent: MotionEvent?){
        
    }
}
```
