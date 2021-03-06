var explorerBlockView = Vue.component('ExplorerBlockView', {
    template: `
    <div>
        <v-nav></v-nav>
        <v-breadcrumb title="Block Explorer" description="블록체인에서 생성된 블록내역을 보여줍니다." titleImg="assets/images/explorer_title.jpg"></v-breadcrumb>
        <div class="container">
            <explorer-nav></explorer-nav>
            <div class="row">
                <div class="col-md-12">
                    <div id="blocks" class="col-md-8 mx-auto">
                        <div class="card shadow-sm" style="margin-bottom: 15px;">
                            <div class="card-header">Blocks</div>
                            <div class="card-body" v-if="isLoding" >
                                <div class="row block-info" v-for="item in blocks">
                                    <div class="col-md-2">BK</div>
                                    <div class="col-md-4">
                                        <router-link :to="{name:'explorer.block.detail', params: {blockNumber:item.number}}" class="block-number">{{ item.number }}</router-link>
                                        <p class="block-timestamp">{{ item.timestamp }}</p>
                                    </div>
                                    <div class="col-md-6 text-right">
                                        <p class="block-num-transactions">{{ item.txCount }} Txes</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <v-foot-nav></v-foot-nav>
    </div>
    `,
    data(){
        return {
            lastReadBlock: 0,
            blocks: [{number:"Loding.."}],
            isLoding : false
        }
    },
    methods: {
        fetchBlocks: async function(){
            /**
             * TODO 최근 10개의 블록 정보를 업데이트 합니다.
             */
            var setBlock = (block) => {
                for(var i = 0; i < 10; i++){
                    var inputinfo = {
                        number : block[i].blockNo,
                        timestamp : timeSince(block[i].timeStamp),
                        txCount : block[i].trans.length
                    }
                    this.$set(this.blocks, i , inputinfo);
                }
            };
            await ethereumService.findblockAll(setBlock);
            this.isLoding = true;
        }
    },
    mounted: function(){
        this.fetchBlocks();

        this.$nextTick(function () {
            window.setInterval(() => {
                this.fetchBlocks();
            }, REFRESH_TIMES_OF_BLOCKS);
        })
    }
})
