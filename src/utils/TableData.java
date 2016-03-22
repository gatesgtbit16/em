package utils;

public class TableData {

	
		public static final String HOST="ec2-54-83-57-25.compute-1.amazonaws.com";
		public static final String PORT="5432";
		public static final String DB_NAME="d7d7u6r32rp2pb";
		public static final String USERNAME="lbwiyhpiuhcwhs";
		public static final String PASSWORD="rEiKYkYxcgjhd-eStzdHLlO4rM";
		public static final String DB_DRIVERS="org.postgresql.Driver";	
		public static final String CONNECTION_URL="jdbc:postgresql://"+HOST+":"+PORT+"/"+DB_NAME+"?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory&sslmode=require";
	
}
