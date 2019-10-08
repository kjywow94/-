var explorerTxDetailFromToView = Vue.component('ExplorerTxDetailFromToView', {
    template: `
        <div>
            <v-nav></v-nav>
            <v-breadcrumb title="Transaction Explorer" description="블록체인에서 생성된 거래내역을 보여줍니다." titleImg="assets/images/explorer_title.jpg"></v-breadcrumb>
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
                <thead class="thead-light">
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
                            <th scope="row"><router-link :to="{name: 'explorer.tx.detail', params: { hash:tran.txHash }}" class="tx-number">{{ tran.txHash | truncate(15) }}</router-link></th>
                            <td scope="row"><router-link :to="{name:'explorer.block.detail', params: {blockNumber:tran.blockNumber}}" class="block-number">{{ tran.blockNumber }}</router-link></td>
                            <td scope="row">{{ tran.timestamp }}</td>
                            <td v-if="fa.id === tran.from" scope="row"> {{ tran.from | truncate(15) }}</td>
                            <td v-else scope="row"><router-link :to="{ name: 'explorer.tx.detail.fromto', params: { address: tran.from }}" class="block-number">{{ tran.from | truncate(15) }}</router-link></td>
                            <td v-if="fa.id === tran.to" scope="row"> {{ tran.to | truncate(15) }}</td>
                            <td v-else scope="row"><router-link :to="{ name: 'explorer.tx.detail.fromto', params: { address: tran.to }}" class="block-number">{{ tran.to | truncate(15) }}</router-link></td>
                            <td scope="row"> {{ tran.amount }} ether</td>
                            <td scope="row"> {{ tran.gas}} ether </td>
                        </tr>
                    </tbody>
                </table>                    
                </div>
                <br>
            </div>
            <v-foot-nav></v-foot-nav>
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
            count: 0,
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
                
                for (var i = this.fal -1; i >= 0; --i) {
                    let hex = this.fa.trans[i].blockNumber;
                    let getgas = this.fa.trans[i].gas;
         
                    let dec = parseInt(hex, 16);
                    let gass = parseInt(getgas, 16);
                    this.fa.trans[i].timestamp = timeFormat(this.fa.trans[i].timestamp);
              
                    
                    this.fa.trans[i].blockNumber = dec;
                    this.fa.trans[i].gas = gass;

                    this.number = String(this.fa.trans[i].gas);
                    this.fa.trans[i].gas = web3.utils.fromWei(this.number, "ether");
                  
                    this.value = String(this.fa.trans[i].amount);
                    
                    this.fa.trans[i].amount = web3.utils.fromWei(""+(this.value/'100'), 'ether')*'100';
                    
                    var len = this.fa.trans.length;
                    var num = len -1;
            
                    this.$set(this.trans, idx++, this.fa.trans[i]);
                }
            }

            await ethereumService.findByAddress(addr, ta);

        } else {
            this.isValid = false;
        }
    }
})