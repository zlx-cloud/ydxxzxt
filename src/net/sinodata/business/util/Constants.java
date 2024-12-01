package net.sinodata.business.util;

public class Constants {
	public static String APP_VERSION = "1.1";
	public static String APP_CONFIG = "appConfig.xml";
	
    
    public static final String Stirng_File_Record = "@@";
    public static final String Stirng_File_Prmity = "#";
    public static final String Stirng_File_CheckData = ":";
    public static final String Stirng_File_Spilt = ",";
    public static final String Stirng_File_spXX = "\\|";
    public static final String String_File_Comp=" ";
    public static final String String_File_Tab="	";
    public static final String String_File_Tab2="		";
    public static final String String_File_spXXX="|";
    
    
    
    public static final String String_NoCheckData="007";     //主键值不存在
    public static final String String_NoService="200";       //服务不存在
    public static final String String_SqlError="201";        //SQL语句执行错误,参数个数不匹配或参数类型不匹配
    public static final String String_CheckSuccess ="002"  ; //核查成功
    public static final String String_CheckFail ="003"  ;    //核查失败
    public static final String String_OtherException ="500"; //其他异常
    public static final String String_ServiceNull ="006";    //报文格式不正确（第一列应为服务标识,以下列格式为： 主键|核查字段1|核查字段2|返回列1|返回列2）
    
    public static final String xml_Exception_0061 ="0061";   //无服务标识属性无批次号属性
    public static final String xml_Exception_0062 ="0062";   //无文件名属性
    public static final String xml_Exception_0063 ="0063";   //无批次号属性
    public static final String xml_Exception_0064 ="0064";   //报文正确
    public static final String xml_Exception_0065 ="0065";   //xml格式不正确
    public static final String record_Exception_0067 ="0067"; //数据行格式错误,应该以Row开头
    public static final String data_pattern_0068="0068";      //数据格式不正确 数据之间应该已“|”分割,以下列格式为： 主键|核查字段1|核查字段2|返回列1|返回列2
    public static final String data_pattern_008="008";        // 非身份证
    
    public static final String Static_Var0 ="0";              // 核查错误
    public static final String Static_Var1 ="1";              // 核查正确
    
    public static final String Execute_Result_er="SUCCESS";  // 用于日志记录状态
    public static final String Execute_Result_fl="FAIL";     // 用于日志记录状态
    
 // Log  当有字段为空时用 '-'代替
    public static final String Ret_Log_Char_Rep="-";
    public static final String Create_Session_Seq="seq_service_id";
    public static final String Create_Detail_Seq="seq_detail_id";
    
    public static final String Execute_FILE=".FAIL";     // 用于日志记录状态
    public static final String  record_Start_Row ="<Row>";  
    public static final String  record_End_Row ="</Row>";  
    
    
    // 数据库分区名称
    public static final String  Partition_Name="PART_";
    public static final String  Match_Where="WHERE";
    public static final String  Match_SFZHM="CZRKGMSFHM";
    
    // 查询照片SQL语句
    public static final String QUERY_POTO_SQL="SELECT T.CZRKXP FROM RKK_USER.LOAD_SUCC_JBXXTP T WHERE T.TPID=?";
    // 代码转换SQL语句
    public static final String CODE_TRANSF_SQL="";
}
