// Web3 Object 생성
function createWeb3() {
    var web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL));
    return web3;
}

// AuctionFactory 컨트랙트 Object 생성
function createFactoryContract(web3) {
    var auctionContract = new web3.eth.Contract(AUCTION_FACTORY_CONTRACT_ABI, AUCTION_CONTRACT_ADDRESS);
    return auctionContract;
}

// Auction Object 컨트랙트 생성
function createAuctionContract(web3, contractAddress) {
    var auctionContract = new web3.eth.Contract(AUCTION_CONTRACT_ABI, contractAddress);
    return auctionContract;
}

/**
 * TODO [경매 생성] 
 * AuctionFactory의 createAuction 함수를 호출하여 경매를 생성합니다.
 * 경매 생성 시, (작품id, 최소입찰가, 경매시작시간, 경매종료시간)을 반드시 지정해야합니다. 
 *  */
function createAuction(options, walletAddress, privateKey, onConfirm) {
    console.log("시간 확인 필요");
    console.log(options);

    var web3 = createWeb3();
    var contract = createFactoryContract(web3);
    // contract.methods.createAuction(options.workId, options.minValue, options.startTime, options.endTime).encodeABI();
    var minValue = web3.utils.toWei(options.minValue, "ether");
    console.log("minvalue :  ", minValue);
    var createAuctionCall = contract.methods.createAuction(options.workId, minValue, options.startTime, options.endTime); // 함수 호출 Object 초기화
    var encodedABI = createAuctionCall.encodeABI();

    // 트랜잭션 생성
    var tx = {
        from: walletAddress,
        to: AUCTION_CONTRACT_ADDRESS,
        gas: 3000000,
        data: encodedABI
    }
    /**
     * 트랜잭션 전자 서명 후 트랜잭션 전송/처리
     */
    web3.eth.accounts.signTransaction(tx, privateKey).then(response => {
        web3.eth.sendSignedTransaction(response.rawTransaction).then(response => {
            contract.methods.allAuctions().call().then(response => {
                var responseAddress = response[response.length - 1];
                console.log(responseAddress);
                // auction_listener_getBalance(responseAddress);
                onConfirm(responseAddress);
            });
        });
    });
}

/**
 * TODO [입찰] 
 * 해당 컨트랙트 주소의 bid함수를 호출하여 입찰합니다.
 * 경매 컨트랙트 주소: options.contractAddress
 *  */
function auction_bid(options, onConfirm) {
    console.log("options : ", options);
    var web3 = createWeb3();
    var contract = createAuctionContract(web3, options.contractAddress);
    var bidCall = contract.methods.bid();
    var encodedABI = bidCall.encodeABI();

    // var options = {
    //     amount: this.input.price,
    //     contractAddress: this.auction['경매컨트랙트주소'],
    //     walletAddress: this.wallet['주소'],
    //     privateKey: this.input.privateKey
    // };
    var tx = {
        from: options.walletAddress,
        to: options.contractAddress,
        value: web3.utils.toWei(options.amount, "ether"),
        gas: 3000000,
        data: encodedABI
    }

    web3.eth.accounts.signTransaction(tx, options.privateKey).then(response => {
        web3.eth.sendSignedTransaction(response.rawTransaction).then(response => {
            console.log("transaction response");
            console.log(response);
            onConfirm(response);
        });
    });
}

function auction_withdraw(options) {
    var web3 = createWeb3();
    var contract = createAuctionContract(web3, options.contractAddress);
    var withdrawCall = contract.methods.withdraw();
    var encodedABI = withdrawCall.encodeABI();

    var tx = {
        from: walletAddress,
        to: AUCTION_CONTRACT_ADDRESS,
        gas: 3000000,
        data: encodedABI
    }

}

/**
 * TODO [경매 종료] 
 * 해당 컨트랙트 주소의 endAuction함수를 호출하여 경매를 종료합니다.
 * 경매 컨트랙트 주소: options.contractAddress
 *  */
function auction_close(options, onConfirm) {
    var contract = createAuctionContract(web3, options.contractAddress);
    var closeAuctionCall = contract.methods.endAuction();
    var encodedABI = closeAuctionCall.encodeABI();

    var tx = {
        from: options.walletAddress,
        to: options.contractAddress,
        gas: 3000000,
        data: encodedABI
    }
    
    web3.eth.accounts.signTransaction(tx, options.privateKey).then(response => {
        web3.eth.sendSignedTransaction(response.rawTransaction).then(response => {
            onConfirm(response);
        });
    });
}

/**
 * TODO [경매 취소] 
 * 해당 컨트랙트 주소의 cancelAuction함수를 호출하여 경매를 종료합니다.
 * 경매 컨트랙트 주소: options.contractAddress
 *  */
function auction_cancel(options, onConfirm) {
    var web3 = createWeb3();
    var contract = createAuctionContract(web3, options.contractAddress);
    var cancelAuctionCall = contract.methods.cancelAuction();
    var encodedABI = cancelAuctionCall.encodeABI();

    var tx = {
        from: options.walletAddress,
        to: options.contractAddress,
        gas: 3000000,
        data: encodedABI
    }
    console.log("tx : " , tx);

    web3.eth.accounts.signTransaction(tx, options.privateKey).then(response => {
        web3.eth.sendSignedTransaction(response.rawTransaction).then(response => {
            onConfirm(response);
        });
    });
}

function auction_listener_getBalance(contractAddress) {
    var web3 = createWeb3();
    var contract = createAuctionContract(web3, contractAddress);
    var listener = contract.getBalance();
    listener.watch(err, response => {
        console.log("err : ", err);
        console.log("response : ", response);
        //abi 변경해야됨
    });
}