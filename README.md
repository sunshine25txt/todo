# TOD — Android to-do app

Jetpack Compose Android app matching the splash, registration, login, and dashboard screens.

## Screens

1. **Splash** — Get Started
2. **Register** — name, email, password, confirm password
3. **Login** — email, password, forgot password
4. **Dashboard** — greeting, live analog clock, daily task list with add / complete

Accounts and tasks are stored on the device (`SharedPreferences`). No internet required.

## Open in Android Studio

This machine did not have the Android SDK installed, so the APK was not compiled here.

1. Install [Android Studio](https://developer.android.com/studio)
2. **File → Open** → this `tod-android` folder
3. Let Gradle sync, then run on an emulator or phone (API 26+)

Package id: `com.todapp.tod`

## Try the UI now

Open `preview/index.html` in a browser, or from this folder:

```powershell
python -m http.server 8080 --directory preview
```

Then visit http://localhost:8080
