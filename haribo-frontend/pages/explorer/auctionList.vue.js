var explorerAuctionView = Vue.component('ExplorerView', {
    template: `
    <div>
        <v-nav></v-nav>
        <v-breadcrumb title="Auction Explorer" description="블록체인에 기록된 경매내역을 보여줍니다."></v-breadcrumb>
        <div class="container">
            <explorer-nav></explorer-nav>
            <div class="row">
                <div class="col-md-12">
                    <table class="table table-bordered table-striped">
                        <thead>
                            <tr>
                                <th>Auction</th>
                                <th>Status</th>
                                <th>Highest Bid</th>
                                <th>Highest Bidder</th>
                                <th>Expire Date</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="(item, index) in contracts">
                                <td><router-link :to="{ name: 'explorer.auction.detail', params: { contractAddress: item } }">{{ item | truncate(15) }}</router-link></td>
                                <td>
                                    <span class="badge badge-primary" v-if="items[index] && !items[index].ended">Processing</span>
                                    <span class="badge badge-danger" v-if="items[index] && items[index].ended">Ended</span>
                                </td>
                                <td>{{ items[index] && items[index].higestBid }} ETH</td>
                                <td>
                                    <span v-if="items[index] && items[index].higestBid != 0">{{ items[index] && items[index].higestBidder | truncate(15) }}</span>
                                    <span v-if="items[index] && items[index].higestBid == 0">-</span>
                                </td>
                                <td>{{ items[index] && items[index].endTime.toLocaleString() }}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <v-foot-nav></v-foot-nav>
    </div>
    `,
    data() {
        return {
            contracts: [],
            items: []
        }
    },
    mounted: async function () {
        /**
         * TODO 
         * 1. AuctionFactory 컨트랙트로부터 경매컨트랙트 주소 리스트를 가져옵니다.
         * 2. 각 컨트랙트 주소로부터 경매의 상태(state) 정보를 가져옵니다. 
         * */
        let web3 = createWeb3();
        let scope = this;
        auction_list(response => {
            let len = response.length;
            scope.contracts = response.slice(len - 20, len).reverse();

            len = scope.contracts.length;
            let newitems = new Map();
            for (let idx = len - 1; idx >= 0; idx--) {
                auction_info(scope.contracts[idx], (ended, bid, bidder, auctionEndTime) => {

                    let inputItem = {
                        ended: ended,
                        higestBid: web3.utils.fromWei(bid, 'ether'),
                        higestBidder: bidder,
                        endTime: new Date(auctionEndTime * 1000)
                    }

                    // userService.findByWallet(bidder, response => {
                    //     inputItem.higestBidder = response.이메일;

                    //     newitems.set(scope.contracts[idx], inputItem);
                    //     let serializeMap = [];
                    //     for (let key of scope.contracts) {
                    //         serializeMap.push(newitems.get(key));
                    //     }
                    //     scope.items = serializeMap;
                    // })
                    newitems.set(scope.contracts[idx], inputItem);
                    let serializeMap = [];
                    for (let key of scope.contracts) {
                        serializeMap.push(newitems.get(key));
                    }
                    scope.items = serializeMap;
                })
            }
        });
    }
})