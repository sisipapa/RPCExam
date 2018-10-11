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
import org.web3j.protocol.ipc.WindowsIpcService;

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
public class RPCEthAccount {
	
	private Admin admin;
	
//	private String fromAddress = "0x8fb40485f0b852b626b3b24ebff9c78500b1df71";
//	private String toAddress = "0x379ee7CE65951832B09EACFFD95437b1350aD508";
	
	public RPCEthAccount() {
		admin = Admin.build(new HttpService(Environment.ETH_RPC_URL));
		
		// OS X/Linux/Unix:
//		web3j = Web3j.build(new UnixIpcService("/path/to/socketfile"));

		// Windows
//		admin = Admin.build(new WindowsIpcService("C:\\Geth\\data\\geth.ipc"));
	}
	
	/**
	 * 
	 * <pre>
	 * 계좌생성
	 * </pre>
	 * 
	 * @Method Name : createNewAccount
	 *
	 */
	public void createNewAccount(String password) {
//		String password = "111222333"; //0x8fb40485f0b852b626b3b24ebff9c78500b1df71
//		String password = "qqqwwweee"; //0xde7edd81450c57e13a7e5312c2ded96c39712dfd
		
		try {
			NewAccountIdentifier newAccountIdentifier = admin.personalNewAccount(password).send();
			String address = newAccountIdentifier.getAccountId();
			System.out.println("new account address " + address);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * <pre>
	 * 계좌목록조회
	 * </pre>
	 * 
	 * @Method Name : getAccountList
	 *
	 */
	public void getAccountList() {
		try {
			PersonalListAccounts personalListAccounts = admin.personalListAccounts().send();
			List<String> addressList;
			addressList = personalListAccounts.getAccountIds();
			System.out.println("account size : " + addressList.size());
			for (String address : addressList) {
				System.out.println(address);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * <pre>
	 * 계정 unlock
	 * </pre>
	 * 
	 * @Method Name : unlockAccount
	 *
	 */
	public void unlockAccount(String address, String password) {
//		String address = "0xde7edd81450c57e13a7e5312c2ded96c39712dfd";
//		String password = "qqqwwweee";
		BigInteger unlockDuration = BigInteger.valueOf(6000L);
		try {
			PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(address, password, unlockDuration).send();
			Boolean isUnlocked = personalUnlockAccount.accountUnlocked();
			System.out.println("account unlock " + isUnlocked);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
}
