package com.andy.core;

import java.sql.Connection;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.andy.core.db.DBUtil;
import com.andy.pc.vo.DataVO;

public class PCDaoUtil {

	
	public static void saveHdwan(List<DataVO> dataVOs) {
		Connection conn = DBUtil.getConn();
        try {
        	conn.setAutoCommit(false);
        	for(DataVO vo:dataVOs){
        		QueryRunner runner=new QueryRunner(); 
        		String sql="insert into hdwan_res (`name`,`torrent`,`desc`,`metadata`) values(?,?,?,?)"; 
        		SerialBlob blob = null;
        		if(vo.getTorrent() != null){
        			blob=new SerialBlob(vo.getTorrent());
        		}
        		Long id = runner.insert(conn,sql,new ScalarHandler<Long>(1),vo.getName(),blob,vo.getDurl(),vo.getMetadata());
        		vo.setId(id.intValue());
        	}
        	conn.commit();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			DBUtil.close(conn);
		}
	}
	
	public static void saveDouban(List<DataVO> dataVOs) {
		Connection conn = DBUtil.getConn();
		try {
			conn.setAutoCommit(false);
			for(DataVO vo:dataVOs){
				QueryRunner runner=new QueryRunner(); 
				String sql="insert into douban_res (`name`,`star`,`url`,`subject`,`desc`,`info`) values(?,?,?,?,?,?)"; 
				Long id = runner.insert(conn,sql,new ScalarHandler<Long>(1),vo.getName(),vo.getRate(),vo.getUrl(),vo.getSubject(),vo.getDesc(),vo.getMetadata());
				vo.setId(id.intValue());
			}
			conn.commit();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			DBUtil.close(conn);
		}
	}
}
