var explorerTxListView = Vue.component('ExplorerTxListView', {
    template: `
        <div>
            <v-nav></v-nav>
            <v-breadcrumb title="Transaction Explorer" description="블록체인에서 생성된 거래내역을 보여줍니다." titleImg="assets/images/explorer_title.jpg"></v-breadcrumb>
            <div class="container">
                <explorer-nav></explorer-nav>
                <div class="row">
                    <div id="transactions" class="col-md-8 mx-auto">
                        <div class="card shadow-sm">
                            <div class="card-header">Transactions</div>
                            <div class="card-body">
                                <div class="row tx-info" v-for="item in transactions">
                                    <div class="col-md-2">Tx</div>
                                    <div class="col-md-4">
                                    <router-link :to="{name: 'explorer.tx.detail', params: { hash: item.hash }}" class="tx-number">{{ item.hash | truncate(10) }}</router-link>
                                        <p class="tx-timestamp">{{ item.timeSince }}</p>
                                    </div>
                                    <div class="col-md-6">
                                        <p><label class="text-secondary">From</label> <router-link :to="{ name: 'explorer.tx.detail.fromto', params: { address: item.from }}">{{ item.from | truncate(10) }}</router-link></p>
                                        <p><label class="text-secondary">To</label> <router-link :to="{ name: 'explorer.tx.detail.fromto', params: { address: item.to }}">{{ item.to | truncate(10) }}</router-link></p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `,
    data() {
        return {
            lastReadBlock: 0,
            transactions: [],
            block: {},
            txl: 0,
            strtime: 0
        };
    },
    methods: {
        fetchTxes: async function () {
            /**
             * TODO 최근 블록에 포함된 트랜잭션 리스트를 반환합니다. 
             */
            await ethereumService.findTransDeca(
                (tran) => {
                    for (let i = 0; i < tran.length; i++) {
                        var blockNumber = tran[i].blockNumber;
                        var next = parseInt(blockNumber, 16);
                        
                        ethereumService.findbyBlock(next, (block) => {
                            this.blocks = block;
                            
                            var inputinfo = {
                                hash: tran[i].txHash,
                                timeSince: timeSince(this.blocks.timeStamp),
                                from: tran[i].from,
                                to: tran[i].to
                            }
                            this.$set(this.transactions, i, inputinfo);
                        });
                    }
                });
        }
    },
    mounted: function () {
        this.fetchTxes();

        this.$nextTick(function () {
            window.setInterval(() => {
                this.fetchTxes();
            }, REFRESH_TIMES_OF_TRANSACTIONS);
        })
    }
})
