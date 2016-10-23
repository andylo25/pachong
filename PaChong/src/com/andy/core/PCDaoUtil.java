package com.andy.core;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang.ArrayUtils;

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
	
	public static void saveMou(List<DataVO> dataVOs) {
		Connection conn = DBUtil.getConn();
        try {
        	conn.setAutoCommit(false);
        	for(DataVO vo:dataVOs){
        		QueryRunner runner=new QueryRunner(); 
        		String sql="insert into mou_res (`name`,`torrent`,`desc`,`metadata`) values(?,?,?,?)"; 
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
	
	public static void saveMou(DataVO vo) {
		Connection conn = DBUtil.getConn();
		try {
			conn.setAutoCommit(false);
			
			QueryRunner runner=new QueryRunner(); 
			String sql="insert into mou_res (`name`,`torrent`,`desc`,`metadata`) values(?,?,?,?)"; 
			SerialBlob blob = null;
			if(vo.getTorrent() != null){
				blob=new SerialBlob(vo.getTorrent());
			}
			Long id = runner.insert(conn,sql,new ScalarHandler<Long>(1),vo.getName(),blob,vo.getDurl(),vo.getMetadata());
			vo.setId(id.intValue());
			
			conn.commit();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			DBUtil.close(conn);
		}
	}
	
	public static void updateMou(List<DataVO> dataVOs){
		if(dataVOs == null || dataVOs.isEmpty())return;
		Connection conn = DBUtil.getConn();
        try {
        	conn.setAutoCommit(false);
        	for(DataVO vo:dataVOs){
        		QueryRunner runner=new QueryRunner(); 
        		String sql="update mou_res set torrent=? where id=?"; 
        		SerialBlob blob = null;
        		if(vo.getTorrent() != null){
        			blob=new SerialBlob(vo.getTorrent());
        		}
        		runner.update(conn,sql,vo.getName(),blob,vo.getId());
        	}
        	conn.commit();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			DBUtil.close(conn);
		}
	}
	
	public static void saveBttt(DataVO vo) {
		Connection conn = DBUtil.getConn();
		try {
			conn.setAutoCommit(false);
			
			QueryRunner runner=new QueryRunner(); 
			String sql="insert into bttt_res (`name`,`desc`,`info`,`star`,`torrent0`,`torrent1`,`torrent2`,`torrent3`,`torrent4`,`torrent5`,"
					+ "`torrent6`,`torrent7`,`torrent8`,`torrent9`,`url0`,`url1`,`url2`,`url3`,`url4`,`url5`,`url6`,`url7`,`url8`,`url9`) "
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			Object[] params = new Object[24];
			params[0] = vo.getName();
			params[1] = vo.getDesc();
			params[2] = vo.getMetadata();
			params[3] = vo.getRate();
			for(int i=0;i<10;i++){
				SerialBlob blob = null;
				if(vo.getTorrents().size() > i){
					Byte[] tor = vo.getTorrents().get(i);
					blob=new SerialBlob(ArrayUtils.toPrimitive(tor));
				}
				params[i+4] = blob;
			}
			for(int i=0;i<10;i++){
				if(vo.getDurls().size() > i){
					params[i+14] = vo.getDurls().get(i);
				}else{
					params[i+14] = null;
				}
			}
			Long id = runner.insert(conn,sql,new ScalarHandler<Long>(1),params);
			vo.setId(id.intValue());
			
			conn.commit();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			DBUtil.close(conn);
		}
	}
	
}
