var explorerBlockDetailView = Vue.component('ExplorerBlockDetailView', {
    template: `
    <div>
        <v-nav></v-nav>
        <v-breadcrumb title="Block Explorer" description="블록체인에서 생성된 블록내역을 보여줍니다." titleImg="assets/images/explorer_title.jpg"></v-breadcrumb>
        <div class="container">
            <explorer-nav></explorer-nav>
            <div class="row">
                <div class="col-md-12">
                <div class="card shadow-sm" style="margin-bottom: 15px;">
                    <div class="card-header">블록 <strong># {{ block.blockNo }}</strong></div>
                    <table class="table">
                        <tbody>
                            <tr>
                                <th width="300">블록 height</th>
                                <td>{{ block.blockNo }}</td>
                            </tr>
                            <tr>
                                <th>블록 해시</th>
                                <td>{{ block.hash }}</td>
                            </tr>
                            <tr>
                                <th>블록 생성 시간</th>
                                <td>{{ block.timeStamp }}</td>
                            </tr>
                            <tr>
                                <th>Miner</th>
                                <td><router-link :to="{ name: 'explorer.tx.detail.fromto', params: { address: block.miner }}" class="block-number">{{ block.miner }}</router-link></td>
                            </tr>
                            <tr>
                                <th>Nonce</th>
                                <td>{{ block.nonce }}</td>
                            </tr>
                            <tr>
                                <th>문제 난이도 (Difficulty)</th>
                                <td>{{ block.difficulty }}</td>
                            </tr>
                            <tr>
                                <th>블록 크기</th>
                                <td>{{ block.size }} bytes</td>
                            </tr>
                            <tr>
                                <th>gasLimit</th>
                                <td>{{ block.gasLimit }}</td>
                            </tr>
                            <tr>
                                <th>gasUsed</th>
                                <td>{{ block.gasUsed }}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                </div>
            </div>
        </div>
        <v-foot-nav></v-foot-nav>
    </div>
    `,
    data() {
        return {
            isValid: true,
            block: {
                number: 0
            }
        }
    },
    mounted: async function(){
        // TODO 
        var blockNumber = this.$route.params.blockNumber; // 조회할 블록 번호를 초기화 합니다. 
        
        if(blockNumber) {
            /**
             * 블록 번호로 블록 정보를 가져옵니다. 
             */  
            var findblock = (blocks) => {
                    this.block = blocks;
                    this.block.timeStamp = timeSince(this.block.timeStamp);
                    console.log(this.block);
                    
                };

            await ethereumService.findbyBlock(blockNumber, findblock);
        } else {
            this.isValid = false;
        }
    }
})
