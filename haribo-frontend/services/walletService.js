var walletService = {
    findAddressById: function (id, callback) {
        $.get(API_BASE_URL + "/api/wallets/of/" + id, function (data) {
            callback(data['주소']);
        });
    },
    findById: function (id, callback) {
        // TODO 지갑 조회 API를 호출합니다. 
        this.findAddressById(id, (address) => {

            $.ajax({
                type : "GET",
                url : API_BASE_URL + "/api/wallets/" + address,
                success : function(data, response, response2){
                    callback(response2);
                }
            });
        })

    },
    isValidPrivateKey: function (id, privateKey, callback) {
        var web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL));
        var account = web3.eth.accounts.privateKeyToAccount(privateKey);

        this.findById(id, function (data) {
            console.log("data : " , data);
            var address = data.responseJSON['주소'];
            console.log("account : ", account);
            console.log("address : ", address);
            callback(account && account.address == address, address);
        });
    },
    registerWallet: function (userId, walletAddress, callback) {

        var body = {
            "소유자id": userId,
            "주소": walletAddress
        }

        // TODO 지갑 등록 API를 호출합니다.
        $.ajax({
            type: "POST",
            url: API_BASE_URL + "/api/wallets",
            data: JSON.stringify(body),
            headers: { 'Content-Type': 'application/json' },
            success: function (response) {
                callback(response);
            }
        });
    },
    chargeEther: function (walletAddress, callback) {
        // TODO 코인 충전 API를 호출합니다.
        var body = {
            "address" : walletAddress
        }

        $.ajax({
            type: "PUT",
            url: API_BASE_URL + "/api/wallets/" + walletAddress,
            success: function (response) {
                callback(response);
            }
        });
    }
}
