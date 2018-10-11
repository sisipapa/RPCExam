package bitcoin;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import common.Environment;
import wf.bitcoin.javabitcoindrpcclient.BitcoinJSONRPCClient;
import wf.bitcoin.javabitcoindrpcclient.BitcoindRpcClient.Transaction;
import wf.bitcoin.javabitcoindrpcclient.BitcoindRpcClient.Unspent;

/**
 * <pre>
 * 비트코인 RPC 클라이언트
 * </pre>
 * 
 */
public class RPCBitcoindClient {
	
	private String host;
	private int port;
	private String rpcuser;
	private String rpcpassword;
	URL url = null;
	BitcoinJSONRPCClient client = null;
	
	/**
	 * 기본생성
	 */
	public RPCBitcoindClient() {
		
		this.host = Environment.BIT_RPC_HOST;
		this.port = Integer.parseInt(Environment.BIT_RPC_PORT);
		this.rpcuser = Environment.BIT_RPC_USER;
		this.rpcpassword = Environment.BIT_RPC_PASSWORD;
		
		try {
			url = new URL("http://" + rpcuser + ':' + rpcpassword + "@" + host + ":" + port + "/");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		client = new BitcoinJSONRPCClient(url);
		
	}
	
	/**
	 * 
	 * <pre>
	 * 정보조회
	 * </pre>
	 * 
	 * @Method Name : getInfo
	 *
	 */
	public void getInfo() {
		
		System.out.println(client.getBlockChainInfo());
		System.out.println(client.getNetworkInfo());
		System.out.println(client.getWalletInfo());
		
	}
	
	/**
	 * 
	 * <pre>
	 * regtest 모드에서 블럭 mining
	 * </pre>
	 * 
	 * @Method Name : generate
	 * @param blockcount
	 */
	public void generate(int blockcount) {
		System.out.println("before generate blockcount : " + client.getBlockCount());
		client.generate(blockcount);
		System.out.println("after generate blockcount : " + client.getBlockCount());
	}
	
	/**
	 * 
	 * <pre>
	 * 확정/미확정 트랜잭션 확인
	 * </pre>
	 * 
	 * @Method Name : listunspent
	 * @param num 
	 * @return
	 */
	public List<String> listunspent(int num){

		List<Unspent> list = null;
		if(num == 0) {
			list = client.listUnspent(0);	// 미확정 트랜잭션 확인
		}else {
			list = client.listUnspent();	// 확정 트랜잭션 확인
		}
		
		List<String> returnList = new ArrayList<String>();
		Iterator<Unspent> iter = list.iterator();
		while(iter.hasNext()) {
			Unspent unspent = iter.next();
			returnList.add(
						unspent.txid() + " | " +
						unspent.vout() + " | " +
						unspent.address() + " | " +
						unspent.scriptPubKey() + " | " +
						unspent.amount() + " | " +
						unspent.confirmations() + " | "
					);
		}
		
		return returnList;
	}
	
	/**
	 * 
	 * <pre>
	 * 잔액조회
	 * </pre>
	 * 
	 * @Method Name : getBalance
	 * @param account
	 */
	public void getBalance(String account) {
		
		if(account != null && !account.equals("")) {
			System.out.println(account + " : " +client.getBalance(account));
		}else {
			System.out.println("\"\"" + " : " +client.getBalance());
		}
		
	}
	
	/**
	 * 
	 * <pre>
	 * 계정생성
	 * </pre>
	 * 
	 * @Method Name : getNewAddress
	 * @param email
	 * @return
	 */
	public String getNewAddress(String email) {
		
		String address = client.getNewAddress(email);
		return address;
		
	}
	
	/**
	 * 
	 * <pre>
	 * 계정목록
	 * </pre>
	 * 
	 * @Method Name : listAccounts
	 * @return
	 */
	public List<String> listAccounts(){
		
		List<String> list = new ArrayList<String>();
		Map<String,Number> accounts = client.listAccounts();
	    Set<String> keyset = accounts.keySet();
	    Iterator<String> iter = keyset.iterator();
	    while(iter.hasNext()) {
	    	
	    	
	    	String key = iter.next();
	    	String addKey = key;
	    	
	    	if(addKey.equals("")) addKey = "\"\"";
	    	list.add(addKey + ":" + accounts.get(key));
	    	
	    	System.out.println(key);
	    }
		return list;
		
	}
	
	/**
	 * 
	 * <pre>
	 * 전송 sendtoaddress
	 * </pre>
	 * 
	 * @Method Name : sendToAddress
	 * @param account
	 * @param amount
	 * @return
	 */
	public String sendToAddress(String account, double amount) {
		
		String transactionId = client.sendToAddress(account, amount);
		return transactionId;
		
	}
	
	/**
	 * 
	 * <pre>
	 * 전송 sendfrom
	 * </pre>
	 * 
	 * @Method Name : sendFrom
	 * @param fromaccount
	 * @param toaddress
	 * @param amount
	 * @return
	 */
	public String sendFrom(String fromaccount, String toaddress, double amount) {
		
		String transactionId = client.sendFrom(fromaccount, toaddress, amount);
		return transactionId;
		
	}
	
	/**
	 * 
	 * <pre>
	 * 계정의 거래내역
	 * </pre>
	 * 
	 * @Method Name : listTransactions
	 * @param email
	 * @return
	 */
	public List<String> listTransactions(String account){
		
		List<Transaction> list = client.listTransactions(account);
		List<String> returnList = new ArrayList<String>();
		Iterator<Transaction> iter = list.iterator();
		while(iter.hasNext()) {
			Transaction transaction = iter.next();
			returnList.add(
					transaction.account() + " | " +
					transaction.address() + " | " +
				    transaction.category() + " | " +
				    transaction.amount() + " | " +
				    transaction.confirmations() + " | " +
				    transaction.blockHash() + " | " + 
				    transaction.blockTime() + " | " +
				    transaction.txId()
					);
		}
		
		return returnList;
	}
	
}
