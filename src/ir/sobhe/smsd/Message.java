package ir.sobhe.smsd;

public class Message {
	private long id;
	private String to;
	private String message;
	private long orig_id;
	
	public long getOrig_id() {
		return orig_id;
	}
	public void setOrig_id(long orig_id) {
		this.orig_id = orig_id;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String toString() {
		return "to: " + to + ", message\"" + message + "\"";
	}
}
