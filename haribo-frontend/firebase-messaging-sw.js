importScripts('https://www.gstatic.com/firebasejs/4.8.1/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/4.8.1/firebase-messaging.js');


var firebaseConfig = {
    apiKey: "AIzaSyBoxlnCri0wGBD35FIqWXQpptzTzIcKi-A",
    authDomain: "voracoin.firebaseapp.com",
    databaseURL: "https://voracoin.firebaseio.com",
    projectId: "voracoin",
    storageBucket: "voracoin.appspot.com",
    messagingSenderId: "863854023483"
};

firebase.initializeApp(firebaseConfig);

const messaging = firebase.messaging();

self.addEventListener("push", function (event) {
    const payload = event.data.json();
    const title = payload.notification.title;
    const options = {
        body: payload.notification.body,
        icon: payload.notification.icon,
        data: payload.notification.click_action
    };
    event.waitUntil(self.registration.showNotification(title, options));
});
