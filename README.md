Android Realm Creator
===

Create initial data of Realm by Android.

## How to use

1. Install and execute app.

```
./gradlew :realmcreator:installDebug
adb shell am start -n hm.orz.chaos114.android.realmcreator/hm.orz.chaos114.android.realmcreator.MainActivity
```

2. Copy realm database to local.

```
adb shell run-as hm.orz.chaos114.android.realmcreator cat files/output.realm > default.realm
```
