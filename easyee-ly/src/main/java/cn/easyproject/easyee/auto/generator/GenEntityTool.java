package cn.easyproject.easyee.auto.generator;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class GenEntityTool {
	private String[] colNames;//列名数组
	private String[] colType;//列名类型数组
	private int[] colSize;//列名大小数组
	private boolean f_util = false;//是否需要导入java.util.*
	private boolean f_sql = false;//是否需要导入java.sql.*
	String basePath="";
	public GenEntityTool(){}
	public GenEntityTool(String tabName,String packPath){
	    tabName = tabName.toUpperCase();
	    String basePath = System.getProperty("user.dir");
		Connection conn = null;
		String sql = "select * from " + tabName+" where 1=2";
		try {
			conn = DBUtils.getConnection();
			 
			PreparedStatement  pstm = conn.prepareStatement(sql);
		     ResultSet rs2 = pstm.executeQuery();
		    ResultSetMetaData rsmd = pstm.getResultSet().getMetaData();//或者 rs2.ge
		   
			int size = rsmd.getColumnCount();//共有多少列
			colNames = new String[size];
			colType = new String[size];
			colSize = new int[size];
			for(int i=0;i<rsmd.getColumnCount();i++){
				colNames[i] = rsmd.getColumnName(i+1).toLowerCase();
				colType[i] = rsmd.getColumnTypeName(i+1);
				if(colType[i].equalsIgnoreCase("date")){
					f_util = true;
				}
				if(colType[i].equalsIgnoreCase("text") || colType[i].equalsIgnoreCase("image")){
					f_sql = true;
				}
				colSize[i] = rsmd.getColumnDisplaySize(i+1);
			}
			String content = parse(tabName, packPath,colNames, colType, colSize);
			String packagePath = packPath.replace('.', '/');
	       
	        String target = basePath + "/src/main/java/" + packagePath;
			
			
			FileWriter fw = new FileWriter(target+"/"+initCap(tabName.toLowerCase())+".java");
			PrintWriter pw = new PrintWriter(fw);
			pw.println(content);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtils.close(conn);
		}
	}
	
	/*
	 * 解析处理，生成java实体类主体代码
	 */
	private String parse(String tabName,String packPath,String[] colNames,String[] colType,int[] colSize){
		StringBuffer sb = new StringBuffer();
		  sb.append("package "+packPath+";\r\n");
		if(f_util){
			sb.append("import java.util.Date;\r\n");
		}
		if(f_sql){
			sb.append("import java.sql.*;\r\n\r\n\r\n");
		}
		sb.append("import java.io.Serializable;\r\n\r\n\r\n");
		sb.append("import cn.easyproject.easyee.auto.EasyField;\r\n\r\n\r\n");
		sb.append("import cn.easyproject.easyee.auto.EasyModule;\r\n\r\n\r\n");
		sb.append("import cn.easyproject.easyee.auto.EasyPage;\r\n\r\n\r\n");
		
		sb.append("@EasyModule(label =\" \" , mybatisTable =\""+tabName+"\") \r\n@EasyPage \r\n public class "+initCap(tabName.toLowerCase())+" implements Serializable {\r\n");
		processAllAttrs(sb);
		processAllMethod(sb);
		sb.append("}\r\n");
		return sb.toString();
	}
	
	/*
	 * 生成所有的方法
	 */
	private void processAllMethod(StringBuffer sb){
		for(int i=0;i<colNames.length;i++){
			sb.append("\r\n \tpublic void set"+initCap(colNames[i])+"("+sqlType2JavaType(colType[i])+" "+colNames[i]+"){\r\n");
			sb.append("\t\tthis."+colNames[i]+" = "+colNames[i]+";\r\n");
			sb.append("\t}\r\n");
			
			sb.append("\tpublic "+sqlType2JavaType(colType[i])+" get"+initCap(colNames[i])+"(){\r\n");
			sb.append("\t\treturn "+colNames[i]+";\r\n");
			sb.append("\t}\r\n");
		}
	}
	
	/*
	 * 解析输出属性
	 * 
	 * @return
	 */
	private void processAllAttrs(StringBuffer sb){
		for(int i=0;i<colNames.length;i++){
			sb.append("\r\n \r\n \t @EasyField(label = \"\")\r\n \t private "+sqlType2JavaType(colType[i])+" "+colNames[i]+";\r\n  \t");
		}
	}
	
	/*
	 * 把输入字符串的首字母变成大写
	 * 
	 * @param str
	 * @return
	 * 
	 */
	private String initCap(String str){
		char[] ch = str.toCharArray();
		if(ch[0] >= 'a' && ch[0] <= 'z'){
			ch[0] = (char) (ch[0] - 32);
		}
		return new String(ch);
	}
	
	private String sqlType2JavaType(String sqlType) {  
		if (sqlType.equalsIgnoreCase("NUMBER")) {
			return "Double";
		} else if (sqlType.equalsIgnoreCase("CHAR")||sqlType.equalsIgnoreCase("VARCHAR")||sqlType.equalsIgnoreCase("VARCHAR2")) {
			return "String";
		} else if (sqlType.equalsIgnoreCase("DATE")) {
			return "Date";
		} 
		return null;
	}  
	
	
	
	
	public static void main(String[] args){
	 
        
 
	    
		  new GenEntityTool("SYS_DICT","cn.easyproject.easyee.auto");
		
	}
}