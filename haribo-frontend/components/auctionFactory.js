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
 * AuctionFactory의 createAuction 함수를 호출하여 경매를 생성합니다.
 * 경매 생성 시, (작품id, 최소입찰가, 경매시작시간, 경매종료시간)을 반드시 지정해야합니다. 
 *  */
function createAuction(options, walletAddress, privateKey, onConfirm) {

    var web3 = createWeb3();
    var contract = createFactoryContract(web3);
    var minValue = web3.utils.toWei(options.minValue, "ether");
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

            console.log("경매 생성 recp");
            console.log(response);

            contract.methods.allAuctions().call().then(response => {
                var responseAddress = response[response.length - 1];
                onConfirm(responseAddress);
            });
        });
    });
}

/**
 * 해당 컨트랙트 주소의 bid함수를 호출하여 입찰합니다.
 * 경매 컨트랙트 주소: options.contractAddress
 *  */
function auction_bid(options, onConfirm) {
    var web3 = createWeb3();
    var contract = createAuctionContract(web3, options.contractAddress);
    var bidCall = contract.methods.bid();
    var encodedABI = bidCall.encodeABI();

    var tx = {
        from: options.walletAddress,
        to: options.contractAddress,
        value: web3.utils.toWei(options.amount, "ether"),
        gas: 3000000,
        data: encodedABI
    }

    web3.eth.accounts.signTransaction(tx, options.privateKey).then(response => {
        web3.eth.sendSignedTransaction(response.rawTransaction).then(response => {

            console.log("경매 입찰 recp");
            console.log(response);

            onConfirm(response);
        });
    });
}

/**
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

            console.log("경매 종료 recp");
            console.log(response);

            onConfirm(response);
        });
    });
}

/**
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

    web3.eth.accounts.signTransaction(tx, options.privateKey).then(response => {
        web3.eth.sendSignedTransaction(response.rawTransaction).then(response => {

            console.log("경매 취소 recp");
            console.log(response);

            onConfirm(response);
        });
    });
}