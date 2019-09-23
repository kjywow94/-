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
                    <tbody>
                        <tr v-for="tran in trans">
                            <th scope="row"> {{ tran.txHash | truncate(15) }} </th>
                            <td scope="row"> {{ tran.blockNumber}} </td>
                            <td scope="row"> {{ tran.timestamp}} </td>
                            <td scope="row"> {{ tran.from | truncate(15) }} </td>
                            <td scope="row"> {{ tran.to | truncate(15) }} </td>
                            <td scope="row"> {{ tran.value}}</td>
                            <td scope="row"> {{ tran.tax}} </td>
                        </tr>
                    </tbody>
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
            transactions: [],
            trans: [],
            count: 0
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
                var idx = 0;
                var cnt = 0;
       
                this.fal = this.fa.trans.length;
                
                var tr = (tran) => {
                    var txView = {
                        hash: tran.txHash,
                        from: tran.from,
                        to: tran.to
                    }

                    this.$set(this.transactions, cnt++,  txView);
      
                }

                ethereumService.findbyTrans(this.fa.id, tr);

                
                for (var i = 0; i < this.fal; i++) {
                    let hex = this.fa.trans[i].blockNumber; 
                    let dec = parseInt(hex, 16);
                    this.fa.trans[i].blockNumber = dec;
                    var num = 0;

                    var bn =  (blocks) => {
                
                        var timeView = {
                            
                            time: blocks.timeStamp
                        }

                        var time = timeSince(timeView.time);
                        this.fa.trans[num].timestamp = time;
                        num++;
                    }

                    ethereumService.findbyBlock(this.fa.trans[i].blockNumber, bn);
                    
                    this.$set(this.trans, idx++, this.fa.trans[i]);
                }
                
                
            }

            await ethereumService.findByAddress(addr, ta);

        } else {
            this.isValid = false;
        }
    }
})