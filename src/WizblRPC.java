import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import bitcoin.RPCBitcoin;
import bitcoin.RPCBitcoindClient;
import ether.RPCEthAccount;
import ether.RPCEthSendTransaction;
import ether.RPCEthTransactionList;

public class WizblRPC {
	
	public static void main(String[] args) throws IOException{
		/******************************** 이더리움 테스트 시작 ********************************/
		new WizblRPC().ethereum();
		/******************************** 이더리움 테스트 종료 ********************************/
		
		/******************************** 비트코인 테스트 시작 ********************************/
//		new WizblRPC().bitcoin();
//		new WizblRPC().bitcoinClient();
		/******************************** 비트코인 테스트 종료 ********************************/
	}
	
	public void ethereum() {
		// TESTNET - rinkeby BLOCKCHAIN
		/**************************************************************
		 coolguy230 - 0x31d053dc044b4d802507768a01e7ea50ec90d85a
		 coolguy231 - 0xbcd2367d38307cd05b207c77322275f2c14a61fb
		**************************************************************/
//		new RPCEthAccount().createNewAccount("coolguy231");	// 계정생성
//		new RPCEthAccount().unlockAccount("0x31d053dc044b4d802507768a01e7ea50ec90d85a", "coolguy230");		// 계정unlock
//		new RPCEthAccount().unlockAccount("0xbcd2367d38307cd05b207c77322275f2c14a61fb", "coolguy231");		// 계정unlock
		new RPCEthAccount().getAccountList();				// 계정목록
//		new RPCEthSendTransaction().sendTransaction("0x31d053dc044b4d802507768a01e7ea50ec90d85a", "0xbcd2367d38307cd05b207c77322275f2c14a61fb", "coolguy230", 0.5);	// Transaction
//		new RPCEthTransactionList().getTransactionAllList(""); // 거래목록
		
		// PRIVATE BLOCKCHAIN
//		new RPCEthAccount().createNewAccount("12121212");	// 계정생성
//		new RPCEthAccount().unlockAccount("0x8fb40485f0b852b626b3b24ebff9c78500b1df71", "111222333");		// 계정unlock
//		new RPCEthAccount().unlockAccount("0xde7edd81450c57e13a7e5312c2ded96c39712dfd", "qqqwwweee");		// 계정unlock
//		new RPCEthAccount().getAccountList();				// 계정목록
//		new RPCEthSendTransaction().sendTransaction("0xde7edd81450c57e13a7e5312c2ded96c39712dfd", "0x8fb40485f0b852b626b3b24ebff9c78500b1df71", "qqqwwweee", 3);	// Transaction
//		new RPCEthTransactionList().getTransactionAllList(""); // 거래목록
	}
	
	
	public void bitcoin() {
		
//		System.out.println("rpcbitcoin.GetWalletInfo()=" + new RPCBitcoin().GetWalletInfo());
//		System.out.println("rpcbitcoin.GetNewAddress()=" + new RPCBitcoin().GetNewAddress("test3@gmail.com"));
//		System.out.println("rpcbitcoin.GetAllAddress()=" + new RPCBitcoin().GetAllAddress("test3@gmail.com"));
//		System.out.println("rpcbitcoin.GetReceivedByAccount()=" + new RPCBitcoin().GetReceivedByAccount("test3@gmail.com"));
		System.out.println("rpcbitcoin.ListTransactions()=" + new RPCBitcoin().ListTransactions("test3@gmail.com"));
		
	}
	
	public void bitcoinClient() {
		
		/********************************
		 testuser1 - 2NBnZKq7Gvjxvw1ZVzj6cHPTCyLrxTypYae
		 testuser2 - 2NAv9xQ5ENKiAJCcDXTGCXdyJC4hsedJ7X8
		 ********************************/
		
		RPCBitcoindClient rpc = new RPCBitcoindClient();
		
//		rpc.getInfo();
//		rpc.generate(1);
//		rpc.getBalance("");
//		System.out.println("getNewAddress : " + rpc.getNewAddress("testuser2"));
//		System.out.println("listAccounts : " + rpc.listAccounts());
//		System.out.println("sendToAddress : " + rpc.sendToAddress("2NBnZKq7Gvjxvw1ZVzj6cHPTCyLrxTypYae", 210));
//		System.out.println("sendFrom : " + rpc.sendFrom("testuser2","2NBnZKq7Gvjxvw1ZVzj6cHPTCyLrxTypYae",3));
//		viewList(rpc.listunspent(0));
		viewList(rpc.listTransactions("testuser1"));
		
	}
	
	private void viewList(List<String> list) {
		Iterator<String> iter = list.iterator();
		int i=1;
		while(iter.hasNext()) {
			String val = iter.next();
			System.out.println(i + "번째 - " + val);
			i++;
			
		}
	}
}
