package ether;

import java.math.BigDecimal;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import common.CommonUtil;

/**
 * geth --rpc --rpcaddr "0.0.0.0" --rpcport "50044" --rpcapi "admin,db,eth,debug,miner,net,shh,txpool,personal,web3,http" --port 50004 --nodiscover --datadir "C:\Program Files\Geth\test1" --testnet console
 * > personal.newAccount("1q2w3e4r5t")
 */

@SuppressWarnings("deprecation")
public class RPCEth2 {
	private String HOST;
	private int PORT;
	private String APIKEYTOKEN;
	
	/**
	 * @param String host
	 * @param int port
	 * @param String apikeytoken
	 */
	public RPCEth2(String host, int port, String apikeytoken) {
		this.HOST = host;
		this.PORT = port;
		this.APIKEYTOKEN = apikeytoken;
	}
	
	/**
	 * "1": Ethereum Mainnet
	 * "2": Morden Testnet (deprecated)
	 * "3": Ropsten Testnet		http://api-ropsten.etherscan.io
	 * "4": Rinkeby Testnet		http://api-rinkeby.etherscan.io
	 * "42": Kovan Testnet		http://api-kovan.etherscan.io
	 * @return String json
	 */
	public String NetVersion() {
		String returnString = "";
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		JSONObject json = new JSONObject();
        json.put("jsonrpc", "1.0");
        json.put("id", "");
        json.put("method", "net_version");       
        
        try {            
            StringEntity myEntity = new StringEntity(json.toJSONString());
            HttpPost httppost = new HttpPost("http://" + HOST + ":" + PORT);
            httppost.setHeader("Accept", "application/json");
            httppost.setHeader("Content-type", "application/json");
            httppost.setEntity(myEntity);
            
            HttpResponse response = httpclient.execute(httppost);
            
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            	HttpEntity entity = response.getEntity();
            	
            	returnString = EntityUtils.toString(entity);
            	
            	EntityUtils.consume(entity);
            }
            else {
            	returnString = CommonUtil.getHttpMessage(response);
            }
        } catch (Exception e) {        	
        	returnString = CommonUtil.getErrorMessage(e);
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        
		return returnString;
	}
	
	/**
	 * 지갑 주소 생성
	 * @param String pwd
	 * @return String json
	 */
	public String PersonalNewAccount(String pwd) {
		String returnString = "";
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		JSONObject json = new JSONObject();
        json.put("jsonrpc", "1.0");
        json.put("id", "");
        json.put("method", "personal_newAccount");       
        
        JSONArray params = new JSONArray();
        params.add(pwd);        
        json.put("params", params);
        
        try {            
            StringEntity myEntity = new StringEntity(json.toJSONString());
            HttpPost httppost = new HttpPost("http://" + HOST + ":" + PORT);
            httppost.setHeader("Accept", "application/json");
            httppost.setHeader("Content-type", "application/json");
            httppost.setEntity(myEntity);
            
            HttpResponse response = httpclient.execute(httppost);
            
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            	HttpEntity entity = response.getEntity();
            	
            	returnString = EntityUtils.toString(entity);
            	
            	EntityUtils.consume(entity);
            }
            else {
            	returnString = CommonUtil.getHttpMessage(response);
            }
        } catch (Exception e) {        	
        	returnString = CommonUtil.getErrorMessage(e);
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        
		return returnString;
	}
	
