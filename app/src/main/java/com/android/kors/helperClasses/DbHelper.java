package com.android.kors.helperClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DbHelper extends SQLiteOpenHelper {

    /* ------------------------------------------------------------------------------ Logcat tag */
    private static final String LOG = "DbHelper_Kors";

    /* -------------------------------------------------------------------------------- DataBase */
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "KorsMobileDb";

    /* ------------------------------------------------------------------------------ Wall Table */
    private static final String LOCAL_TABLE = "Locals";
    private static final String LOCAL_ID = "Id";
    private static final String LOCAL_WALLPAPER = "Wallpaper";
    private static final String LOCAL_NOTIFICATION = "Notification";
    private static final String LOCAL_USER_ID = "UId";
    private static final String LOCAL_CURRENT_GROUP_ID = "CurrentGId";
    private static final String LOCAL_CURRENT_GROUP_NAME = "CurrentGName";


    private static final String CREATE_LOCAL_TABLE =
            "CREATE TABLE " +LOCAL_TABLE+ "("
                    +LOCAL_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    +LOCAL_WALLPAPER+ " INTEGER, "
                    +LOCAL_NOTIFICATION+ " INTEGER, "
                    +LOCAL_USER_ID+ " INTEGER, "
                    +LOCAL_CURRENT_GROUP_ID+ " INTEGER, "
                    + LOCAL_CURRENT_GROUP_NAME + " TEXT)";

    /* ----------------------------------------------------------------------------- Constructor */
    public DbHelper(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    /* -------------------------------------------------------------------------------- OnCreate */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LOCAL_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + LOCAL_TABLE);
        onCreate(db);
    }

    // truncate all tables
    public void truncateAllTables(){
        SQLiteDatabase db = this.getWritableDatabase();

        try{
            db.execSQL("delete from " + LOCAL_TABLE);
        } catch(Exception e){
            Log.e(LOG, e.getMessage());
        } finally {
            db.close();
        }
    }

    /* ----------------------------------------------------------------------------------- METHODS */
    public void insertInitialSettingPreference (int prefCode, int notifid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();

        try{
            value.put(LOCAL_WALLPAPER, prefCode);
            value.put(LOCAL_NOTIFICATION, notifid);

            db.insert(LOCAL_TABLE, null, value);
        } catch(Exception e){
            Log.e(LOG, e.getMessage());
        } finally {
            db.close();
        }
    }

    public void insertLocalUserId(int uid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();

        try{
            value.put(LOCAL_USER_ID, uid);
            db.update(LOCAL_TABLE, value,LOCAL_ID + " = " + 1 ,null);
        } catch(Exception e){
            Log.e(LOG, e.getMessage());
        } finally {
            db.close();
        }
    }

    public void insertLocalGroupId(int gid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();

        try{
            value.put(LOCAL_CURRENT_GROUP_ID, gid);
            db.update(LOCAL_TABLE, value,LOCAL_ID + " = " + 1 ,null);
        } catch(Exception e){
            Log.e(LOG, e.getMessage());
        } finally {
            db.close();
        }
    }

    public void insertLocalCurrentGroupName(String groupName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();

        try{
            value.put(LOCAL_CURRENT_GROUP_NAME, groupName);
            db.update(LOCAL_TABLE, value,LOCAL_ID + " = " + 1 ,null);
        } catch(Exception e){
            Log.e(LOG, e.getMessage());
        } finally {
            db.close();
        }
    }

    public void updateWallpaperPreference(int prefCode){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();

        try{
            value.put(LOCAL_WALLPAPER, prefCode);
            db.update(LOCAL_TABLE, value,LOCAL_ID + " = " + 1 ,null);

        }catch (Exception e){
            Log.e(LOG, e.getMessage());
        }finally {
            db.close();
        }
    }

    public String getCurrentGroupName(){
        SQLiteDatabase db = this.getReadableDatabase();
        String output = "";

        String selectQuery = "SELECT "+ LOCAL_CURRENT_GROUP_NAME +" FROM "+LOCAL_TABLE+" WHERE "
                +LOCAL_ID+" = " + 1 ;
        Log.e(LOG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        try{
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                output = cursor.getString(cursor.getColumnIndex(LOCAL_CURRENT_GROUP_NAME));
            }
        }catch (Exception e){
            Log.e(LOG, e.getMessage());
        }finally {
            cursor.close();
            db.close();
        }
        return output;
    }

    public int getCurrentGroupId(){
        SQLiteDatabase db = this.getReadableDatabase();
        int output = 0;

        String selectQuery = "SELECT "+ LOCAL_CURRENT_GROUP_ID +" FROM "+LOCAL_TABLE+" WHERE "
                +LOCAL_ID+" = " + 1 ;
        Log.e(LOG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        try{
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                output = cursor.getInt(cursor.getColumnIndex(LOCAL_CURRENT_GROUP_ID));
            }
        }catch (Exception e){
            Log.e(LOG, e.getMessage());
        }finally {
            cursor.close();
            db.close();
        }
        return output;
    }

    public int getCurrentWallpaperPreference(){
        SQLiteDatabase db = this.getReadableDatabase();
        int output = 0;

        String selectQuery = "SELECT "+LOCAL_WALLPAPER+" FROM "+LOCAL_TABLE+" WHERE "
                +LOCAL_ID+" = " + 1 ;
        Log.e(LOG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        try{
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                output = cursor.getInt(cursor.getColumnIndex(LOCAL_WALLPAPER));
            }
        }catch (Exception e){
            Log.e(LOG, e.getMessage());
        }finally {
            cursor.close();
            db.close();
        }
        return output;
    }

    public long getDbItemCount()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, LOCAL_TABLE);
        db.close();

        return count;
    }

    public int getCurrentNotificationPreference(){
        SQLiteDatabase db = this.getReadableDatabase();
        int output = 0;

        String selectQuery = "SELECT "+LOCAL_NOTIFICATION+" FROM "+LOCAL_TABLE+" WHERE "
                +LOCAL_ID+" = " + 1 ;
        Log.e(LOG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        try{
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                output = cursor.getInt(cursor.getColumnIndex(LOCAL_NOTIFICATION));
            }
        }catch (Exception e){
            Log.e(LOG, e.getMessage());
        }finally {
            cursor.close();
            db.close();
        }
        return output;
    }

    public void updateNotificationPreference(int prefCode){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();

        try{
            value.put(LOCAL_NOTIFICATION, prefCode);
            db.update(LOCAL_TABLE, value,LOCAL_ID + " = " + 1 ,null);

        }catch (Exception e){
            Log.e(LOG, e.getMessage());
        }finally {
            db.close();
        }
    }

    public int getCurrentUserId(){
        SQLiteDatabase db = this.getReadableDatabase();
        int output = 0;

        String selectQuery = "SELECT "+LOCAL_USER_ID+" FROM "+LOCAL_TABLE+" WHERE "
                +LOCAL_ID+" = " + 1 ;
        Log.e(LOG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        try{
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                output = cursor.getInt(cursor.getColumnIndex(LOCAL_USER_ID));
            }
        }catch (Exception e){
            Log.e(LOG, e.getMessage());
        }finally {
            cursor.close();
            db.close();
        }
        return output;
    }
}