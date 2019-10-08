var manageService = {
    
    // 전체 회원 조회
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

    //전제 작품 조회
    findAllArtwork: function(callback){
        $.get(API_BASE_URL + '/api/allWorks', function(data){
            callback(data);
        });
    },
    //작품 삭제
    deleteArtwork: function (id, success, whenError) {
        $.ajax({
            type: 'DELETE',
            url: API_BASE_URL + "/api/works/" + id,
            success: success,
            error: whenError
        });
    },
    //작품복원
    restoreArtwork: function (body, success, whenError) {
        $.ajax({
            type: 'PUT',
            url: API_BASE_URL + '/api/works',
            data: JSON.stringify(body),
            headers: { 'Content-Type': 'application/json' },
            success: success,
            error: whenError
        })
    }
}