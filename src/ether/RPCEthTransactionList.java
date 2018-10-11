package ether;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlock.TransactionResult;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthTransaction;
//import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.http.HttpService;

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
public class RPCEthTransactionList {
	
	private Web3j web3j;
	
	public RPCEthTransactionList() {
		web3j = Web3j.build(new HttpService(Environment.ETH_RPC_URL));
	}
	
	/************************************************** TRANSACTION 목록조회 관련 START **************************************************/
	public void getTransactionAllList(String account) {
		
		try {
			EthBlockNumber ethBlockNumber = web3j.ethBlockNumber().send();
			
			
//			ethBlock = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send();
		
			BigInteger startBlock = BigInteger.valueOf(0);
			BigInteger endBlock = ethBlockNumber.getBlockNumber();
//			BigInteger endBlock = ethBlock.getBlock().getNumber();
			System.out.println("endBlock : " + endBlock);
			
			int k=0;
			for(int i=startBlock.intValue(); i<endBlock.intValue(); i++ ) {
				EthBlock ethBlock = web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(BigInteger.valueOf(i)), false).send();
				List<TransactionResult> list = ethBlock.getBlock().getTransactions();
				
				for(int j=0; j<list.size(); j++) {
					TransactionResult tr = list.get(j);
					String trHash = (String)tr.get();
					Request<?, EthTransaction> objectList = web3j.ethGetTransactionByHash(trHash);
					EthTransaction eth = objectList.send();
					System.out.println("---->" + eth.getJsonrpc());
					System.out.println("---->" + eth.getTransaction());
					BigInteger index = BigInteger.ONE;
					
					Transaction transaction = eth.getTransaction().get();
					// assertThat(transaction.getBlockHash(), is(config.validBlockHash()));
					System.out.println("transaction.getBlockHash()---->" + transaction.getBlockHash());
					System.out.println("transaction.getTransactionIndex()---->" + transaction.getTransactionIndex());

					// System.out.println("------"+transaction.getHash() );//hash: Hash- 32 Bytes -
					// 트랜잭션의 해시.

					// System.out.println("------"+transaction.getNonce() );// nonce: Quantity- 이전에
					// 보낸 사람이 수행 한 트랜잭션의 수입니다.

					// System.out.println("------"+transaction.getBlockHash() );//: Hash- 32 Bytes
					// -이 트랜잭션이 있던 블록의 해시. null보류 중일 때.

					// System.out.println("------"+transaction.getBlockNumber();//: Quantity Tag-
					// null보류중인 트랜잭션이 있었던 블록 번호 .

					// System.out.println("------"+transaction.getTransactionIndex();//
					// .transactionIndex: Quantity- 블록 내의 트랜잭션 인덱스 위치의 정수. null보류 중일 때.

					System.out.println("from------" + transaction.getFrom());// .from: Address- 20 Bytes - 보낸 사람의 주소입니다.
					System.out.println("to--------" + transaction.getTo());// .to: Address- 20 Bytes - 수신자의 주소. null언제 계약 생성 거래.
					System.out.println("value-----" + transaction.getValue());// .value: Quantity- 웨이에서 양도 된 가치.

					// System.out.println("------"+transaction.getGasPrice();// .gasPrice: Quantity-
					// Wei에서 보낸 사람이 제공 한 가스 가격.

					// System.out.println("------"+transaction.getGas();// .gas: Quantity- 송부자가 제공 한
					// 가스.

					// System.out.println("------"+transaction.getInput();// .input: Data- 데이터가
					// 트랜잭션과 함께 전송됩니다.

					// System.out.println("------"+transaction.getV();// .v: Quantity- 서명의 표준화 된 V
					// 필드.

					// transaction.getst .standard_v: Quantity- 서명의 표준화 된 V 필드 (0 또는 1).

					// System.out.println("------"+transaction.getR();// .r: Quantity- 서명의 R 필드.

					// System.out.println("------"+transaction.getRaw();// .raw: Data- 원시 트랜잭션 데이터

					// System.out.println("------"+transaction.getPublicKey();// .publicKey: Hash-
					// 서명자의 공개 키.

					// transaction .networkId: Quantity- 트랜잭션의 네트워크 ID (있는 경우).

					// System.out.println("------"+transaction.getCreates();// .creates: Hash- 계약
					k++;
				}
			}
			System.out.println("total : " + k);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/************************************************** TRANSACTION 목록조회 관련 END **************************************************/
}
