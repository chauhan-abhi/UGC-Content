import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class JDBCSingleton {
	// Step 1
	// create a JDBCSingleton class.
	// static member holds only one instance of the JDBCSingleton class.

	private static JDBCSingleton jdbc;

	// JDBCSingleton prevents the instantiation from any other class.
	private JDBCSingleton() {
	}

	// Now we are providing gloabal point of access.
	public static JDBCSingleton getInstance() {
		if (jdbc == null) {
			jdbc = new JDBCSingleton();
		}
		return jdbc;
	}

	// to get the connection from methods like insert, view etc.
	private static Connection getConnection() throws ClassNotFoundException, SQLException {

		Connection con = null;
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pk", "root", "abhi");
		return con;

	}

	/////////// returns noOfTextContentsUploaded//////////////
	public int noOfTextContentsUploaded(String start_time, String end_time) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			con = JDBCSingleton.getConnection();
			ps = con.prepareStatement("select count(uid) from ugc_content where content_type=\"text\""
					+ " and insert_time between \'" + start_time + "\'" + " and \'" + end_time + "\'");

			rs = ps.executeQuery();
			if (rs.last()) {
				return rs.getInt(1); // to get the value in the first, and in
										// your case, only column.

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
			if (con != null) {
				con.close();
			}
		}
		return 0;
	}

	/////////// returns noOfImageContentsUploaded///////////
	public int noOfImageContentsUploaded(String start_time, String end_time) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			con = JDBCSingleton.getConnection();
			ps = con.prepareStatement("select count(1) from ugc_content where content_type=\"image\" "
					+ " and insert_time between \'" + start_time + "\'" + " and \'" + end_time + "\'");

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
			if (con != null) {
				con.close();
			}
		}
		return 0;
	}

	//////////// no. of unique users who uploaded content///////////
	public int noOfUniqueUsers(String start_time, String end_time) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			con = JDBCSingleton.getConnection();
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
			if (con != null) {
				con.close();
			}
		}
		return 0;
	}

	//////////// no. of likes////////////
	public int noOfLikes(String start_time, String end_time) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			con = JDBCSingleton.getConnection();
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
			if (con != null) {
				con.close();
			}
		}
		return 0;
	}

	///////////////////// no of shares///////////////////
	public int noOfShares(String start_time, String end_time) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			con = JDBCSingleton.getConnection();
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
			if (con != null) {
				con.close();
			}
		}
		return 0;
	}

	//////////////// no of comments/////////////////
	public int noOfComments(String start_time, String end_time) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			con = JDBCSingleton.getConnection();
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
			if (con != null) {
				con.close();
			}
		}
		return 0;
	}

	////////////////////// n of likes >10/////////////////////
	public int noOfLikesGreaterThan10(String start_time, String end_time) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0; // store count greater than 10
		try {

			con = JDBCSingleton.getConnection();
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
			if (con != null) {
				con.close();
			}
		}
		return count;
	}

	///////////// no of shares greater than 10//////////
	public int noOfSharesGreaterThan10(String start_time, String end_time) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0; // store count greater than 10
		try {

			con = JDBCSingleton.getConnection();
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
			if (con != null) {
				con.close();
			}
		}
		return count;
	}

	///////////////// no of spams/////////////////
	public int noOfSpams(String start_time, String end_time) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			con = JDBCSingleton.getConnection();
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
			if (con != null) {
				con.close();
			}
		}
		return 0;
	}

	/////////// no of unique handlers followed today////////////
	public int uniqueHandlersFollowed(String start_time, String end_time) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			con = JDBCSingleton.getConnection();
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
			if (con != null) {
				con.close();
			}
		}
		return 0;
	}

	////////////// unique handlers unfollowed/////////////
	public int uniqueHandlersUnfollowed(String start_time, String end_time) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			con = JDBCSingleton.getConnection();
			ps = con.prepareStatement("select count(distinct uid_followed) from users_follow_relation where status=0"
					+ " and update_time between \'" + start_time + "\'" + " and \'" + end_time + "\'");

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
			if (con != null) {
				con.close();
			}
		}
		return 0;
	}

	public static void main(String args[]) throws IOException {
		JDBCSingleton jdbc = new JDBCSingleton();
		try {
			int no_of_images = jdbc.noOfImageContentsUploaded("2017-05-31 00:00:00", "2017-05-31 17:00:00");
			int no_of_jokes = jdbc.noOfTextContentsUploaded("2017-05-31 00:00:00","2017-05-31 17:00:00");
			int total_content = no_of_images + no_of_jokes;
			int distict_users = jdbc.noOfUniqueUsers("2017-05-31 00:00:00","2017-05-31 17:00:00");
			int no_of_likes = jdbc.noOfLikes("2017-05-31 00:00:00","2017-05-31 17:00:00");
			double no_of_likes_content=no_of_likes/total_content;
			int no_of_shares_click_count=jdbc.noOfShares("2017-05-31 00:00:00","2017-05-31 17:00:00");
			double shares_per_content=no_of_shares_click_count/total_content;
			int no_of_comments = jdbc.noOfComments("2017-05-31 00:00:00","2017-05-31 17:00:00");
		    double comments_per_comment=no_of_comments/total_content;
			int no_of_likes_grt_10=jdbc.noOfLikesGreaterThan10("2017-03-20 00:00:00", "2017-03-20 18:00:00");
			int no_of_spams = jdbc.noOfSpams("2017-05-31 00:00:00","2017-05-31 17:00:00");
			int noOfLikesGreaterThan10=jdbc.noOfLikesGreaterThan10("2017-03-20 14:12:26","2017-05-03 17:16:12");
			int noOfSharesGreaterThan10=jdbc.noOfSharesGreaterThan10("2017-03-20 14:12:26","2017-05-03 17:16:12");
			int unique_handlers_followed =jdbc.uniqueHandlersFollowed("2017-03-20 14:12:26","2017-05-03 17:16:12");
			int unique_handlers_unfollowed = jdbc.uniqueHandlersUnfollowed("2017-03-20 14:12:26","2017-05-03 17:16:12");
			System.out.println(no_of_images);

			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}