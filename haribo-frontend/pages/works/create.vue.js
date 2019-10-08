var worksCreateView = Vue.component("worksCreateView", {
    template: `
        <div>
            <v-nav></v-nav>
            <v-breadcrumb title="작품 등록" description="새로운 작품을 등록합니다." titleImg="assets/images/register_title.jpg"></v-breadcrumb>
            <div class="container">
                <div class="row">
                    <div class="col-md-8 mx-auto">
                        <div class="card">
                            <div class="card-body">
                                <div class="form-group">
                                    <label id="name">작품 이름</label>
                                    <input type="text" class="form-control" id="name" v-model="work.name">
                                </div>
                                <div class="form-group">
                                    <label id="description">작품 설명</label>
                                    <textarea class="form-control" id="description" v-model="work.description"></textarea>
                                </div>
                                <div class="form-group">
                                
                                <div class="ml-2 col-sm-6">
                                <div id="msg"></div>
                                <form method="post" id="image-form">
                                  <input type="file" ref="inputRef" name="img[]" class="file" accept="image/*" @change="changeFile($event)">
                                  <div class="input-group my-3">
                                    <input type="text" class="form-control" disabled placeholder="Upload File" v-model="work.imgName">
                                    <div class="input-group-append">
                                      <button type="button" class="browse btn btn-primary" v-on:click="uploadImg()">Browse...</button>
                                    </div>
                                  </div>
                                </form>
                              </div>
                              <div class="ml-2 col-sm-6">
                                <img :src="work.img" id="preview" class="img-thumbnail">
                              </div>

                                </div>
                                <div class="form-group">
                                    <label id="isActive">공개여부(공개하려면 체크)</label><br>
                                    <input type="checkbox" id="isActive" v-model="work.isActive">
                                </div>
                                <button type="button" class="btn btn-primary" v-on:click="save">작품 등록하기</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `,
    data() {
        return {
            work: {
                name: "",
                description: "",
                isActive: true,
                status: true,
                imgName: "UploadName",
                img: "https://placehold.it/80x80"
            },
            sharedStates: store.state
        }
    },
    methods: {
        save: function () {
            var scope = this;

            workService.create({
                "이름": this.work.name,
                "설명": this.work.description,
                "공개여부": this.work.isActive ? "Y" : "N",
                "상태": this.work.status ? "Y" : "N",
                "회원id": this.sharedStates.user.id
            },
                function (response) {
                    workService.findWorksInfoByOwner(scope.sharedStates.user.id, (workList) => {
                        var workId = workList[workList.length - 1].id;

                        var filename = scope.work.imgName;
                        var fileLen = filename.length;
                        var lastDot = filename.lastIndexOf('.');
                        var extension = filename.substring(lastDot, fileLen).toLowerCase();

                        workService.uploadImage({
                            "imgName": workId,
                            "extension": extension,
                            "imgData": scope.work.img
                        }, function () {
                            alert('작품이 등록되었습니다.');
                            scope.$router.push('/artworks');

                        }, function (error) {
                            alert("작품 이미지 업로드 중 에러가 발생했습니다.");
                            console.log(error);
                        });
                    });
                },
                function (error) {
                    alert("입력폼을 모두 입력해주세요.");
                });
        },
        uploadImg() {
            this.$refs.inputRef.click();
        },
        changeFile(event) {
            this.work.imgName = event.target.files[0].name;
            var reader = new FileReader();
            reader.readAsDataURL(event.target.files[0]);
            
            reader.onload = e => {
                this.work.img = e.target.result;
            };
        }
    }
});
