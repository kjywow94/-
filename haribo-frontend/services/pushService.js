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

var pushService = {
    storeToken : function(){
        Notification.requestPermission().then(permission => {
            if (permission === 'granted') {
                messaging.getToken().then(response=> {
                    var data = {
                        "id": store.state.user.id,
                        "token": response
                    }
                    $.ajax({
                        type: "POST",
                        url: API_BASE_URL + "/api/storeToken",
                        data: JSON.stringify(data),
                        headers: { 'Content-Type': 'application/json' },
                        success: function (res) {
                        }
                    });
                }).catch(function(err){
                    console.log("permission err", err);
                })
            } else {
            }
        })
    }
}