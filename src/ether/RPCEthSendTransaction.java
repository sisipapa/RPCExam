package ether;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
//import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.ipc.UnixIpcService;
import org.web3j.protocol.ipc.WindowsIpcService;
import org.web3j.utils.Convert;

import common.Environment;

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
public class RPCEthSendTransaction {
	
	private Web3j web3j;
	private Admin admin;
	
	private BigDecimal defaultGasPrice = BigDecimal.valueOf(5);
	
	public RPCEthSendTransaction() {
		web3j = Web3j.build(new HttpService(Environment.ETH_RPC_URL));
		admin = Admin.build(new HttpService(Environment.ETH_RPC_URL));
	}
	
	/**
	 * 
	 * <pre>
	 * TRANSACTION - 잔액조회
	 * </pre>
	 * 
	 * @Method Name : getBalance
	 * 
	 * @param address
	 * @return
	 */
	private BigInteger getBalance(String address) {
		BigInteger balance = null;
		try {
			EthGetBalance ethGetBalance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
			balance = ethGetBalance.getBalance();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("address " + address + " balance " + balance + "wei");
		return balance;
	}

	/**
	 * 
	 * <pre>
	 * TRANSACTION - 거래생성
	 * </pre>
	 * 
	 * @Method Name : makeTransaction
	 * 
	 * @param fromAddress
	 * @param toAddress
	 * @param nonce
	 * @param gasPrice
	 * @param gasLimit
	 * @param value
	 * @return
	 */
	private Transaction makeTransaction(String fromAddress, String toAddress,
											   BigInteger nonce, BigInteger gasPrice,
											   BigInteger gasLimit, BigInteger value) {
		Transaction transaction;
		
		transaction = Transaction.createEtherTransaction(fromAddress, nonce, gasPrice, gasLimit, toAddress, value);
		return transaction;
	}

	/**
	 * 
	 * <pre>
	 * TRANSACTION - TODO설명
	 * </pre>
	 * 
	 * @Method Name : getTransactionGasLimit
	 * 
	 * @param transaction
	 * @return
	 */
	private BigInteger getTransactionGasLimit(Transaction transaction) {
		BigInteger gasLimit = BigInteger.ZERO;
		try {
			EthEstimateGas ethEstimateGas = web3j.ethEstimateGas(transaction).send();
			gasLimit = ethEstimateGas.getAmountUsed();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return gasLimit;
	}

	/**
	 * 
	 * <pre>
	 * TRANSACTION - TODO설명
	 * </pre>
	 * 
	 * @Method Name : getTransactionNonce
	 * 
	 * @param address
	 * @return
	 */
	private BigInteger getTransactionNonce(String address) {
		BigInteger nonce = BigInteger.ZERO;
		try {
			EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(address, DefaultBlockParameterName.PENDING).send();
			nonce = ethGetTransactionCount.getTransactionCount();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nonce;
	}

	/**
	 * 
	 * <pre>
	 * TRANSACTION - TODO설명
	 * </pre>
	 * 
	 * @Method Name : sendTransaction
	 * 
	 * @param fromAddress
	 * @param toAddress
	 * @param fromPassword
	 * @return
	 */
	public String sendTransaction(String fromAddress, String toAddress, String fromPassword, double damount) {
//		String password = "111222333";
//		String password = "qqqwwweee";
		BigInteger unlockDuration = BigInteger.valueOf(60L);
		BigDecimal amount = new BigDecimal(String.valueOf(damount));
		String txHash = null;
		try {
			PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(fromAddress, fromPassword, unlockDuration).send();
//			PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(toAddress, password, unlockDuration).send();
			if (personalUnlockAccount.accountUnlocked()) {
				BigInteger value = Convert.toWei(amount, Convert.Unit.ETHER).toBigInteger();
				Transaction transaction = makeTransaction(fromAddress, toAddress,
//				Transaction transaction = makeTransaction(toAddress, fromAddress,
						null, null, null, value);
				//不是必须的 可以使用默认值
				BigInteger gasLimit = getTransactionGasLimit(transaction);
				//不是必须的 缺省值就是正确的值
				BigInteger nonce = getTransactionNonce(fromAddress);
//				BigInteger nonce = getTransactionNonce(toAddress);
				//该值为大部分矿工可接受的gasPrice
				BigInteger gasPrice = Convert.toWei(defaultGasPrice, Convert.Unit.GWEI).toBigInteger();
				transaction = makeTransaction(fromAddress, toAddress,
//				transaction = makeTransaction(toAddress, fromAddress,
						nonce, gasPrice,
						gasLimit, value);
				EthSendTransaction ethSendTransaction = web3j.ethSendTransaction(transaction).send();
				txHash = ethSendTransaction.getTransactionHash();
				
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("tx hash " + txHash);
		return txHash;
	}
	
}
