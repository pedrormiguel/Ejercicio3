package com.example.ejercicio2;

import android.app.Person;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

public class BDHelper extends SQLiteOpenHelper {

    protected static final String DATABASE_NAME = "StudentDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "STUDENT";
    private static final String IdColumn = "id";
    private static final String NameColum = "name";
    private static final String PicturePathColum = "picturePath";
    private static final String CityBornColum = "city";
    private static final String NumberIdentifierColum = "numberIdentifier";
    private static final String PicturePathInt = "picturePathInt";
    private static final String CreativeExpressionColum = "creativeExpression";

    public BDHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlQuery = "CREATE TABLE " + TABLE_NAME;
        String firstAttribute = "(id INTEGER PRIMARY KEY AUTOINCREMENT, ";
        String secondAttribute = "name        TEXT, ";
        String thirdAttribute = "picturePath TEXT, ";
        String fourthAttribute = "city    TEXT, ";
        String fithAttribute = "numberIdentifier  TEXT, ";
        String sixthAttribute = "picturePathInt INTEGER, ";
        String seventhAttribute = "creativeExpression TEXT)";

        String finalQuery = sqlQuery + firstAttribute + secondAttribute +
                thirdAttribute + fourthAttribute + fithAttribute +
                sixthAttribute + seventhAttribute;

        db.execSQL(finalQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    public void deleteTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    public boolean create(Estudiantes student) {
        ContentValues values = new ContentValues();

        values.put(NameColum, student.name);
        values.put(PicturePathColum, student.pathImage);
        values.put(CityBornColum, student.city);
        values.put(NumberIdentifierColum, student.numberId);
        values.put(PicturePathInt, student.photoInt);
        values.put(CreativeExpressionColum, student.expression);

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert(TABLE_NAME, null, values) > 0;
        db.close();

        return createSuccessful;
    }

    public int count() {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM " + TABLE_NAME;

        int recordCount = db.rawQuery(sql, null).getCount();

        db.close();

        return recordCount;
    }

    public List<Estudiantes> read() {

        List<Estudiantes> recordList = new ArrayList<Estudiantes>();

        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY id DESC";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                recordList.add(
                        new Estudiantes(
                                Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))),
                                cursor.getString(cursor.getColumnIndex(NameColum)),
                                cursor.getString(cursor.getColumnIndex(PicturePathColum)),
                                cursor.getString(cursor.getColumnIndex(CityBornColum)),
                                cursor.getString(cursor.getColumnIndex(NumberIdentifierColum)),
                                cursor.getString(cursor.getColumnIndex(CreativeExpressionColum)),
                                cursor.getColumnIndex(PicturePathInt)
                        )
                );
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return recordList;
    }

    public Estudiantes readSingleRecord(int studentId) {

        Estudiantes objectStudent = null;

        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = " + studentId;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));

            String name = cursor.getString(cursor.getColumnIndex(NameColum));

            String pathImage = cursor.getString(cursor.getColumnIndex(PicturePathColum));

            String city = cursor.getString(cursor.getColumnIndex(CityBornColum));

            String numberId = cursor.getString(cursor.getColumnIndex(NumberIdentifierColum));

            String expression = cursor.getString(cursor.getColumnIndex(CreativeExpressionColum));

            int photoId = cursor.getColumnIndex(PicturePathInt);

            objectStudent = new Estudiantes(id, name, pathImage, city, numberId, expression, photoId);

        }

        cursor.close();
        db.close();

        return objectStudent;
    }

    public boolean update(Estudiantes objectStudent) {

        ContentValues values = new ContentValues();

        values.put(NameColum, objectStudent.name);
        values.put(PicturePathColum, objectStudent.pathImage);
        values.put(CityBornColum, objectStudent.city);
        values.put(NumberIdentifierColum, objectStudent.numberId);
        values.put(CreativeExpressionColum, objectStudent.expression);
        values.put(PicturePathInt, objectStudent.photoInt);

        String where = "id = ?";

        String[] whereArgs = { Integer.toString(objectStudent.id) };

        SQLiteDatabase db = this.getWritableDatabase();

        boolean updateSuccessful = db.update(TABLE_NAME, values, where, whereArgs) > 0;

        db.close();
        return updateSuccessful;
    }

    public boolean delete(int studenId) {

        boolean deleteSuccessful = false;

        SQLiteDatabase db = this.getWritableDatabase();

        deleteSuccessful = db.delete(TABLE_NAME, "id ='" + studenId + "'", null) > 0;

        db.close();
        return deleteSuccessful;
    }
}
