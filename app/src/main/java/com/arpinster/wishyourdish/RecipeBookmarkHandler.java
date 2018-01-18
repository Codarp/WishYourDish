package com.arpinster.wishyourdish;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Arpit on 6/15/2017.
 */
public class RecipeBookmarkHandler extends SQLiteOpenHelper {

    String TABLE_NAME="Bookmark_Recipe";
    public RecipeBookmarkHandler(Context context, String name) {
        super(context, name, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create = "create table "+TABLE_NAME+" ( TITLE TEXT PRIMARY KEY, HEALTH TEXT, INGREDIENTS TEXT, URL TEXT, IMAGE BLOB, CALORIES TEXT )";
        sqLiteDatabase.execSQL(create);
        Log.i("TAG","Table is created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String title,String health, String ingredients, String url, byte[] image, String calories)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("TITLE",title);
        cv.put("INGREDIENTS",ingredients);
        cv.put("URL",url);
        cv.put("HEALTH",health);
        cv.put("IMAGE",image);
        cv.put("CALORIES",calories);
        long i = db.insert(TABLE_NAME,null,cv);
        Log.i("TAG","Values inserted");
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
