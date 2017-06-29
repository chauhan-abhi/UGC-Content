package jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCSingleton {
	// Step 1
	// create a JDBCSingleton class.
	// static member holds only one instance of the JDBCSingleton class.

	private static JDBCSingleton jdbc;

	// JDBCSingleton prevents the instantiation from any other class.
	private JDBCSingleton() {
	}

	// Now we are providing global point of access.
	public static JDBCSingleton getInstance() {
		if (jdbc == null) {
			jdbc = new JDBCSingleton();
		}
		return jdbc;
	}

	// to get the connection from methods like insert, view etc.
	public static Connection getConnection() throws ClassNotFoundException, SQLException, IOException {

		Connection con = null;

		Properties prop = new Properties();
		InputStream inputStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("config.properties");

		try {
			prop.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		inputStream.close();
		String driver = prop.getProperty("driverClassName");
		if (driver != null) {
			Class.forName(driver);
		}
		String dburl = prop.getProperty("url");
		String username = prop.getProperty("username");
		String password = prop.getProperty("password");
		//System.out.println(driver+" ,"+dburl+" ,"+username+" ,"+password);
		con = DriverManager.getConnection(dburl, username, password);
		return con;
		

	}

	/////////// returns noOfTextContentsUploaded//////////////
	public int[] typeContentsUploaded(String start_time, String end_time, Connection conn) throws SQLException {
		Connection con = conn;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int[] grouped_data = new int[2];
		try {
			int index = 0;
			ps = con.prepareStatement("select count(id) from ugc_content" + " where insert_time between \'" + start_time
					+ "\'" + " and \'" + end_time + "\'" + " group by content_type");

			rs = ps.executeQuery();
			while (rs.next()) {
				grouped_data[index] = rs.getInt(1); // to get the value in the
													// first, and in
				index++; // your case, only column.
			}
			return grouped_data;
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
		}
		return grouped_data;
	}

	/////////// returns noOfImageContentsUploaded///////////

	//////////// no. of unique users who uploaded content///////////
	public int noOfUniqueUsers(String start_time, String end_time, Connection conn) throws SQLException {
		Connection con = conn;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement("select count(distinct uid) from ugc_content" + " where insert_time between \'"
					+ start_time + "\'" + " and \'" + end_time + "\'");

			rs = ps.executeQuery();
			if (rs.last()) {
				return rs.getInt(1);
			} else {
				return 0;
			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}

		}
		return 0;
	}

	//////////// no. of likes////////////
	public int noOfLikes(String start_time, String end_time, Connection conn) throws SQLException {
		Connection con = conn;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement("select count(1) from like_details" + " where like_date between \'" + start_time
					+ "\'" + " and \'" + end_time + "\'");

			rs = ps.executeQuery();
			if (rs.last()) {
				return rs.getInt(1);
			} else {
				return 0;
			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}

		}
		return 0;
	}

	///////////////////// no of shares///////////////////
	public int noOfShares(String start_time, String end_time, Connection conn) throws SQLException {
		Connection con = conn;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement("select count(id) from share_details" + " where share_date between \'"
					+ start_time + "\'" + " and \'" + end_time + "\'");

			rs = ps.executeQuery();
			if (rs.last()) {
				return rs.getInt(1);
			} else {
				return 0;
			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}

		}
		return 0;
	}

	//////////////// no of comments/////////////////
	public int noOfComments(String start_time, String end_time, Connection conn) throws SQLException {
		Connection con = conn;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement("select count(id) from comment_details" + " where insert_time between \'"
					+ start_time + "\'" + " and \'" + end_time + "\'");

			rs = ps.executeQuery();
			if (rs.last()) {
				return rs.getInt(1);
			} else {
				return 0;
			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}

		}
		return 0;
	}

	////////////////////// n of likes >10/////////////////////
	public int noOfLikesGreaterThan10(String start_time, String end_time, Connection conn) throws SQLException {
		Connection con = conn;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0; // store count greater than 10
		try {
			ps = con.prepareStatement("select count(content_id) as c from like_details" + " where like_date between \'"
					+ start_time + "\'" + " and \'" + end_time + "\'" + "group by content_id having c>10");

			rs = ps.executeQuery();
			while (rs.next()) {
				count++;
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}

		}
		return count;
	}

	///////////// no of shares greater than 10//////////
	public int noOfSharesGreaterThan10(String start_time, String end_time, Connection conn) throws SQLException {
		Connection con = conn;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0; // store count greater than 10
		try {
			ps = con.prepareStatement(
					"select count(content_id) as c from share_details" + " where share_date between \'" + start_time
							+ "\'" + " and \'" + end_time + "\'" + "group by content_id having c>10");

			rs = ps.executeQuery();
			while (rs.next()) {
				count++;
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}

		}
		return count;
	}

	///////////////// no of spams/////////////////
	public int noOfSpams(String start_time, String end_time, Connection conn) throws SQLException {
		Connection con = conn;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement("select count(id) from spam_details" + " where insert_time between \'"
					+ start_time + "\'" + " and \'" + end_time + "\'");

			rs = ps.executeQuery();
			if (rs.last()) {
				return rs.getInt(1);
			} else {
				return 0;
			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}

		}
		return 0;
	}

	/////////// spams not from handlers////////////
	public int noOfSpamsNonHandlers(String start_time, String end_time, Connection conn)
			throws SQLException, IOException {
		Connection con = conn;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Properties prop = new Properties();
		InputStream inputStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("config.properties");

		try {
			prop.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String uid = prop.getProperty("default_handlers_uid").trim();

		try {

			ps = con.prepareStatement("select count(uid) from spam_details " + " where insert_time between \'"
					+ start_time + "\'" + " and \'" + end_time + "\'" + " and uid not in (" + uid + ")");

			rs = ps.executeQuery();
			if (rs.last()) {
				return rs.getInt(1);
			} else {
				return 0;
			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}

		}

		return 0;
	}

	/////////// no of unique handlers followed today////////////
	public int uniqueHandlersFollowed(String start_time, String end_time, Connection conn) throws SQLException {
		Connection con = conn;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement("select count(distinct uid_followed) from users_follow_relation"
					+ " where insert_time between \'" + start_time + "\'" + " and \'" + end_time + "\'");

			rs = ps.executeQuery();
			if (rs.last()) {
				return rs.getInt(1);
			} else {
				return 0;
			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}

		}
		return 0;
	}

	////////////// unique handlers unfollowed/////////////
	public int uniqueHandlersUnfollowed(String start_time, String end_time, Connection conn) throws SQLException {
		Connection con = conn;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement("select count(distinct uid_followed) from users_follow_relation where"
					+ " update_time between \'" + start_time + "\'" + " and \'" + end_time + "\'" + " and status=0");

			rs = ps.executeQuery();
			if (rs.last()) {
				return rs.getInt(1);
			} else {
				return 0;
			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}

		}
		return 0;
	}
}