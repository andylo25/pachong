package com.qipai.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Table;
import com.jfinal.plugin.activerecord.TableMapping;
import com.jfinal.plugin.activerecord.dialect.Dialect;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.dialect.OracleDialect;

public class DbUtil {

	private static Dialect dialect = new MysqlDialect();
	public static Table getTable(Class<? extends Model> clasz){
		return TableMapping.me().getTable(getUsefulClass(clasz));
	}

	private static Class<? extends Model> getUsefulClass(Class<? extends Model> clasz) {
		return (Class<? extends Model>) (clasz.getName().indexOf("EnhancerByCGLIB") == -1 ? clasz : clasz.getSuperclass());	// com.demo.blog.Blog$$EnhancerByCGLIB$$69a17158
	}
	
	private static Map<Class<? extends Model>,Map<String,String>> colMap = new HashMap<Class<? extends Model>,Map<String,String>>();
	
	public static String getColName(Class<? extends Model> clasz,String attr){
		synchronized (colMap) {
			Map<String, String> map = colMap.get(clasz);
			if(map == null){
				map = new HashMap<String, String>();
				Table table = getTable(clasz);
				Set<String> set = table.getColumnTypeMap().keySet();
				for(String col:set){
					map.put(buildAttrName(col),col);
				}
				colMap.put(clasz, map);
			}
			return map.get(attr);
		}
	}
	
	public static String buildAttrName(String colName) {
		if (dialect instanceof OracleDialect) {
			colName = colName.toLowerCase();
		}
		return StrKit.toCamelCase(colName);
	}
}
