var explorerTxDetailView = Vue.component('ExplorerTxDetailView', {
    template: `
        <div>
            <v-nav></v-nav>
            <v-breadcrumb title="Transaction Explorer" description="블록체인에서 생성된 거래내역을 보여줍니다." titleImg="assets/images/explorer_title.jpg"></v-breadcrumb>
            <div class="container">
                <explorer-nav></explorer-nav>
                <div class="row">
                    <div class="col-md-12">
                        <div class="card shadow-sm" style="margin-bottom: 15px;">
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
                                        <td><router-link :to="{ name: 'explorer.tx.detail.fromto', params: { address: tx.from }}">{{ tx.from }}</router-link></td>
                                    </tr>
                                    <tr>
                                        <th>수신자 주소</th>
                                        <td><router-link :to="{ name: 'explorer.tx.detail.fromto', params: { address: tx.to }}">{{ tx.to }}</router-link></td>
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
            <v-foot-nav></v-foot-nav>
        </div>
    `,
    data() {
        return {
            isValid: true,
            tx: {
                from: "Loding...",
                to: "Loding...",
                hash: "-"

            },
            timestamp: ""
        }
    },
    mounted: async function () {
        var hash = this.$route.params.hash; // 조회할 트랜잭션 해시를 초기화합니다. 

        if (hash) {
            /**
             * 트랜잭션 해시값으로 트랜잭션 정보를 가져옵니다. 
             */
            var tn = (tran) => {
                this.tx = tran;

                var blockNumber = this.tx.blockNumber;
                var next = parseInt(blockNumber, 16);

                var bn = (block) => {
                    this.blocks = block;
                    this.tx.timestamp = timeSince(this.blocks.timeStamp);
                    this.number = String(this.tx.amount);
                    this.tx.amount = web3.utils.fromWei(this.number, "ether");
                }
                ethereumService.findbyBlock(next, bn);
            }
            await ethereumService.findbyTrans(hash, tn);
        } else {
            this.isValid = false;
        }
    }
})
