package com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Model.NewChatModel;

import java.util.ArrayList;


public class ChatDbHelper extends SQLiteOpenHelper {
    private static final String COLUMN_IS_FROM_HISTORY = "is_from_history";
    private static final String COLUMN_MESSAGE = "answer";
    private static final String COLUMN_QUESTION_TYPE = "que_type";
    private static final String COLUMN_SENDER = "question";
    private static final String COLUMN_SENDER_IMAGE = "image_data";
    private static final String COLUMN_SENDER_TYPE = "sender_type";
    private static final String DB_NAME = "chatdb";
    private static final int DB_VERSION = 1;
    private static final String ID_COL = "id";
    private static final String TABLE_NAME = "mychat";

    public ChatDbHelper(Context context) {
        super(context, DB_NAME, (SQLiteDatabase.CursorFactory) null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE mychat (id INTEGER PRIMARY KEY AUTOINCREMENT, question TEXT,answer TEXT,que_type TEXT,image_data INTEGER,sender_type TEXT,is_from_history TEXT)");
    }

    public void insertChatMessage(NewChatModel newChatModel) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_SENDER, newChatModel.getQuestionText());
        contentValues.put(COLUMN_SENDER_IMAGE, Integer.valueOf(newChatModel.getQuestionImage()));
        contentValues.put(COLUMN_QUESTION_TYPE, newChatModel.getQuestionType());
        contentValues.put(COLUMN_SENDER_TYPE, newChatModel.getSenderType());
        contentValues.put(COLUMN_MESSAGE, newChatModel.getAnsText());
        contentValues.put(COLUMN_IS_FROM_HISTORY, newChatModel.isFromHistory());
        writableDatabase.insert(TABLE_NAME, null, contentValues);
        writableDatabase.close();
    }

    public ArrayList<NewChatModel> getAllChatMessages() {
        Cursor rawQuery = getReadableDatabase().rawQuery("SELECT * FROM mychat", null);
        ArrayList<NewChatModel> arrayList = new ArrayList<>();
        if (rawQuery.moveToFirst()) {
            do {
                NewChatModel newChatModel = new NewChatModel();
                newChatModel.setId(rawQuery.getInt(rawQuery.getColumnIndex(ID_COL)));
                newChatModel.setQuestionText(rawQuery.getString(rawQuery.getColumnIndex(COLUMN_SENDER)));
                newChatModel.setQuestionImage(rawQuery.getInt(rawQuery.getColumnIndex(COLUMN_SENDER_IMAGE)));
                newChatModel.setQuestionType(rawQuery.getString(rawQuery.getColumnIndex(COLUMN_QUESTION_TYPE)));
                newChatModel.setSenderType(rawQuery.getString(rawQuery.getColumnIndex(COLUMN_SENDER_TYPE)));
                newChatModel.setAnsText(rawQuery.getString(rawQuery.getColumnIndex(COLUMN_MESSAGE)));
                newChatModel.setFromHistory(rawQuery.getString(rawQuery.getColumnIndex(COLUMN_IS_FROM_HISTORY)));
                arrayList.add(newChatModel);
            } while (rawQuery.moveToNext());
            rawQuery.close();
            return arrayList;
        }
        rawQuery.close();
        return arrayList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS mychat");
        onCreate(sQLiteDatabase);
    }

    public void delete() {
        getReadableDatabase().delete(TABLE_NAME, null, null);
    }
}
