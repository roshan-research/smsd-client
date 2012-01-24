package ir.sobhe.smsd;

public class Constants {
	public static final String flask = "http://192.168.1.3:5000/";
	public static final String fetch_url = flask + "get/";
	public static final String sent_url = flask + "sent/";
	public static final String delivered_url = flask + "delivered/";
	public static final int fetch_interval = 1000;
	public static final int sender_interval = 100;
	
	//Client info for smsd
	public static final String name = "htc-tatto";
	public static final String key = "2f1a5ee55fe8435b6aa82782d318f5e2";
}
