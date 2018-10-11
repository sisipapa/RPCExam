package ether;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.NewAccountIdentifier;
import org.web3j.protocol.admin.methods.response.PersonalListAccounts;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlock.TransactionResult;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthTransaction;
//import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.http.HttpService;

import common.Environment;
import rx.Subscription;

/**
 * 
 * <h1>이더리움 관련 계좌생성, 전송, 계정의 전송내역</h1>
 * 
 * <pre>
 * contents
 * </pre>
 * 
 * @author : 김경훈
 * @since  : 2018. 8. 29. 오후 1:24:02
 */
public class RPCEthFilter {
	
	private Web3j web3j;
	
	public RPCEthFilter() {
		web3j = Web3j.build(new HttpService(Environment.ETH_RPC_URL));
	}
	
	public void newBlockFilter() {
		Subscription subscription = web3j.
				blockObservable(false).
				subscribe(block -> {
					System.out.println("new block come in");
					System.out.println("block number" + block.getBlock().getNumber());
				});
	}

	public void newTransactionFilter() {
		Subscription subscription = web3j.
				transactionObservable().
				subscribe(transaction -> {
					System.out.println("transaction come in");
					System.out.println("transaction txHash " + transaction.getHash());
				});
	}

	public void replayFilter() {
		
		
		BigInteger startBlock = null;
		BigInteger endBlock = null;
		
		EthBlockNumber ethBlockNumber = null;
//		EthBlock ethBlock = null;
		List<String> hashList = new ArrayList<String>(); 
		try {
			
			ethBlockNumber =web3j.ethBlockNumber().send();
//			ethBlock = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send();
			
			startBlock = BigInteger.valueOf(0);
			endBlock = ethBlockNumber.getBlockNumber();
//			endBlock = ethBlock.getBlock().getNumber();
			System.out.println("endBlock : " + endBlock);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Subscription subscription = web3j.
				replayBlocksObservable(
						DefaultBlockParameter.valueOf(startBlock),
						DefaultBlockParameter.valueOf(endBlock),
						false).
				subscribe(ethBlock -> {
					System.out.println("replay block");
					System.out.println(ethBlock.getBlock().getNumber());
				});
		
		web3j.replayTransactionsObservable(DefaultBlockParameter.valueOf(startBlock),
				DefaultBlockParameter.valueOf(endBlock)).subscribe();
		Subscription subscription1 = web3j.
				replayTransactionsObservable(
						DefaultBlockParameter.valueOf(startBlock),
						DefaultBlockParameter.valueOf(endBlock)).
				subscribe((transaction) -> {
					System.out.println("replay transaction");
					System.out.println("txHash " + transaction.getHash());
					System.out.println("blockHash " + transaction.getBlockHash());
				});
		
	}

	public void catchUpFilter() {
		BigInteger startBlock = BigInteger.valueOf(2000000);

		Subscription subscription = web3j.catchUpToLatestAndSubscribeToNewBlocksObservable(
				DefaultBlockParameter.valueOf(startBlock), false)
				.subscribe(block -> {
					System.out.println("block");
					System.out.println(block.getBlock().getNumber());
				});

		Subscription subscription2 = web3j.catchUpToLatestAndSubscribeToNewTransactionsObservable(
				DefaultBlockParameter.valueOf(startBlock))
				.subscribe(tx -> {
					System.out.println("transaction");
					System.out.println(tx.getHash());
				});
	}
	
}
