import admin from 'firebase-admin';

// Initialize Firebase Admin using service account JSON provided via env var
// Option A: GOOGLE_APPLICATION_CREDENTIALS points to a JSON file path
// Option B: FIREBASE_SERVICE_ACCOUNT (base64) containing the JSON
export const initFirebaseAdmin = () => {
  if (admin.apps.length) return admin.app();
  try {
    if (process.env.FIREBASE_SERVICE_ACCOUNT) {
      const json = Buffer.from(process.env.FIREBASE_SERVICE_ACCOUNT, 'base64').toString('utf8');
      const creds = JSON.parse(json);
      return admin.initializeApp({ credential: admin.credential.cert(creds) });
    } else {
      return admin.initializeApp(); // uses GOOGLE_APPLICATION_CREDENTIALS file path
    }
  } catch (e) {
    console.error("Firebase Admin init failed:", e);
  }
};

export const sendToTopic = async (topic, title, body, data = {}) => {
  try {
    initFirebaseAdmin();
    const message = {
      notification: { title, body },
      data,
      topic
    };
    await admin.messaging().send(message);
    console.log('✅ FCM sent to topic:', topic);
  } catch (e) {
    console.error('FCM error:', e.message);
  }
};