<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="jdbc.JDBCSingleton"%>
    <%@page import="java.sql.Connection" %>
	<%@page import="java.sql.DriverManager" %>
	<%@page import="java.sql.SQLException" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>UGC Report</title>
</head>
   
   <body>
      <!-- HTML --> 
      <form action="input.jsp" method="post">
       <p>Start_Date : </p><input name="start" type="date" value="2017-05-01"/> <span style="display:inline-block; width: 1em;"></span> End_Date : <input name="end" type="date" value="2017-05-02"/>
      <p>Get data: <input type="submit" name="getData"></form>
      <%
		if(request.getParameter("start")!=null&&request.getParameter("end")!=null){	
		String start_time = request.getParameter("start") + " " + "00:00:00";
		String end_time = request.getParameter("end") + " " + "23:59:59";
		JDBCSingleton jdbc = JDBCSingleton.getInstance();
		Connection con = JDBCSingleton.getConnection();
		
		//s="2017-05-31 00:00:00";				2017-05-31 00:00:00 2017-06-30 23:59:59 
		//e="2017-06-30 23:59:59";
		int[] content_type = new int[2];
		content_type = jdbc.typeContentsUploaded(start_time, end_time,con);
		int no_of_images = content_type[0];
		int no_of_jokes = content_type[1];
		int total_content = no_of_images + no_of_jokes;
		int distict_users = jdbc.noOfUniqueUsers(start_time, end_time,con);
		int no_of_likes = jdbc.noOfLikes(start_time, end_time,con);
		float no_of_likes_content = (float) no_of_likes / total_content;
		int no_of_shares_click_count = jdbc.noOfShares(start_time, end_time,con);
		float shares_per_content = (float) no_of_shares_click_count / total_content;
		int no_of_comments = jdbc.noOfComments(start_time, end_time,con);
		float comments_per_content = (float) no_of_comments / total_content;
		int no_of_spams = jdbc.noOfSpams(start_time, end_time,con);
		int no_of_spams_non_handlers = jdbc.noOfSpamsNonHandlers(start_time, end_time,con);
		int noOfLikesGreaterThan10 = jdbc.noOfLikesGreaterThan10(start_time, end_time,con);
		int noOfSharesGreaterThan10 = jdbc.noOfSharesGreaterThan10(start_time, end_time,con);
		int unique_handlers_followed = jdbc.uniqueHandlersFollowed(start_time, end_time,con);
		int unique_handlers_unfollowed = jdbc.uniqueHandlersUnfollowed(start_time, end_time,con);
		if (con != null) {
			con.close();
		}
	%>
	<h2>UGC Report</h2>
	<p>Total no. of contents uploaded: <%=total_content%>    (Images: <%=no_of_images%> Jokes: <%=no_of_jokes%>)</p>
	<p>Total no. of unique users who contents uploaded: <%=distict_users%></p>
	<p>Total no. of likes: <%=no_of_likes%></p>
	<p>No. of likes per content: <%=no_of_likes_content%></p>
	<p>Total share click count: <%=no_of_shares_click_count%></p>
	<p>Total no. of comments: <%=no_of_comments%></p>
	<p>Total no. of comments per content: <%=comments_per_content%></p>
	<p>Total no. of contents whose like count > 10 : <%=noOfLikesGreaterThan10%></p>
	<p>Total no. of contents whose share count > 10 : <%=noOfSharesGreaterThan10%></p>
	<p>Total no. of contents spammed: <%=no_of_spams%></p>
	<p>Total no. of contents spammed (excluding ones from default handlers): <%=no_of_spams_non_handlers %></p>
	<p>Total no. of unique handlers followed today: <%=unique_handlers_followed%></p>
	<p>Total no. of unique handlers unfollowed today: <%=unique_handlers_unfollowed%></p>
	<%}%></body>
</html>