
public class Str {

	public static void main(String args[]){
		String start_time="2017-02-31 00:00:00";
		String end_time="2017-05-31 17:00:00";
		System.out.println("select count(distinct uid_followed) from users_follow_relation where status=0"
				+ " and update_time between \'"
				+ start_time + "\'" + " and \'" + end_time + "\'");
	}
}