	/**
	 * 현재 지갑 주소
	 * @return String
	 */
	public String EthCoinbase() {
		String returnString = "";
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		JSONObject json = new JSONObject();
        json.put("jsonrpc", "1.0");
        json.put("id", "");
        json.put("method", "eth_coinbase");       
        
        try {            
            StringEntity myEntity = new StringEntity(json.toJSONString());
            HttpPost httppost = new HttpPost("http://" + HOST + ":" + PORT);
            httppost.setHeader("Accept", "application/json");
            httppost.setHeader("Content-type", "application/json");
            httppost.setEntity(myEntity);
            
            HttpResponse response = httpclient.execute(httppost);
            
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            	HttpEntity entity = response.getEntity();
            	
            	returnString = EntityUtils.toString(entity);
            	
            	EntityUtils.consume(entity);
            }
            else {
            	returnString = CommonUtil.getHttpMessage(response);
            }
        } catch (Exception e) {        	
        	returnString = CommonUtil.getErrorMessage(e);
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        
		return returnString;
	}
	
	
	/**
	 * 현재 계정의 지갑 주소
	 * @return String
	 */
	public String PersonalListAccounts() {
		String returnString = "";
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		JSONObject json = new JSONObject();
        json.put("jsonrpc", "1.0");
        json.put("id", "");
        json.put("method", "personal_listAccounts");       
        
        try {            
            StringEntity myEntity = new StringEntity(json.toJSONString());
            HttpPost httppost = new HttpPost("http://" + HOST + ":" + PORT);
            httppost.setHeader("Accept", "application/json");
            httppost.setHeader("Content-type", "application/json");
            httppost.setEntity(myEntity);
            
            HttpResponse response = httpclient.execute(httppost);
            
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            	HttpEntity entity = response.getEntity();
            	
            	returnString = EntityUtils.toString(entity);
            	
            	EntityUtils.consume(entity);
            }
            else {
            	returnString = CommonUtil.getHttpMessage(response);
            }
        } catch (Exception e) {        	
        	returnString = CommonUtil.getErrorMessage(e);
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        
		return returnString;
	}
	
	/**
	 * 지갑 주소의 현재 금액
	 * @param String address
	 * @return String json
	 */
	public String GetBalanceByAddress(String address) {
		String URL = "https://api.etherscan.io/api";
		
		URIBuilder builder = new URIBuilder();
		//builder.setScheme("https").setHost("api.etherscan.io").setPort(443).setPath("/api")
		builder.setScheme("http").setHost("api-ropsten.etherscan.io").setPort(80).setPath("/api")		
		.setParameter("module", "account")
		.setParameter("action", "balance")
		.setParameter("address", address)
		.setParameter("tag", "latest")		
		.setParameter("apikey", APIKEYTOKEN);			
	
		String returnString = "";
		
		CloseableHttpClient httpclient = HttpClients.createDefault();	
        
        try {            
            HttpGet httpget = new HttpGet(builder.build());            
            HttpResponse response = httpclient.execute(httpget);
            
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            	HttpEntity entity = response.getEntity();
            	
            	returnString = EntityUtils.toString(entity);
            	
            	EntityUtils.consume(entity);
            }
            else {
            	returnString = CommonUtil.getHttpMessage(response);
            }
        } catch (Exception e) {        	
        	returnString = CommonUtil.getErrorMessage(e);
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        
		return returnString;
	}	
	
	/**
	 * 지갑주소의 트랜잭션 리스트
	 * @param String address
	 * @return String json
	 */
	public String GetTxListByAddress(String address) {
		String URL = "https://api.etherscan.io/api";
		
		URIBuilder builder = new URIBuilder();
		//builder.setScheme("https").setHost("api.etherscan.io").setPort(443).setPath("/api")
		builder.setScheme("http").setHost("api-ropsten.etherscan.io").setPort(80).setPath("/api")
		.setParameter("module", "account")
		.setParameter("action", "txlist")
		.setParameter("address", address)
		.setParameter("startblock", "0")		
		.setParameter("endblock", "99999999")
		.setParameter("sort", "asc")
		.setParameter("apikey", APIKEYTOKEN);			
	
		String returnString = "";
		
		CloseableHttpClient httpclient = HttpClients.createDefault();	
        
        try {            
            HttpGet httpget = new HttpGet(builder.build());            
            HttpResponse response = httpclient.execute(httpget);
            
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            	HttpEntity entity = response.getEntity();
            	
            	returnString = EntityUtils.toString(entity);
            	
            	EntityUtils.consume(entity);
            }
            else {
            	returnString = CommonUtil.getHttpMessage(response);
            }
        } catch (Exception e) {        	
        	returnString = CommonUtil.getErrorMessage(e);
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        
		return returnString;
	}
	
