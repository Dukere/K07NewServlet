package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

public class myfileDAO {

	Connection con;
	PreparedStatement psmt;
	ResultSet rs;
	
	public myfileDAO(ServletContext ctx) {
		try {
			Class.forName(ctx.getInitParameter("JDBCDriver"));
			String id = "kosmo";
			String pw = "1234";
			con =DriverManager.getConnection(
					ctx.getInitParameter("ConnectionURL"), id, pw);
			System.out.println("DB연결성공");
		}
		catch (Exception e) {
			System.out.println("DB연결 실패");
			e.printStackTrace();
		}
	}
	
	public myfileDAO() {
		try {
			Context initCtx = new InitialContext();

			Context ctx = (Context)initCtx.lookup("java:comp/env");
			
			DataSource source = (DataSource)ctx.lookup("jdbc/myoracle");
			
			con = source.getConnection();
			
			System.out.println("DBCP 연결 성공");
		}
		catch (Exception e) {
			System.out.println("DBCP 연결 실패");
			e.printStackTrace();
		}
	}
	
	public int myfileInsert(myfileDTO dto) {
		int affected = 0;
		try {
			String query = "INSERT INTO myfile( "
					+" idx, name, title, inter, ofile, sfile) "
					+" VALUES ( "
					+" SEQ_BBS_NUM.nextval, ?, ?, ?, ?, ?)";
			
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getName());
			psmt.setString(2, dto.getTitle());
			psmt.setNString(3, dto.getInter());
			psmt.setString(4, dto.getOfile());
			psmt.setString(5, dto.getSfile());
			
			affected = psmt.executeUpdate();
		}
		catch(Exception e) {
			System.out.println("insert 중 예외 발생");
			e.printStackTrace();
		}
		return affected;
	}
	
	public List<myfileDTO> myfileList(){
		
		List<myfileDTO> fileList = new Vector<myfileDTO>();
		
		String query = "select * from myfile "
				+ " where 1=1 "
				+ " order by idx desc " ;
		System.out.println("query=" + query);
		
		try {
			psmt = con.prepareStatement(query);
			rs= psmt.executeQuery();
			while(rs.next()) {
				myfileDTO dto = new myfileDTO();
				
				dto.setIdx(rs.getString(1));
				dto.setName(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setInter(rs.getString(4));
				dto.setOfile(rs.getString(5));
				dto.setSfile(rs.getString(6));
				dto.setPostdate(rs.getString(7));
				
				fileList.add(dto);
			}
		}
		catch(Exception e) {
			System.out.println("Select 시 예외발생");
			e.printStackTrace();
		}
		return fileList;
	}
	
	public void close() {
		try {
			if(rs != null) rs.close();
			if(psmt != null) psmt.close();
			if(con != null) con.close();
		} 
		catch (Exception e) {
			System.out.println("자원반납시 예외발생");
			e.printStackTrace();
		}
	}
}
