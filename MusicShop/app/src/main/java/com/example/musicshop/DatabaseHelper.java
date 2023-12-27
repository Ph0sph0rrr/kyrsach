package com.example.musicshop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.musicshop.Classes.Item;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "my_database";
    private static final int DATABASE_VERSION = 3;

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    public static final String TABLE_ITEMS = "items";
    public static final String COLUMN_ITEM_ID = "_id";
    public static final String COLUMN_ITEM_NAME = "name";
    public static final String COLUMN_ITEM_DESCRIPTION = "description";
    public static final String COLUMN_ITEM_PRICE = "price";

    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS +
            "(" + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_USERNAME + " TEXT, " +
            COLUMN_PASSWORD + " TEXT)";

    private static final String CREATE_TABLE_ITEMS = "CREATE TABLE " + TABLE_ITEMS +
            "(" + COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_ITEM_NAME + " TEXT, " +
            COLUMN_ITEM_DESCRIPTION + " TEXT, " +
            COLUMN_ITEM_PRICE + " REAL)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_ITEMS);
        fillItemsTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        onCreate(db);
    }

    private void fillItemsTable(SQLiteDatabase db) {
        addItem(db, "Электроакустическая гитара Parkwood", "Электроакустическая гитара Parkwood PF51E с концертным корпусом натурального цвета с защитной накладкой на верхнюю деку имеет мягкое, объемное звучание и радующий глаз дизайн.", 25030);
        addItem(db, "Диатоническая губная гармошка Hohner Blues", "Гармошка идеальна для тех, кто учится играть блюз и развивает свои навыки бэндинга.", 3000);
        addItem(db, "Блок-флейта Nuvo Recorder", "Блокфлейты Nuvo Soprano выглядят современно, это нравится детям.", 740);
        addItem(db, "Кларнет Buffet Student", "Кларнет Buffet Student - это непринужденная легкость звукоизвлечения, идеальные интервалы между всеми нотами, сбалансированность регистров и прекрасные акустические свойства.", 37999);
        addItem(db, "Вокальный микрофон Shure", "Перед вами один из самых подаваемых микрофонов современности - SHURE. Он завоевал такую бешеную популярность, благодаря своей прочности, надежности и невероятно чистому, четкому и читаемому звучанию.", 17990);
        addItem(db, "Скрипка 4/4 Karl Hofner", "Скрипка категории студенческих инструментов специальной серии Alfred Stingl производства Германии.", 20069);
    }

    public long addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        long id = db.insert(TABLE_USERS, null, values);
        db.close();
        return id;
    }

    public long addItem(SQLiteDatabase db, String name, String description, double price) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_NAME, name);
        values.put(COLUMN_ITEM_DESCRIPTION, description);
        values.put(COLUMN_ITEM_PRICE, price);
        return db.insert(TABLE_ITEMS, null, values);
    }

    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_ITEMS,
                null,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ITEM_ID));
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NAME));
            String description = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_DESCRIPTION));
            double price = cursor.getDouble(cursor.getColumnIndex(COLUMN_ITEM_PRICE));

            Item item = new Item(id, name, description, price);
            items.add(item);
        }

        cursor.close();
        db.close();

        return items;
    }
}