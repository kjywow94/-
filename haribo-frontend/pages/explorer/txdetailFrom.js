var explorerTxDetailFromView = Vue.component('ExplorerTxDetailFromView', {
    template: `
        <div>
            <v-nav></v-nav>
            <v-breadcrumb title="Transaction Explorer" description="블록체인에서 생성된 거래내역을 보여줍니다."></v-breadcrumb>
            <div class="container">
                <explorer-nav></explorer-nav>
                <div class="row">
                    <div class="col-md-12">
                        <div class="card shadow-sm">
                            <div class="card-header"><strong>{{ fa.id }}</strong></div>
                            <table class="table">
                                <tbody>
                                    <tr>
                                        <th width="200">아이디</th>
                                        <td>{{ fa.id }}</td>
                                    </tr>
                                    <tr>
                                        <th>Balance</th>
                                        <td>{{ fa.balance }}</td>
                                    </tr>
                                    <tr>
                                        <th>TxCount</th>
                                        <td>{{ fa.txCount }}</td>
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
            fa: {
                addr: "-"  
            },
            transactions: [],
            timestamp:""
        }
    },
    mounted: async function () {
        /**
         *  TODO from 주소를 상세 정보를 조회합니다.
         */
        var addr = this.$route.params.address; // 조회할 from 주소 초기화합니다.;
        
        if (addr) {
            /**
             * 트랜잭션 해시값으로 트랜잭션 정보를 가져옵니다. 
             */
            var ta = (address) => {
                this.fa = address;
                
                console.log(this.fa.id);
                
     
                var tr = (tran) => {
                    var txView = {
                       hash : tran.txHash,
                       from : tran.from,
                       to : tran.to
                    }
                    console.log(txView);
                    
                    this.$set(this.transactions, idx++, txView);   
                }

                ethereumService.findbyTrans(this.fa.id, tr);
                
                // var trans = this


                // ethereumService.findbyTrans()
                // var blockNumber = this.tx.blockNumber;
                // var next = parseInt(blockNumber, 16); 

            //    var bn = (block) => {
            //        this.blocks = block;
            //        this.tx.timestamp = timeSince(this.blocks.timeStamp); 
            //    }
            //    ethereumService.findbyBlock(next, bn);
           } 

           await ethereumService.findByAddress(addr, ta);   

        } else {
            this.isValid = false;
        }
    }
})