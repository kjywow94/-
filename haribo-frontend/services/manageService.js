var manageService = {
    
    // 전체 경매 내역 조회
    findAll: function (callback) {
        $.get(API_BASE_URL + '/api/members', function (data) {
            callback(data);
        });
    },
    modifyAuth: function (data, callback) {
        $.ajax({
            type: "PUT",
            url: API_BASE_URL + "/api/member/auth",
            data: JSON.stringify(data),
            headers: { 'Content-Type': 'application/json' },
            success: callback
        });
    },
    DeleteUser: function (userId, callback) {
        $.ajax({
            type: "PUT",
            url: API_BASE_URL + "/api/members/" + userId,
            data: JSON.stringify(data),
            headers: { 'Content-Type': 'application/json' },
            success: callback
        });
    },
    // 경매 생성
    register: function (data, callback) {
        $.ajax({
            type: "POST",
            url: API_BASE_URL + "/api/auctions",
            data: JSON.stringify(data),
            headers: { 'Content-Type': 'application/json' },
            success: callback
        });
    },
    findById: function (id, callback) {
        $.get(API_BASE_URL + "/api/auctions/" + id, function (data) {
            callback(data);
        });
    },
    countBidById: function (id, callback) {
        $.get(API_BASE_URL + "/api/auctions/bid/" + id, function (data) {
            callback(data);
        });
    },
    // 경매 내역 저장
    saveBid: function (bidder, auctionId, bidPrice, callback) {
        var data = {
            "경매참여자id": bidder,
            "경매id": auctionId,
            "입찰금액": bidPrice,
            "입찰일시": new Date()
        }
        $.ajax({
            type: "PUT",
            url: API_BASE_URL + "/api/auctions/bid",
            data: JSON.stringify(data),
            headers: { 'Content-Type': 'application/json' },
            success: callback
        })
    },
    // 경매 취소
    cancel: function (auctionId, bidderId, callback, whenError) {
        if (!bidderId)
            bidderId = 0;
        $.ajax({
            type: "DELETE",
            url: API_BASE_URL + "/api/auctions/" + auctionId + "/by/" + bidderId,
            success: callback,
            error: whenError
        });
    },
    // 경매 종료
    close: function (auctionId, bidderId, callback, whenError) {
        $.ajax({
            type: "PUT",
            url: API_BASE_URL + "/api/auctions/" + auctionId + "/by/" + bidderId,
            success: callback,
            error: whenError
        });
    }
}
