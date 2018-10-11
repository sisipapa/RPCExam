package common;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.json.simple.JSONObject;

public class CommonUtil {
	/**
	 * HttpStatus.SC_OK 이외의 상태시 에러
	 * @param HttpResponse response
	 * @return String json
	 */	
	public static String getHttpMessage(HttpResponse response) {
		JSONObject result = new JSONObject();
    	result.put("result", "fail");
    	result.put("message", response.getStatusLine().toString());
    	
    	return result.toString();
	}
	
	/**
	 * 에러 메세지
	 * @param Exception e
	 * @return String json
	 */
	public static String getErrorMessage(Exception e) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintStream pinrtStream = new PrintStream(out);
		e.printStackTrace(pinrtStream);		  
		String stackTraceString = out.toString(); // 찍은 값을 가져오고.
		  
		JSONObject result = new JSONObject();
    	result.put("result", "fail");
    	result.put("message", e.getMessage());
    	result.put("tracestring", out.toString());
    	
    	return result.toString();
	}
	
	
	/**
	 * timestamp 값을 날짜 형식의 문자열로 변경
	 * @param long timestamp 
	 * @return String date
	 */
	public static String getTimestampToDate(long timestamp) {
		Timestamp stamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
		Date date = new Date(stamp.getTime());
		
		SimpleDateFormat sdf = new SimpleDateFormat(); 
		sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
				
		String sDate = sdf.format(date);
		return sDate;
	}
}