	public String PersonalUnlockAccount(String address, String pwd) {
		String returnString = "";
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		JSONObject json = new JSONObject();
        json.put("jsonrpc", "2.0");
        json.put("id", "");
        json.put("method", "personal_unlockAccount");   
                
      
        JSONArray params = new JSONArray();
        params.add(address);
        params.add(pwd);
        params.add(60);
               
        json.put("params", params);  
        
        System.out.println("json=" + json.toString());
        
        try {              	
            StringEntity myEntity = new StringEntity(json.toJSONString());
            HttpPost httppost = new HttpPost("http://" + HOST + ":" + PORT);
            httppost.setHeader("Accept", "application/json");
            httppost.setHeader("Content-type", "application/json");
            httppost.setEntity(myEntity);
            
            HttpResponse response = httpclient.execute(httppost);
            
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            	HttpEntity entity = response.getEntity();
            	
            	returnString = EntityUtils.toString(entity);
            	
            	EntityUtils.consume(entity);
            }
            else {
            	returnString = CommonUtil.getHttpMessage(response);
            }
        } catch (Exception e) {        	
        	returnString = CommonUtil.getErrorMessage(e);
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        
		return returnString;
	}
	
	//eth_estimateGas
	//gasPrice
	
	public String EthEstimateGas(String sender, String recver, String coin) {
		String returnString = "";
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		JSONObject json = new JSONObject();
        json.put("jsonrpc", "2.0");
        json.put("id", "");
        json.put("method", "eth_estimateGas");     
                
        JSONObject obj = new JSONObject();
        obj.put("from", sender);
        obj.put("to", recver);
        //obj.put("data", "0x2386f26fc10000");
        
        JSONArray params = new JSONArray();
        params.add(obj);
        //params.add(pwd);
               
        json.put("params", params);  
        
        System.out.println("json=" + json.toString());
        
        try {              	
            StringEntity myEntity = new StringEntity(json.toJSONString());
            HttpPost httppost = new HttpPost("http://" + HOST + ":" + PORT);
            httppost.setHeader("Accept", "application/json");
            httppost.setHeader("Content-type", "application/json");
            httppost.setEntity(myEntity);
            
            HttpResponse response = httpclient.execute(httppost);
            
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            	HttpEntity entity = response.getEntity();
            	
            	returnString = EntityUtils.toString(entity);
            	
            	EntityUtils.consume(entity);
            }
            else {
            	returnString = CommonUtil.getHttpMessage(response);
            }
        } catch (Exception e) {        	
        	returnString = CommonUtil.getErrorMessage(e);
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        
		return returnString;
	}
	
	
	public String SendEth(String sender, String recver, String coin, String pwd) {
		String returnString = "";
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		JSONObject json = new JSONObject();
        json.put("jsonrpc", "2.0");
        json.put("id", "");
        json.put("method", "eth_sendTransaction");     
                
        JSONObject obj = new JSONObject();
        obj.put("from", sender);
        obj.put("to", recver);
        //obj.put("data", "0xd46e8dd67c5d32be8d46e8dd67c5d32be8058bb8eb970870f072445675058bb8eb970870f072445675");
        obj.put("value", "0x1152921504606847000");
       
        //obj.put("gas", ToHex("9000"));
       // obj.put("gasPrice", ToHex("25000000000"));
        //obj.put("gasLimit", ToHex("1225000000000000"));
        
        
        
        JSONArray params = new JSONArray();
        params.add(obj);
        //params.add(pwd);
               
        json.put("params", params);  
        
        System.out.println("json=" + json.toString());
        
        try {              	
            StringEntity myEntity = new StringEntity(json.toJSONString());
            HttpPost httppost = new HttpPost("http://" + HOST + ":" + PORT);
            httppost.setHeader("Accept", "application/json");
            httppost.setHeader("Content-type", "application/json");
            httppost.setEntity(myEntity);
            
            HttpResponse response = httpclient.execute(httppost);
            
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            	HttpEntity entity = response.getEntity();
            	
            	returnString = EntityUtils.toString(entity);
            	
            	EntityUtils.consume(entity);
            }
            else {
            	returnString = CommonUtil.getHttpMessage(response);
            }
        } catch (Exception e) {        	
        	returnString = CommonUtil.getErrorMessage(e);
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        
		return returnString;
	}
	
	public String ToHex(String value) {
		BigDecimal data = new BigDecimal(value);
		String hex = "0x" + data.toBigInteger().toString(16);
		
		return hex;
	}
	
	public String CoinToHex18(String coin) {
		BigDecimal coin18 = new BigDecimal(coin).multiply(new BigDecimal("100000000000000000"));
		String hex = "0x" + coin18.toBigInteger().toString(16);
		
		return hex;
	}
}
