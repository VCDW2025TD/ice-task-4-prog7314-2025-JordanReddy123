# iceTaskTwo — Android + Auth + Biometric + Backend

This zip contains:
- **Android** app (Kotlin) with **Google SSO (Firebase Auth)** and **BiometricPrompt**.
- **Retrofit** client consuming your MemeStream REST API.
- **Bundled backend** from Task One under `MemeStream/01-RestfulAPI`.

## Setup (Android)
1. Open `Android/` in Android Studio (Giraffe+).
2. Add your **Firebase** config file:
   - Download `google-services.json` from Firebase Console and place it at: `Android/app/google-services.json`.
   - In Firebase, enable **Google** provider in Authentication.
   - Copy the **Web client ID** into `strings.xml` as `default_web_client_id` (create if missing).
3. Update API base URL in `ApiClient.kt` if deploying remotely.
4. Run the app: first login with Google, then you can use biometrics for quick re-entry.

## Setup (Backend)
- The Node/Express API is in `MemeStream/01-RestfulAPI`.
- `npm install`, set `.env`, `npm start`.
- From Android emulator, use `http://10.0.2.2:8080/` to access local backend.

## Notes
- Min SDK 23; uses `androidx.biometric`.
- Replace placeholders and add icons as needed.

## Offline Mode (RoomDB) + Sync (WorkManager)
- Caches memes locally in Room (`memes` table).
- If network fails, app shows cached memes.
- Pending uploads stored in `pending_memes`; a WorkManager (`SyncWorker`) syncs them when online.
- Use `Repository.enqueueUpload(MemePost(...))` to queue an upload from your UI (e.g., an Upload screen).



## Real-Time Notifications (FCM)
**Android**: Subscribes to topics `trending` and `region_za_durban` after login. Handles messages in `fcm/MemeMessagingService.kt`.

**Backend**: On `POST /memes`, server sends FCM notifications using Firebase Admin to:

- `trending` (global)

- `region_<yourRegion>` if you include `region` in the POST body (e.g., `region: "za_durban"`)



**Server setup**:

- Add a Firebase service account to the server via one of:

  - `GOOGLE_APPLICATION_CREDENTIALS=/path/to/serviceAccount.json`, or

  - `FIREBASE_SERVICE_ACCOUNT=<base64 of your JSON>`

- Install deps and run server.



**Test**:
- Android logs: subscribe to topics on login.
- POST a meme with body `{ "userId":"u1", "title":"Hi", "imageUrl":"https://...", "region":"za_durban" }`.
- Device should receive a push notification.
