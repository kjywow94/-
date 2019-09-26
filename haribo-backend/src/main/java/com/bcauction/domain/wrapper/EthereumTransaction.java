package com.bcauction.domain.wrapper;

import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class EthereumTransaction {

    private String txHash;
    private String Status;
    private String blockId;
    private LocalDateTime timestamp;
    private String from;
    private String to;
    private BigInteger amount;
    private boolean accepted;
    
    //이하는 추가로 추가한 속성 (추후 문제 발생시 수정할것)
    private String gas;
    private String gasPrice;
    private String blockNumber;

    public static EthereumTransaction getEthereumTransaction(final EthBlock.TransactionResult tx,
                                                              final BigInteger timestamp,
                                                              final boolean accepted){
        EthereumTransaction ethTx = new EthereumTransaction();
        if(tx instanceof EthBlock.TransactionHash){
            ethTx.txHash = ((EthBlock.TransactionHash) tx).get();
        } else if(tx instanceof EthBlock.TransactionObject){
            ethTx = EthereumTransaction.convertTransaction(((EthBlock.TransactionObject) tx).get());
        }

        ethTx.accepted = accepted;
        ethTx.timestamp = (timestamp != null) ? LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp.longValue()), TimeZone.getDefault().toZoneId()) : null;

        return ethTx;
    }

    public static List<EthereumTransaction> getEthereumTransactionList(final List<EthBlock.TransactionResult> txs, final BigInteger timestamp, final boolean accepted){
        List<EthereumTransaction> txList = new ArrayList<>();

        for(EthBlock.TransactionResult t: txs) {
            EthereumTransaction etx = EthereumTransaction.getEthereumTransaction(t, timestamp, accepted);
            etx.setStatus("0x01");
            txList.add(etx);
        }

        return txList;
    }

    public static EthereumTransaction convertTransaction(Transaction transaction){
        EthereumTransaction tx = new EthereumTransaction();

        tx.txHash = transaction.getHash();
        tx.blockId = String.valueOf(transaction.getBlockNumber());
        tx.from = transaction.getFrom();
        tx.to = transaction.getTo();
        tx.amount = transaction.getValue();
        
        // 이하는 추가로 지정한 초기화(문제 발생시 수정할 것)
        tx.gas = transaction.getGasRaw();
        tx.gasPrice = transaction.getGasPriceRaw();
        tx.blockNumber = transaction.getBlockNumberRaw();

        return tx;
    }

    public static EthereumTransaction convertTransaction(final com.bcauction.domain.Transaction transaction)
    {
        EthereumTransaction tx = new EthereumTransaction();
        tx.txHash = transaction.getHash();
        tx.blockId = transaction.getBlockNumber();
        tx.from = transaction.getFrom();
        tx.to = transaction.getTo();
        if(transaction.getValue() != null)
            tx.amount = new BigInteger(transaction.getValue());
        
        // 이하는 추가로 지정한 초기화(문제 발생시 수정할 것)
        tx.gas = transaction.getGas();
        tx.gasPrice = transaction.getGasPrice();
        tx.blockNumber = transaction.getBlockNumber();
        
        tx.timestamp = transaction.get저장일시();

        return tx;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
    
    
    //이하는 추가로 설정한 Getter/Setter (문제 발생시 수정할 것)

	public String getGas() {
		return gas;
	}

	public void setGas(String gas) {
		this.gas = gas;
	}

	public String getGasPrice() {
		return gasPrice;
	}

	public void setGasPrice(String gasPrice) {
		this.gasPrice = gasPrice;
	}

	public String getBlockNumber() {
		return blockNumber;
	}

	public void setBlockNumber(String blockNumber) {
		this.blockNumber = blockNumber;
	}

	@Override
	public String toString() {
		return "EthereumTransaction [txHash=" + txHash + ", Status=" + Status + ", blockId=" + blockId + ", timestamp="
				+ timestamp + ", from=" + from + ", to=" + to + ", amount=" + amount + ", accepted=" + accepted
				+ ", gas=" + gas + ", gasPrice=" + gasPrice + ", blockNumber=" + blockNumber + "]";
	}
}
