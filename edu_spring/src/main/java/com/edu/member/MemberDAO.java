package com.edu.member;

import java.sql.*;

import org.springframework.stereotype.Repository;


@Repository
public class MemberDAO {

	public Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("org.h2.Driver");
			con = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public void memberInsert(Member member) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = this.getConnection();
			pstmt = con.prepareStatement("insert into member values(?,?,?,?)");
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPasswd());
			pstmt.setString(3, member.getName());
			pstmt.setString(4, member.getMail());
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				pstmt.close();
			} catch (Exception e) {
			}
		}
	}

}
