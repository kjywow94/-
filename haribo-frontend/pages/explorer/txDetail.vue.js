var explorerTxDetailView = Vue.component('ExplorerTxDetailView', {
    template: `
        <div>
            <v-nav></v-nav>
            <v-breadcrumb title="Transaction Explorer" description="블록체인에서 생성된 거래내역을 보여줍니다."></v-breadcrumb>
            <div class="container">
                <explorer-nav></explorer-nav>
                <div class="row">
                    <div class="col-md-12">
                        <div class="card shadow-sm">
                            <div class="card-header"><strong>{{ tx.txHash }}</strong></div>
                            <table class="table">
                                <tbody>
                                    <tr>
                                        <th width="200">트랜잭션 해시</th>
                                        <td>{{ tx.txHash }}</td>
                                    </tr>
                                    <tr>
                                        <th>블록 넘버</th>
                                        <td>{{ tx.blockId }}</td>
                                    </tr>
                                    <tr>
                                        <th>날짜</th>
                                        <td>{{ tx.timestamp }}</td>
                                    </tr>
                                    <tr>
                                        <th>송신자 주소</th>
                                        <td><router-link :to="{ name: 'explorer.tx.detail.from', params: { address: tx.from }}">{{ tx.from }}</router-link></td>
                                    </tr>
                                    <tr>
                                        <th>수신자 주소</th>
                                        <td><router-link :to="{ name: 'explorer.tx.detail.to', params: { address: tx.to }}">{{ tx.to }}</router-link></td>
                                    </tr>
                                    <tr>
                                        <th>전송한 이더</th>
                                        <td>{{ tx.amount }} Ether</td>
                                    </tr>
                                    <tr>
                                        <th>Gas</th>
                                        <td>{{ tx.gas }}</td>
                                    </tr>
                                    <tr>
                                        <th>Gas Price</th>
                                        <td>{{ tx.gasPrice }}</td>
                                    </tr>
                                    <tr>
                                        <th>Input Data</th>
                                        <td>
                                            <textarea class="form-control" readonly>{{ tx.input }}</textarea>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `,
    data() {
        return {
            isValid: true,
            tx: {
                hash: "-"
                
            },
            timestamp:""
        }
    },
    mounted: async function () {
        /**
         *  TODO 트랜잭션 해시로 트랜잭션 상세 정보를 조회합니다.
         */
        var hash = this.$route.params.hash; // 조회할 트랜잭션 해시를 초기화합니다. 

        if (hash) {
            /**
             * 트랜잭션 해시값으로 트랜잭션 정보를 가져옵니다. 
             */
            var tn = (tran) => {
                this.tx = tran;
                console.log(this.tx);
                
                  
                var blockNumber = this.tx.blockNumber;
                var next = parseInt(blockNumber, 16); 

               var bn = (block) => {
                   this.blocks = block;
                   this.tx.timestamp = timeSince(this.blocks.timeStamp); 
               }
               ethereumService.findbyBlock(next, bn);
           } 

           await ethereumService.findbyTrans(hash, tn);   

        } else {
            this.isValid = false;
        }
    }
})