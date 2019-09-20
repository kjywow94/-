var explorerTxDetailToView = Vue.component('ExplorerTxDetailToView', {
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
                <br>
                <div class="card shadow-sm">
                <table class="table">
                <thead>
                  <tr>
                    <th scope="col">Txn Hash</th>
                    <th scope="col">Block</th>
                    <th style="color:blue;" scope="col">Age</th>
                    <th scope="col">From</th>
                    <th scope="col">To</th>
                    <th scope="col">Value</th>
                    <th scope="col">[Txn Fee]</th>
                  </tr>
                </thead>
                <div v-for="tran in transactions">
                <tbody>
                  <tr>
                    <th scope="row"> tran.txHash </th>
                    <td scope="row"> tran.blockId </td>
                    <td scope="row"> tran.timestamp </td>
                    <td scope="row"> tran.from </td>
                    <td scope="row"> tran.to </td>
                    <td scope="row"> tran.value</td>
                    <td scope="row"> tran.tax </td>
                  </tr>
                  </tbody>
                  </div>
              </table>
                </div>
                <br>
            </div>
        </div>
    `,
    data() {
        return {
            isValid: true,
            fa: {
                addr: "-"
                
            },
            transactions: [
                {
                    txHash : 1,
                    blockId : 2,
                    timestamp : 1,
                    from : 1,
                    to : 1,
                    value : 2,
                    tax : 1
                },
                {
                    txHash : 1,
                    blockId : 2,
                    timestamp : 1,
                    from : 1,
                    to : 1,
                    value : 2,
                    tax : 1
                }
            ],
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