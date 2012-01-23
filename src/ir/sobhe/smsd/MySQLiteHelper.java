package ir.sobhe.smsd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper{
	
	public static final String TABLE_MESSAGES = "messages";
	public static final String COLUMN_ID = "`_id`";
	public static final String COLUMN_TO = "`to`";
	public static final String COLUMN_TEXT = "`text`";
	
	private static final String DATABASE_NAME = "messages.db";
	private static final int DATABASE_VERSION = 1;
	
	//Database creation sql statement
	private static final String DATABASE_CREATE = "CREATE TABLE "
		+ TABLE_MESSAGES + "(" + COLUMN_ID + " integer primary key autoincrement, "
		+ COLUMN_TO + " VARCHAR(20) not null,"
		+ COLUMN_TEXT + " TEXT not null);";

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_MESSAGES);
		onCreate(db);
	}
	
}
