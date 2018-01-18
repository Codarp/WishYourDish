package com.arpinster.wishyourdish;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Arpit on 6/12/2017.
 */

public class RecipeSaveHandler extends SQLiteOpenHelper {

    String TABLE_NAME="Self_Recipe";
    public RecipeSaveHandler(Context context, String name) {
        super(context, name, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create = "create table "+TABLE_NAME+" (TITLE TEXT PRIMARY KEY, INGREDIENTS TEXT, DESCRIPTION TEXT, IMAGE BLOB)";
        sqLiteDatabase.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String title, String ingredients, String description, byte[] image)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("TITLE",title);
        cv.put("INGREDIENTS",ingredients);
        cv.put("DESCRIPTION",description);
        cv.put("IMAGE",image);
        long i = db.insert(TABLE_NAME,null,cv);
        if(i==-1)
            return false;
        else
            return true;
    }

    public Cursor viewData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor row = db.rawQuery("select * from "+TABLE_NAME,null);
        return row;
    }

    public int deleteData(String title)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"TITLE = ?",new String[] {title});
    }
}
