package net.sinodata.business.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtil {
	private static CharacterIterator it;  
    private static char              c;  
    private static int               col;  
	public static JSONArray formatRsToJsonArray(ResultSet rs)throws Exception{
		ResultSetMetaData md=rs.getMetaData();
		int num=md.getColumnCount();
		JSONArray array=new JSONArray();
		while(rs.next()){
			JSONObject mapOfColValues=new JSONObject();
			for(int i=1;i<=num;i++){
				Object o=rs.getObject(i);
				if(o instanceof Date){
					mapOfColValues.put(md.getColumnName(i), DateUtil.formatDate((Date)o, "yyyy-MM-dd"));
				}else{
					mapOfColValues.put(md.getColumnName(i), rs.getObject(i));					
				}
			}
			array.add(mapOfColValues);
		}
		return array;
	}
	public static JSONArray formatRsToJsonArray(SearchResult resoult)throws Exception{
		List<?> list= resoult.getRows();
		JSONArray Array=new JSONArray();
		JSONObject JSONObject=new JSONObject();
		for(Object o:list){
			JSONArray jsonArray=new JSONArray();
			jsonArray =JSONArray .fromObject(o);
			
			for(int i=0;i<jsonArray.size();i++){
			
				JSONObject J=	jsonArray.getJSONObject(i);
				 Iterator it = J.keys(); 
				    while (it.hasNext()) {  
		                String key = (String) it.next();  
		                String value = J.getString(key);  
		            	JSONObject.put(key, value);
				    }
			
			
				 
				
			}
			Array.add(JSONObject);
			
		}
		
		return Array;
	}
	
	
	
	  /** 
     * 验证一个字符串是否是合法的JSON串 
     *  
     * @param input 要验证的字符串 
	 * @return 
     * @return true-合法 ，false-非法 
     */  
    public static boolean validate(String input) {  
        input = input.trim();  
        boolean ret = valid(input);  
        return ret;  
    }  
  
    private static boolean valid(String input) {  
        if ("".equals(input)) return true;  
  
        boolean ret = true;  
        it = new StringCharacterIterator(input);  
        c = it.first();  
        col = 1;  
        if (!value()) {  
            ret = error("value", 1);  
        } else {  
            skipWhiteSpace();  
            if (c != CharacterIterator.DONE) {  
                ret = error("end", col);  
            }  
        }  
  
        return ret;  
    }  
  
    private static boolean value() {  
        return literal("true") || literal("false") || literal("null") || string() || number() || object() || array();  
    }  
  
    private static boolean literal(String text) {  
        CharacterIterator ci = new StringCharacterIterator(text);  
        char t = ci.first();  
        if (c != t) return false;  
  
        int start = col;  
        boolean ret = true;  
        for (t = ci.next(); t != CharacterIterator.DONE; t = ci.next()) {  
            if (t != nextCharacter()) {  
                ret = false;  
                break;  
            }  
        }  
        nextCharacter();  
        if (!ret) error("literal " + text, start);  
        return ret;  
    }  
  
    private static boolean array() {  
        return aggregate('[', ']', false);  
    }  
  
    private static boolean object() {  
        return aggregate('{', '}', true);  
    }  
  
    private static boolean aggregate(char entryCharacter, char exitCharacter, boolean prefix) {  
        if (c != entryCharacter) return false;  
        nextCharacter();  
        skipWhiteSpace();  
        if (c == exitCharacter) {  
            nextCharacter();  
            return true;  
        }  
  
        for (;;) {  
            if (prefix) {  
                int start = col;  
                if (!string()) return error("string", start);  
                skipWhiteSpace();  
                if (c != ':') return error("colon", col);  
                nextCharacter();  
                skipWhiteSpace();  
            }  
            if (value()) {  
                skipWhiteSpace();  
                if (c == ',') {  
                    nextCharacter();  
                } else if (c == exitCharacter) {  
                    break;  
                } else {  
                    return error("comma or " + exitCharacter, col);  
                }  
            } else {  
                return error("value", col);  
            }  
            skipWhiteSpace();  
        }  
  
        nextCharacter();  
        return true;  
    }  
  
    private static boolean number() {  
        if (!Character.isDigit(c) && c != '-') return false;  
        int start = col;  
        if (c == '-') nextCharacter();  
        if (c == '0') {  
            nextCharacter();  
        } else if (Character.isDigit(c)) {  
            while (Character.isDigit(c))  
                nextCharacter();  
        } else {  
            return error("number", start);  
        }  
        if (c == '.') {  
            nextCharacter();  
            if (Character.isDigit(c)) {  
                while (Character.isDigit(c))  
                    nextCharacter();  
            } else {  
                return error("number", start);  
            }  
        }  
        if (c == 'e' || c == 'E') {  
            nextCharacter();  
            if (c == '+' || c == '-') {  
                nextCharacter();  
            }  
            if (Character.isDigit(c)) {  
                while (Character.isDigit(c))  
                    nextCharacter();  
            } else {  
                return error("number", start);  
            }  
        }  
        return true;  
    }  
  
    private static boolean string() {  
        if (c != '"') return false;  
  
        int start = col;  
        boolean escaped = false;  
        for (nextCharacter(); c != CharacterIterator.DONE; nextCharacter()) {  
            if (!escaped && c == '\\') {  
                escaped = true;  
            } else if (escaped) {  
                if (!escape()) {  
                    return false;  
                }  
                escaped = false;  
            } else if (c == '"') {  
                nextCharacter();  
                return true;  
            }  
        }  
        return error("quoted string", start);  
    }  
  
    private static boolean escape() {  
        int start = col - 1;  
        if (" \\\"/bfnrtu".indexOf(c) < 0) {  
            return error("escape sequence  \\\",\\\\,\\/,\\b,\\f,\\n,\\r,\\t  or  \\uxxxx ", start);  
        }  
        if (c == 'u') {  
            if (!ishex(nextCharacter()) || !ishex(nextCharacter()) || !ishex(nextCharacter())  
                || !ishex(nextCharacter())) {  
                return error("unicode escape sequence  \\uxxxx ", start);  
            }  
        }  
        return true;  
    }  
  
    private static boolean ishex(char d) {  
        return "0123456789abcdefABCDEF".indexOf(c) >= 0;  
    }  
  
    private static char nextCharacter() {  
        c = it.next();  
        ++col;  
        return c;  
    }  
  
    private static void skipWhiteSpace() {  
        while (Character.isWhitespace(c)) {  
            nextCharacter();  
        }  
    }  
  
    private static boolean error(String type, int col) {  
         System.out.printf("type: %s, col: %s%s", type, col, System.getProperty("line.separator"));  
        return false;  
    } 
    public static void main(String[] args){  
    	/*String jsonParam="{"
	        	  +"\"FWQQZ_ZCXX\": \"实有人口采集系统\","
	        	  +"\"FWBS\": \"S00111009120000000001\","
	        	  +"\"FWQQ_RQSJ\": \"20170629170707\","
	        	  +"\"FWQQ_NR\":{\"method\":\"GetHouseInfo\",\"params\":{\"houseID\":\"10000000\",\"sfzh\":\"13105619890224\"}},"
	        	  +"\"XXCZRY_XM\": \"王五\","
	        	  +"\"XXCZRY_GMSFHM\": \"130579198607244513\","
	        	  +"\"XXCZRY_GAJGJGDM\": \"110102020000\","
	        	  +"\"FWQQSB_BH\": \"866620054421684\""
	        	  +"}";*/
    	
    	String jsonParam="{"
    	  		  +"\"FWQQ_BWBH\":\"SR020001011052201709191541010000001\","
    	  		  +"\"BWLYIPDZ\":\"192.168.0.1\","
    	  		  +"\"BWLYDKH\":\"80\","
    	  		  +"\"FWQQZ_ZCXX\": \"实有人口采集系统\","
    	  		  +"\"FWBS\": \"S00111009120000000001\","
    	  		  +"\"FFBS\": \"GetHouseInfo\","
    	  		  +"\"FWQQ_RQSJ\": \"20170629170707\","
    	  		  +"\"FWQQ_NR\":{\"method\":\"GetHouseInfo\",\"params\":{\"houseID\":\"10000000\",\"sfzh\":\"13105619890224\"}},"
    	  		  +"\"XXCZRY_XM\": \"王五\","
    	  		  +"\"XXCZRY_GMSFHM\": \"130579198607244513\","
    	  		  +"\"XXCZRY_GAJGJGDM\": \"110102020000\","
    	  		  +"\"FWQQSB_BH\": \"866620054421684\","
    	  		  +"\"FWQQ_SJSJLX\":\"service_request\""
    	          +"}";
        String jsonStr = "{\"website\":\"oschina.net\"}";  
        //System.out.println(jsonStr+":"+JsonUtil.validate(jsonParam));  
        //System.out.println(System.getProperty("user.dir"));
		//System.out.println(System.getProperty("user.home"));
    }  
}
