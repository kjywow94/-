var ethereumService = {
    // 이더리움 블록, 트렌젝션 조회
    
    findblockAll: function(callback){
        $.get(API_BASE_URL + '/api/eth/blocks', function(data){           
            callback(data);
        });
    },
    
    findbyBlock: function(bid, callback){
        $.get(API_BASE_URL + '/api/eth/block/' + bid, function(data){
            callback(data);
        });  
    },
 
    findtransAll: function(callback){
        $.get(API_BASE_URL + '/api/eth/txes', function(data){           
            callback(data);
        });
    },

    findbyTrans: function(tid, callback){
        $.get(API_BASE_URL + '/api/eth/txes/' + tid, function(data){
            callback(data);
        });  
    },
    findByAddress: function(addr, callback){
        $.get(API_BASE_URL + '/api/eth/address/' + addr, function(data){
            callback(data);
        })
    },
    pushTransaction: function(receipt){
        $.ajax({
            type: "POST",
            url: API_BASE_URL + "/api/eth/transaction",
            data: JSON.stringify(receipt),
            headers: { 'Content-Type': 'application/json' },
            success: function(response){
                console.log("success");
                console.log(response);
            }
        });
    }
}