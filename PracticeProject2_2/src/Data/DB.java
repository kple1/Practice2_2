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
	
	public static List<String[]> summary(int decade) {
		List<String[]> save = new ArrayList<>();
		String q = "SELECT m.m_name, count(l.l_no) as count FROM listenlist l\r\n"
				+ "JOIN playlist p ON l.p_no = p.p_no\r\n"
				+ "JOIN user u ON u.u_no = p.u_no\r\n"
				+ "JOIN music m ON m.m_no = p.m_no\r\n"
				+ "where l.play_ox = 1\r\n"
				+ "AND TIMESTAMPDIFF(YEAR, u.birth, CURDATE()) BETWEEN " + decade + " AND " + (decade + 9) + "\r\n"
				+ "GROUP BY m.m_name\r\n"
				+ "ORDER BY count DESC\r\n"
				+ "LIMIT 5";
		try {
			st.execute("USE oldpopsong");
			ResultSet rs = st.executeQuery(q);
			while(rs.next()) save.add(new String[] {rs.getString(1), rs.getString(2)});
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return save;
	}

	public static List<String> getPlayList(String wantCol, String userId) {
		List<String> list = new ArrayList<>();
		try {
			st.execute("USE oldpopsong");
			String q = "SELECT m." + wantCol + " FROM playlist p\r\n"
					+ "JOIN user u ON u.u_no = p.u_no AND u.id = ?\r\n" + "JOIN music m ON p.m_no = m.m_no";

			PreparedStatement pstm = conn.prepareStatement(q);
			pstm.setString(1, userId);
			ResultSet rs = pstm.executeQuery();
			while (rs.next())
				list.add(rs.getString(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	//index 0 = singer
	//index 1 = m_name
	//index 2 = count
	public static List<String[]> getListenList(String where) {
		List<String[]> list = new ArrayList<>();
		String q = "SELECT m.singer, m.m_name, COUNT(l.p_no) as count FROM listenlist l\r\n"
				+ "JOIN playlist p ON l.p_no = p.p_no\r\n" + "JOIN user u ON u.u_no = p.u_no AND u.id = ?\r\n"
				+ "JOIN music m ON m.m_no = p.m_no "
				+ "GROUP BY m.singer, m.m_name";
		try {
			st.execute("USE oldpopsong");
			PreparedStatement pstm = conn.prepareStatement(q);
			pstm.setString(1, where);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) list.add(new String[] {rs.getString(1), rs.getString(2), rs.getString(3)});
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<String> sortMusic(String where, String sort) {
		List<String> save = new ArrayList<>();
		try {
			String q = "SELECT m_name from oldpopsong.music WHERE m_name LIKE ?\r\n" + "ORDER BY m_name " + sort;

			PreparedStatement pstm = conn.prepareStatement(q);
			pstm.setString(1, "%" + where + "%");
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				save.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return save;
	}

	public static List<byte[]> sortImage(String where, String getCol, String sort) {
		List<byte[]> save = new ArrayList<>();
		try {
			String q = "SELECT m_img from oldpopsong.music WHERE " + getCol + " LIKE ?\r\n" + "ORDER BY " + getCol + " "
					+ sort;

			PreparedStatement pstm = conn.prepareStatement(q);
			pstm.setString(1, "%" + where + "%");
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				save.add(rs.getBytes(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return save;
	}

	public static List<String> sortArtist(String where, String sort) {
		List<String> save = new ArrayList<>();
		try {
			String q = "SELECT m_name from oldpopsong.music WHERE singer LIKE ?\r\n" + "ORDER BY singer " + sort;

			PreparedStatement pstm = conn.prepareStatement(q);
			pstm.setString(1, "%" + where + "%");
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				save.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return save;
	}

	public static String lastlisten(String userId, int musicNum) {
		String save = "";
		try {
			st.executeUpdate("USE oldpopsong;");
			String q = "SELECT l.stoptime FROM listenlist l " + "JOIN playlist p ON l.p_no = p.p_no AND l.play_ox = 0 "
					+ "JOIN user u ON p.u_no = u.u_no AND u.id = '" + userId + "' "
					+ "JOIN music m ON p.m_no = m.m_no AND m.m_no = " + musicNum + ";";

			ResultSet rs = st.executeQuery(q);
			if (rs.next())
				save = rs.getString(1);
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
