package bitcoin;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import common.CommonUtil;
import common.Environment;

@SuppressWarnings("deprecation")
public class RPCBitcoin {
	private String host;
	private int port;
	private String rpcuser;
	private String rpcpassword;
	
	/**
	 * 기본생성
	 */
	public RPCBitcoin() {
		this.host = Environment.BIT_RPC_HOST;
		this.port = Integer.parseInt(Environment.BIT_RPC_PORT);
		this.rpcuser = Environment.BIT_RPC_USER;
		this.rpcpassword = Environment.BIT_RPC_PASSWORD;
	}

	public DefaultHttpClient GetHTTPClient() {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient.getCredentialsProvider().setCredentials(new AuthScope(host, port), new UsernamePasswordCredentials(rpcuser, rpcpassword));		
		return httpclient;
	}
	
	/**
	 * 지갑정보
	 * @return String
	 */
	public String GetWalletInfo() {
		String returnString = "";
		
		DefaultHttpClient httpclient = GetHTTPClient();
		
		JSONObject json = new JSONObject();
        json.put("jsonrpc", "1.0");
        json.put("id", rpcuser);
        json.put("method", "getwalletinfo");
        
        try {            
            StringEntity myEntity = new StringEntity(json.toJSONString());
            HttpPost httppost = new HttpPost("http://" + host + ":" + port);
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
	 * Label별 지갑 address 생성
	 * @param label
	 * @return String
	 */
	public String GetNewAddress(String label) {
		String returnString = "";
		
		DefaultHttpClient httpclient = GetHTTPClient();
		
		JSONObject json = new JSONObject();
        json.put("jsonrpc", "1.0");
        json.put("id", rpcuser);
        json.put("method", "getnewaddress");
        JSONArray params = new JSONArray();
        params.add(label);
        json.put("params", params);
        
        try {            
            StringEntity myEntity = new StringEntity(json.toJSONString());
            HttpPost httppost = new HttpPost("http://" + host + ":" + port);
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
	 * Label별 address 목록
	 * @param label
	 * @return String
	 */
	public String GetAllAddress(String label) {
		String returnString = "";
		
		DefaultHttpClient httpclient = GetHTTPClient();
		
		JSONObject json = new JSONObject();
        json.put("jsonrpc", "1.0");
        json.put("id", rpcuser);
        json.put("method", "getaddressesbyaccount");
        JSONArray params = new JSONArray();
        params.add(label);
        json.put("params", params);
        
        try {            
            StringEntity myEntity = new StringEntity(json.toJSONString());
            HttpPost httppost = new HttpPost("http://" + host + ":" + port);
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
	 * account별 받은 금액
	 * @param label
	 * @return String
	 */
	public String GetReceivedByAccount(String label) {
		String returnString = "";
		
		DefaultHttpClient httpclient = GetHTTPClient();
		
		JSONObject json = new JSONObject();
        json.put("jsonrpc", "1.0");
        json.put("id", rpcuser);
        json.put("method", "getreceivedbyaccount");
      
        JSONArray params = new JSONArray();
        params.add(label);   
        params.add(1);       
        json.put("params", params);  
        
        try {            
            StringEntity myEntity = new StringEntity(json.toJSONString());
            HttpPost httppost = new HttpPost("http://" + host + ":" + port);
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
	 * 계정별 트랜잭션 리스트
	 * @param label
	 * @return String
	 */
	public String ListTransactions(String label) {
		String returnString = "";
		
		DefaultHttpClient httpclient = GetHTTPClient();
		
		JSONObject json = new JSONObject();
        json.put("jsonrpc", "1.0");
        json.put("id", rpcuser);
        json.put("method", "listtransactions");
      
        JSONArray params = new JSONArray();
        params.add(label);   
        params.add(100);
        params.add(0);
        json.put("params", params);  
        
        try {            
            StringEntity myEntity = new StringEntity(json.toJSONString());
            HttpPost httppost = new HttpPost("http://" + host + ":" + port);
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
}
