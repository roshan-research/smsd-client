package ir.sobhe.smsd;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class MessagesDataSource {
	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_TO, MySQLiteHelper.COLUMN_TEXT };
	
	public MessagesDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}
	
	private Message cursorToMessage(Cursor cursor) {
		Message message = new Message();
		message.setId(cursor.getLong(0));
		message.setMessage(cursor.getString(2));
		message.setTo(cursor.getString(1));
		return message;
	}
	
	public Message createMessage(String to, String message) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_TO, to);
		values.put(MySQLiteHelper.COLUMN_TEXT, message);
		long insertId = database.insert(MySQLiteHelper.TABLE_MESSAGES, null,
				values);
		// To show how to query
		Cursor cursor = database.query(MySQLiteHelper.TABLE_MESSAGES,
				allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		return cursorToMessage(cursor);
	}
	
	public void deleteMessage(Message message) {
		long id = message.getId();
		System.out.println("Message deleted with id: " + id);
		database.delete(MySQLiteHelper.TABLE_MESSAGES, MySQLiteHelper.COLUMN_ID
				+ " = " + id, null);
	}
	
	public List<Message> getAllComments() {
		List<Message> messages = new ArrayList<Message>();
		Cursor cursor = database.query(MySQLiteHelper.TABLE_MESSAGES,
				allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Message message = cursorToMessage(cursor);
			messages.add(message);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return messages;
	}
}
