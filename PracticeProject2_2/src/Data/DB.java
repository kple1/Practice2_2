package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DB {
	static Connection conn;
	static Statement st;

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/?allowLoadLocalInfile=true", "root", "1234");
			st = conn.createStatement();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static String lastlisten(String userId, int musicNum) {
		String save = "";
		try {
			st.executeUpdate("USE oldpopsong;");
			String q = "SELECT l.stoptime FROM listenlist l "
					+ "JOIN playlist p ON l.p_no = p.p_no AND l.play_ox = 0 "
					+ "JOIN user u ON p.u_no = u.u_no AND u.id = '" + userId + "' "
					+ "JOIN music m ON p.m_no = m.m_no AND m.m_no = " + musicNum + ";";

			ResultSet rs = st.executeQuery(q);
			if (rs.next()) save = rs.getString(1); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return save;
	}

	public static boolean loginEquals(String pw, String id) {
		boolean save = false;
		String q = "SELECT COUNT(*) FROM oldpopsong.user WHERE pw = ? AND id = ?";
		try {
			PreparedStatement pstm = conn.prepareStatement(q);
			pstm.setString(1, pw);
			pstm.setString(2, id);
			ResultSet rs = pstm.executeQuery();
			if (rs.next())
				save = rs.getBoolean(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return save;
	}

	public static List<byte[]> getAllImage() {
		List<byte[]> list = new ArrayList<>();
		String q = "SELECT m_img FROM oldpopsong.music";
		try {
			ResultSet rs = st.executeQuery(q);
			while (rs.next()) {
				list.add(rs.getBytes(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static void insertPlayList(String u_no, String m_no) {
		String q = "INSERT INTO oldpopsong.playlist (u_no, m_no) VALUES (?, ?)";
		try {
			PreparedStatement pstm = conn.prepareStatement(q);
			pstm.setString(1, u_no);
			pstm.setString(2, m_no);
			pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static String getString(String wantCol, String table, String getCol, String where) {
		String save = "";
		String q = "SELECT " + wantCol + " FROM oldpopsong." + table + " WHERE " + getCol + " = ?";
		try {
			PreparedStatement pstm = conn.prepareStatement(q);
			pstm.setString(1, where);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				save = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return save;
	}

	public static int getInt(String wantCol, String table, String getCol, String where) {
		int save = 0;
		String q = "SELECT " + wantCol + " FROM oldpopsong." + table + " WHERE " + getCol + " = ?";
		try {
			PreparedStatement pstm = conn.prepareStatement(q);
			pstm.setString(1, where);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				save = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return save;
	}

	public static byte[] getImage(String getCol, String where) {
		byte[] save = null;
		String q = "SELECT m_img FROM oldpopsong.music WHERE " + getCol + " = ?";
		try {
			PreparedStatement pstm = conn.prepareStatement(q);
			pstm.setString(1, where);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				save = rs.getBytes(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return save;
	}

	public static List<Integer> getAllInt(String wantCol, String table) {
		List<Integer> save = new ArrayList<>();
		String q = "SELECT " + wantCol + " FROM oldpopsong." + table;
		ResultSet rs;
		try {
			rs = st.executeQuery(q);
			while (rs.next()) {
				save.add(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return save;
	}
	
	public static List<String> getAllString(String wantCol, String table) {
		List<String> save = new ArrayList<>();
		String q = "SELECT " + wantCol + " FROM oldpopsong." + table;
		ResultSet rs;
		try {
			rs = st.executeQuery(q);
			while (rs.next()) {
				save.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return save;
	}
}
