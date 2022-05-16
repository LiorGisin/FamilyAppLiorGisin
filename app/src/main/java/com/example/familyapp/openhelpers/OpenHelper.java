package com.example.familyapp.openhelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.familyapp.objects.Event;
import com.example.familyapp.objects.Family;
import com.example.familyapp.objects.Item;
import com.example.familyapp.objects.ShoppingItem;
import com.example.familyapp.objects.Task;
import com.example.familyapp.objects.User;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class OpenHelper extends SQLiteOpenHelper {

    public static final String DATABASENAME = "db";
    public static final int DATABASEVERSION = 1;


    public static final String TABLE_FAMILY = "tblfamilies";


    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FNAME = "familyName";
    public static final String COLUMN_FPASSWORD = "familyPassword";

    private static final String CREATE_TABLE_FAMILY = "CREATE TABLE IF NOT EXISTS " +
            TABLE_FAMILY + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_FNAME + " VARCHAR,"
            + COLUMN_FPASSWORD + " VARCHAR " + ");";

    String[] familyAllColumns = {OpenHelper.COLUMN_ID, OpenHelper.COLUMN_FNAME, OpenHelper.COLUMN_FPASSWORD};


    public static final String TABLE_USERS = "tblusers";

    public static final String COLUMN_USERID = "userId";
    public static final String COLUMN_FIRSTNAME = "firstname";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_FAMILYID = "familyid";
    public static final String COLUMN_UPASSWORD = "password";
    public static final String COLUMN_PHONENUMBER = "phoneNumber";


    private static final String CREATE_TABLE_USERS = "CREATE TABLE IF NOT EXISTS " +
            TABLE_USERS + "(" + COLUMN_USERID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_FIRSTNAME + " VARCHAR," + COLUMN_IMAGE + " VARCHAR, "
            + COLUMN_FAMILYID + " INTEGER," + COLUMN_UPASSWORD + " VARCHAR," + COLUMN_PHONENUMBER + " VARCHAR " + ");";

    String[] userAllColumns = {OpenHelper.COLUMN_USERID, OpenHelper.COLUMN_FIRSTNAME, OpenHelper.COLUMN_IMAGE, OpenHelper.COLUMN_FAMILYID, OpenHelper.COLUMN_UPASSWORD, OpenHelper.COLUMN_PHONENUMBER};


    public static final String TABLE_TASK = "tbltasks";

    public static final String COLUMN_TASKID = "taskId";
    public static final String COLUMN_TFAMILYID = "tFamilyid";
    public static final String COLUMN_TOWNERID = "tOwnerId";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_TDONE = "tDoneOrNot";
    public static final String COLUMN_TMONTH = "tMonth";
    public static final String COLUMN_PERFORMERID = "peformer";
    public static final String COLUMN_FREQUENCY = "frequency";
    public static final String COLUMN_DAY = "day";


    private static final String CREATE_TABLE_TASK = "CREATE TABLE IF NOT EXISTS " + TABLE_TASK +
            "(" + COLUMN_TASKID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_TFAMILYID + " INTEGER," +
            COLUMN_TOWNERID + " INTEGER," +
            COLUMN_DESCRIPTION + " VARCHAR," +
            COLUMN_TDONE + " INTEGER," +
            COLUMN_TMONTH + " INTEGER," +
            COLUMN_PERFORMERID + " INTEGER," +
            COLUMN_FREQUENCY + " INTEGER," +
            COLUMN_DAY + " INTEGER" +
            ");";

    String[] taskAllColumns = {OpenHelper.COLUMN_TASKID, OpenHelper.COLUMN_TFAMILYID, OpenHelper.COLUMN_TOWNERID, OpenHelper.COLUMN_DESCRIPTION,
            OpenHelper.COLUMN_TDONE, OpenHelper.COLUMN_TMONTH, OpenHelper.COLUMN_PERFORMERID, OpenHelper.COLUMN_FREQUENCY, OpenHelper.COLUMN_DAY};


    public static final String TABLE_SHOPPING = "tblshopping";

    public static final String COLUMN_SHOPPINGID = "ShoppingItemId";
    public static final String COLUMN_SFAMILYID = "sFamilyid";
    public static final String COLUMN_SOWNERID = "sOwnerId";
    public static final String COLUMN_PRODUCT = "product";
    public static final String COLUMN_SDONE = "sDoneOrNot";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_AMOUNT = "amount";


    private static final String CREATE_TABLE_SHOPPING = "CREATE TABLE IF NOT EXISTS " + TABLE_SHOPPING +
            "(" + COLUMN_SHOPPINGID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_SFAMILYID + " INTEGER," +
            COLUMN_SOWNERID + " INTEGER," +
            COLUMN_PRODUCT + " VARCHAR," +
            COLUMN_SDONE + " INTEGER," +
            COLUMN_TYPE + " VARCHAR," +
            COLUMN_AMOUNT + " INTEGER" +
            ");";

    String[] shoppingAllColumns = {OpenHelper.COLUMN_SHOPPINGID, OpenHelper.COLUMN_SFAMILYID, OpenHelper.COLUMN_SOWNERID, OpenHelper.COLUMN_PRODUCT,
            OpenHelper.COLUMN_SDONE, OpenHelper.COLUMN_TYPE, OpenHelper.COLUMN_AMOUNT};


    public static final String TABLE_EVENT = "tblevent";

    public static final String COLUMN_EVENTID = "EventId";
    public static final String COLUMN_EFAMILYID = "eFamilyid";
    public static final String COLUMN_EOWNERID = "eOwnerId";
    public static final String COLUMN_EVENTNAME = "EventName";
    public static final String COLUMN_EDONE = "eDoneOrNot";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_MONTH = "month";
    public static final String COLUMN_EDAY = "eday";
    public static final String COLUMN_MINUTE = "minute";
    public static final String COLUMN_HOUR = "hour";


    private static final String CREATE_TABLE_EVENT = "CREATE TABLE IF NOT EXISTS " + TABLE_EVENT +
            "(" + COLUMN_EVENTID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_EFAMILYID + " INTEGER," +
            COLUMN_EOWNERID + " INTEGER," +
            COLUMN_EVENTNAME + " VARCHAR," +
            COLUMN_EDONE + " INTEGER," +
            COLUMN_YEAR + " INTEGER," +
            COLUMN_MONTH + " INTEGER," +
            COLUMN_EDAY + " INTEGER," +
            COLUMN_MINUTE + " INTEGER," +
            COLUMN_HOUR + " INTEGER" +
            ");";

    String[] eventAllColumns = {OpenHelper.COLUMN_EVENTID, OpenHelper.COLUMN_EFAMILYID, OpenHelper.COLUMN_EOWNERID, OpenHelper.COLUMN_EVENTNAME,
            OpenHelper.COLUMN_EDONE, OpenHelper.COLUMN_YEAR, OpenHelper.COLUMN_MONTH, OpenHelper.COLUMN_EDAY, OpenHelper.COLUMN_MINUTE, OpenHelper.COLUMN_HOUR};


    SQLiteDatabase database;


    public OpenHelper(@Nullable Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
        Log.d("data", "ctor ");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_FAMILY);
        sqLiteDatabase.execSQL(CREATE_TABLE_USERS);
        sqLiteDatabase.execSQL(CREATE_TABLE_TASK);
        sqLiteDatabase.execSQL(CREATE_TABLE_SHOPPING);
        sqLiteDatabase.execSQL(CREATE_TABLE_EVENT);

        Log.d("data", "table created " + CREATE_TABLE_FAMILY);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FAMILY);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPPING);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT);
        onCreate(sqLiteDatabase);
    }

    public void open() {
        database = this.getWritableDatabase();
        Log.d("data", "database connection open");

    }







    /* Family */


    //טענת כניסה: משתנה מסוג משפחה  טענת יציאה: הוספת המשפחה למבנה נתונים
    public Family saveFamily(Family f) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_FNAME, f.getName());
        values.put(COLUMN_FPASSWORD, f.getPassword());
        long insertid = database.insert(OpenHelper.TABLE_FAMILY, null, values);
        Log.d("data", "family " + insertid + " insert to database");
        f.setId(insertid);
        return f;

    }


    //טענת כניסה: שם וסיסמה של משפחה    טענת יציאה: אם המשפחה קיימת, הפעולה תחזיר את הת.ז של המשפחה, אחרת תחזיר 1-
    public long findFamily(String family_name, String family_password) {

        ArrayList<Family> l = new ArrayList<Family>();
        this.open();
        Cursor cursor = database.query(OpenHelper.TABLE_FAMILY, familyAllColumns, COLUMN_FNAME + "=\"" + family_name + "\"" + " AND " + COLUMN_FPASSWORD + "=\"" + family_password + "\"", null, null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                long familyId = cursor.getLong(cursor.getColumnIndex(OpenHelper.COLUMN_ID));
                    this.close();
                    return familyId;
                }

            }

        this.close();
        return -1;

        }


    //טענת כניסה: ת.ז של משפחה קיימת    טענת יציאה: אם המשפחה קיימת, הפעולה תחזיר את המשפחה שהת.ז שייך לה, אחרת תחזיר null
    public Family findFamily(long familyId) {

        ArrayList<Family> l = new ArrayList<Family>();
        this.open();
        Cursor cursor = database.query(OpenHelper.TABLE_FAMILY, familyAllColumns, COLUMN_ID + "=" + familyId, null, null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_FNAME));
                String password = cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_FPASSWORD));
                Family f = new Family(name, password);
                f.setId(familyId);
                    this.close();
                    return f;

            }
        }

        this.close();
        return null;


    }


    //טענת כניסה: שם וסיסמה של משפחה    טענת יציאה: הפעולה בודקת אם המשפחה קיימת, במידה וכן מחזירה true אחרת מחזירה false
    public boolean familyExist(String family_name, String family_password) {

        ArrayList<Family> l = new ArrayList<Family>();
        this.open();
        Cursor cursor = database.query(OpenHelper.TABLE_FAMILY, familyAllColumns, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_FNAME));
                String password = cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_FPASSWORD));
                if (password.equals(family_password) && name.equals(family_name)) {
                    this.close();
                    return true;
                }

            }
        }

        this.close();
        return false;


    }









    /* User */


    //טענת כניסה: משתנה מסוג משתמש  טענת יציאה: הוספת המשתמש למבנה נתונים
    public User saveUser(User u) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRSTNAME, u.getFirstName());
        values.put(COLUMN_FAMILYID, u.getFamilyId());
        values.put(COLUMN_UPASSWORD, u.getPassword());
        values.put(COLUMN_IMAGE, u.getImage());
        values.put(COLUMN_PHONENUMBER, u.getPhoneNumber());
        /*long id = database.insert(TABLE_USERS, null, values); */
        long insertid = database.insert(TABLE_USERS, null, values);
        Log.d("data", "user " + insertid + " insert to database");
        u.setUserId(insertid);
        return u;
    }



    //טענת כניסה: ת.ז של משפחה  טענת יציאה: הפונקציה מחזירה את מספר בני המשפחה
    public int userCount(long family_id) {
        this.open();
        String query = "SELECT COUNT(*) "+" \n"+
                "FROM "+ OpenHelper.TABLE_USERS+"\n"+
                "WHERE "+COLUMN_FAMILYID + " = " + family_id;

        Cursor cursor = database.rawQuery(query,null);

        cursor.moveToFirst();
        int num= cursor.getInt(0);

        this.close();
        return num;
    }




    //טענת כניסה: ת.ז של משתמש  טענת יציאה: הפונקציה מחזירה משתנה מסוג משתמש, שהת.ז שייך לו
    public User findUser(long user_id) {

        ArrayList<User> l = new ArrayList<User>();
        this.open();
        Cursor cursor = database.query(OpenHelper.TABLE_USERS, userAllColumns, COLUMN_USERID + "=" + user_id, null, null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_FIRSTNAME));
                String _password = cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_UPASSWORD));
                long familyId = cursor.getLong(cursor.getColumnIndex(OpenHelper.COLUMN_FAMILYID));
                String image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE));
                String phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONENUMBER));
                    User u = new User(name, _password, familyId, image, phone);
                    u.setUserId(user_id);
                    this.close();
                    return u;

            }
        }
        this.close();
        return null;

    }

    /*public User getUser(long id) {

        this.open();
        Cursor cursor = database.query(OpenHelper.TABLE_USERS, userAllColumns, COLUMN_USERID + " = " + id, null, null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                long familyId = cursor.getLong(cursor.getColumnIndex(OpenHelper.COLUMN_FAMILYID));
                String _name = cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_FIRSTNAME));
                String password = cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_UPASSWORD));
                String image = cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_IMAGE));
                String phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONENUMBER));
                User u = new User(_name, password, familyId, image, phone);
                this.close();
                return u;
            }
        }
        this.close();
        return null;

    }

     */


    //טענת כניסה: שם וסיסמה של משתמש    אענת יציאה: הפעולה מחזירה true אם המשתמש קים ו-false אחרת
    public boolean userExist(String name, String password) {

        ArrayList<User> l = new ArrayList<User>();
        this.open();
        Cursor cursor = database.query(OpenHelper.TABLE_USERS, userAllColumns, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String _name = cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_FIRSTNAME));
                String _password = cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_UPASSWORD));
                if (password.equals(_password) && name.equals(_name)) {
                    this.close();
                    return true;
                }

            }
        }
        this.close();
        return false;


    }


    //טענת כניסה: שם וסיסמה של משתמש    טענת יציאה: אם המשתמש קיים, הפעולה מחזירה את הת.ז של המשתמש, אחרת מחזירה 1-
    public long return_user_id(String name, String password) {
        ArrayList<User> l = new ArrayList<User>();
        this.open();
        Cursor cursor = database.query(OpenHelper.TABLE_USERS, userAllColumns, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String _name = cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_FIRSTNAME));
                String _password = cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_UPASSWORD));
                long user_id = cursor.getLong(cursor.getColumnIndex(OpenHelper.COLUMN_USERID));
                if (password.equals(_password) && name.equals(_name)) {
                    this.close();
                    return user_id;
                }
            }
        }
        this.close();
        return -1;
    }


    //טענת כניסה: ת.ז של משפחה ושם של משתמש     טענת יציאה: הפעולה מחזירה את המשתמש, אם לא קיים מחזירה null
    public User exist(String name, long familyid) {
        this.open();
        Cursor cursor = database.query(OpenHelper.TABLE_USERS, userAllColumns, COLUMN_FAMILYID + "=" + familyid, null, null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String _name = cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_FIRSTNAME));
                if (_name.equals(name)) {
                    String password = cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_UPASSWORD));
                    String image = cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_IMAGE));
                    String phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONENUMBER));
                    User u = new User(name, password, familyid, image, phone);
                    u.setUserId(cursor.getLong(cursor.getColumnIndex(COLUMN_USERID)));
                    this.close();
                    return u;
                }
            }
        }
        this.close();
        return null;

    }


    //טענת כניסה: ת.ז של משתמש      טענת יציאה: שמו של המשתמש
    public String getUserName(long id) {

        ArrayList<User> l = new ArrayList<User>();
        this.open();
        Cursor cursor = database.query(OpenHelper.TABLE_USERS, userAllColumns, COLUMN_USERID + "=" + id, null, null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String _name = cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_FIRSTNAME));
                this.close();
                return _name;
            }
        }
        this.close();
        return null;

    }


    //טענת כניסה: ת.ז ל משפחה ושם של משתמש      טענת יציאה: true אם המשתמש שייך למשפחה ו-false אחרת
    public boolean userExsintInTheFamily(long family_id, String name) {

        ArrayList<User> l = new ArrayList<User>();
        this.open();
        Cursor cursor = database.query(OpenHelper.TABLE_USERS, userAllColumns, COLUMN_FAMILYID + " = " + family_id, null, null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String _name = cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_FIRSTNAME));
                if (_name.equals(name)) {
                    this.close();
                    return true;
                }
            }
        }
        this.close();
        return false;

    }


    //טנעת כניסה: אובייקט מסוג משתמש    טאנת יציאה: הפעולה מעדכנת את תמונת המשתמש ב-database
    public long updateImage(User u) {

        ContentValues values = new ContentValues();
        values.put(OpenHelper.COLUMN_IMAGE, u.getImage());


        return database.update(OpenHelper.TABLE_USERS, values, COLUMN_USERID + "=" + u.getUserId(), null);

    }


    //טענת כניסה: ת.ז של משפחה  טענת יציאה: רשימה של כל בני המשפחה
    public ArrayList<User> getAllUsers(long family_id) {

        ArrayList<User> l = new ArrayList<User>();
        this.open();
        Cursor cursor = database.query(OpenHelper.TABLE_USERS, userAllColumns, COLUMN_FAMILYID + " = " + family_id, null, null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String _name = cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_FIRSTNAME));
                String password = cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_UPASSWORD));
                String image = cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_IMAGE));
                String phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONENUMBER));
                User u = new User(_name, password, family_id, image, phone);
                u.setUserId(cursor.getLong(cursor.getColumnIndex(COLUMN_USERID)));
                l.add(u);
            }

        }
        this.close();
        return l;

    }


    //טענת כניסה: ת.ז של משפחה  טענת יציאה: מערך של כל השמות של בני המשפחה
    public String[] getAllUsersNames(long family_id) {

        String [] users = new String [this.userCount(family_id)];

        this.open();
        Cursor cursor = database.query(OpenHelper.TABLE_USERS, userAllColumns, COLUMN_FAMILYID + " = " + family_id, null, null, null, null);

        if (cursor.getCount() > 0) {
            int i = 0;
            while (cursor.moveToNext()) {
                String _name = cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_FIRSTNAME));
                users[i] = _name;
                i++;
            }

        }
        this.close();
        return users;

    }







    /* Task */



    //טענת כניסה: אובייקט מסוג מטלה     טענת יציאה: הפעולה מוסיפה את המטלה לטבלת מטלות
    public Task saveTask(Task t) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TFAMILYID, t.getFamily_id());
        values.put(COLUMN_TOWNERID, t.getOwner_id());
        values.put(COLUMN_DESCRIPTION, t.getDescription());
        values.put(COLUMN_TDONE, t.getDone());
        values.put(COLUMN_TMONTH, t.gettMonth());
        values.put(COLUMN_PERFORMERID, t.getPerformer_id());
        values.put(COLUMN_FREQUENCY, Task.generateFrequency(t.getFrequency()));
        values.put(COLUMN_DAY, t.getDay());
        long insertid = database.insert(TABLE_TASK, null, values);
        Log.d("data", "task " + insertid + " insert to database");
        t.setId(insertid);
        return t;
    }



    /*//public int userCount(long family_id) {
    //
    //    this.open();
        String query = "SELECT COUNT(*) "+" \n"+
                "FROM "+ OpenHelper.TABLE_USERS+"\n"+
                "WHERE "+COLUMN_FAMILYID + " = " + family_id;

        Cursor cursor = database.rawQuery(query,null);
        Log.d("data", query);


        cursor.moveToFirst();
        int num= cursor.getInt(0);
/*
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                num++;
            }

        }*/
        //int c =  cursor.getCount();
  /*      this.close();

        return num;
    }



    public void delete_task(Task task){
        String query = "DELETE FROM " + OpenHelper.TABLE_TASK + " WHERE " + COLUMN_TASKID + " = " + task.getId();
    }
 */


    //טענת כניסה: ת.ז של מטלה       טענת יציאה: הפעולה מוחקת את המטלה מה-database
    public long deleteTaskByRow(long id)
    {
        return database.delete(OpenHelper.TABLE_TASK, OpenHelper.COLUMN_TASKID + "=" + id, null);
    }



    //טענת כניסה: ת.ז של משפחה      טענת יציאה: רשימה של כל המטלו של המשפחה
    public ArrayList<Item> getAllTasks(long family_id) {

        ArrayList<Item> l = new ArrayList<Item>();
        this.open();
        Cursor cursor = database.query(OpenHelper.TABLE_TASK, taskAllColumns, COLUMN_TFAMILYID + " = " + family_id + " AND " + COLUMN_TDONE + "=0" + " AND " + COLUMN_FREQUENCY + "=1", null, null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                long ownerId = cursor.getLong(cursor.getColumnIndex(OpenHelper.COLUMN_TOWNERID));
                String description = cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_DESCRIPTION));
                int done = cursor.getInt(cursor.getColumnIndex(OpenHelper.COLUMN_TDONE));
                int month = cursor.getInt(cursor.getColumnIndex(OpenHelper.COLUMN_TMONTH));
                long performerId = cursor.getLong(cursor.getColumnIndex(OpenHelper.COLUMN_PERFORMERID));
                int frequency = cursor.getInt(cursor.getColumnIndex(OpenHelper.COLUMN_FREQUENCY));
                int day = cursor.getInt(cursor.getColumnIndex(OpenHelper.COLUMN_DAY));
                Task t = new Task(family_id, ownerId, done, month, description, performerId, Task.generateFrequency(frequency), day);
                t.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_TASKID)));
                l.add(t);
            }

        }
        this.close();
        return l;
    }



   /* public ArrayList<Task> getFrequencyTasks(long family_id) {

        ArrayList<Task> l = new ArrayList<Task>();
        this.open();
        Cursor cursor = database.query(OpenHelper.TABLE_TASK, taskAllColumns, COLUMN_TFAMILYID + " = " + family_id + " AND " + COLUMN_TDONE + "=0" + " AND " + COLUMN_FREQUENCY + "!=1" , null, null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                long ownerId = cursor.getLong(cursor.getColumnIndex(OpenHelper.COLUMN_TOWNERID));
                String description = cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_DESCRIPTION));
                int done = cursor.getInt(cursor.getColumnIndex(OpenHelper.COLUMN_TDONE));
                int month = cursor.getInt(cursor.getColumnIndex(OpenHelper.COLUMN_TMONTH));
                long performerId = cursor.getLong(cursor.getColumnIndex(OpenHelper.COLUMN_PERFORMERID));
                int frequency = cursor.getInt(cursor.getColumnIndex(OpenHelper.COLUMN_FREQUENCY));
                int day = cursor.getInt(cursor.getColumnIndex(OpenHelper.COLUMN_DAY));
                Task t = new Task(family_id, ownerId, done, month, description, performerId, Task.generateFrequency(frequency), day);
                t.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_TASKID)));
                l.add(t);
            }

        }
        this.close();
        return l;
    }

    */


    //טענת כניסה: -     טענת יציאה: רשימה מסוג task של כל המטלות החזרתיות
    public ArrayList<Task> getAllFrequencyTasks() {

        ArrayList<Task> l = new ArrayList<Task>();
        this.open();
        Cursor cursor = database.query(OpenHelper.TABLE_TASK, taskAllColumns, COLUMN_TDONE + "=0" + " AND " + COLUMN_FREQUENCY + "!=1" , null, null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                long family_id = cursor.getLong(cursor.getColumnIndex(OpenHelper.COLUMN_TFAMILYID));
                long ownerId = cursor.getLong(cursor.getColumnIndex(OpenHelper.COLUMN_TOWNERID));
                String description = cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_DESCRIPTION));
                int done = cursor.getInt(cursor.getColumnIndex(OpenHelper.COLUMN_TDONE));
                int month = cursor.getInt(cursor.getColumnIndex(OpenHelper.COLUMN_TMONTH));
                long performerId = cursor.getLong(cursor.getColumnIndex(OpenHelper.COLUMN_PERFORMERID));
                int frequency = cursor.getInt(cursor.getColumnIndex(OpenHelper.COLUMN_FREQUENCY));
                int day = cursor.getInt(cursor.getColumnIndex(OpenHelper.COLUMN_DAY));
                Task t = new Task(family_id, ownerId, done, month, description, performerId, Task.generateFrequency(frequency), day);
                t.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_TASKID)));
                l.add(t);
            }

        }
        this.close();
        return l;
    }


    //טענת כניסה: -     טענת יציאה: רשימה מסוג item של כל המטלות החזרתיות
    public ArrayList<Item> getFrequencyTasks2(long family_id) {

        ArrayList<Item> l = new ArrayList<Item>();
        this.open();
        Cursor cursor = database.query(OpenHelper.TABLE_TASK, taskAllColumns, COLUMN_TFAMILYID + " = " + family_id + " AND " + COLUMN_TDONE + "=0" + " AND " + COLUMN_FREQUENCY + "!=1" , null, null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                long ownerId = cursor.getLong(cursor.getColumnIndex(OpenHelper.COLUMN_TOWNERID));
                String description = cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_DESCRIPTION));
                int done = cursor.getInt(cursor.getColumnIndex(OpenHelper.COLUMN_TDONE));
                int month = cursor.getInt(cursor.getColumnIndex(OpenHelper.COLUMN_TMONTH));
                long performerId = cursor.getLong(cursor.getColumnIndex(OpenHelper.COLUMN_PERFORMERID));
                int frequency = cursor.getInt(cursor.getColumnIndex(OpenHelper.COLUMN_FREQUENCY));
                int day = cursor.getInt(cursor.getColumnIndex(OpenHelper.COLUMN_DAY));
                Task t = new Task(family_id, ownerId, done, month, description, performerId, Task.generateFrequency(frequency), day);
                t.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_TASKID)));
                l.add(t);
            }

        }
        this.close();
        return l;
    }


    //טענת כניסה: ת.ז של משתמש      טענת יציאה: רשימה מסוג item של כל מטלות שמשוייכות למשתמש
    public ArrayList<Item> getMyTasks(long user_id) {

        ArrayList<Item> l = new ArrayList<Item>();
        this.open();
        Cursor cursor = database.query(OpenHelper.TABLE_TASK, taskAllColumns, COLUMN_TDONE + "=0" + " AND " + COLUMN_FREQUENCY + "=1", null, null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                long performer_id = cursor.getLong(cursor.getColumnIndex(OpenHelper.COLUMN_PERFORMERID));
                if (user_id == performer_id) {
                    long familyid = cursor.getLong(cursor.getColumnIndex(OpenHelper.COLUMN_TFAMILYID));
                    long ownerId = cursor.getLong(cursor.getColumnIndex(OpenHelper.COLUMN_TOWNERID));
                    String description = cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_DESCRIPTION));
                    int done = cursor.getInt(cursor.getColumnIndex(OpenHelper.COLUMN_TDONE));
                    int month = cursor.getInt(cursor.getColumnIndex(OpenHelper.COLUMN_TMONTH));
                    int frequency = cursor.getInt(cursor.getColumnIndex(OpenHelper.COLUMN_FREQUENCY));
                    int day = cursor.getInt(cursor.getColumnIndex(OpenHelper.COLUMN_DAY));
                    Task t = new Task(familyid, ownerId, done, month, description, performer_id, Task.generateFrequency(frequency), day);
                    t.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_TASKID)));
                    l.add(t);
                }
            }
            this.close();
            return l;
        }
        this.close();
        return l;

    }


    //טענת כניסה: ת.ז של משפחה      טענת יציאה: רשימה של כל המטלות שנעשו במשפחה
    public ArrayList<Item> getDoneTasks(long family_id) {

        ArrayList<Item> l = new ArrayList<Item>();
        this.open();
        Cursor cursor = database.query(OpenHelper.TABLE_TASK, taskAllColumns, COLUMN_TFAMILYID + " = " + family_id + " AND " + COLUMN_TDONE + "=1", null, null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                long ownerId = cursor.getLong(cursor.getColumnIndex(OpenHelper.COLUMN_TOWNERID));
                String description = cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_DESCRIPTION));
                int done = cursor.getInt(cursor.getColumnIndex(OpenHelper.COLUMN_TDONE));
                long performerId = cursor.getLong(cursor.getColumnIndex(OpenHelper.COLUMN_PERFORMERID));
                int frequency = cursor.getInt(cursor.getColumnIndex(OpenHelper.COLUMN_FREQUENCY));
                int day = cursor.getInt(cursor.getColumnIndex(OpenHelper.COLUMN_DAY));
                int month = cursor.getInt(cursor.getColumnIndex(OpenHelper.COLUMN_TMONTH));
                Task t = new Task(family_id, ownerId, done, month, description, performerId, Task.generateFrequency(frequency), day);
                t.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_TASKID)));
                l.add(t);
            }

        }
        this.close();
        return l;

    }


    //טענת כניסה: ת.ז של משתמש      טענת יציאה: הפעולה סוכמת את מספר המטלות שביצע המשתמש ומחזירה את מספר הנקודות שלו בחודש הנוכחי
    public int getNumOfPoints(long performer_id) {

        LocalDateTime localDateTime = LocalDateTime.now();
        int todayMonth = localDateTime.getMonthValue();  // 1 - 12

        this.open();
       // Cursor cursor = database.query(OpenHelper.TABLE_TASK, taskAllColumns, COLUMN_PERFORMERID + " = " + performer_id + " AND " + COLUMN_TDONE + "=1", null, null, null, null);
      //  Cursor cursor = database.query(OpenHelper.TABLE_TASK, taskAllColumns, COLUMN_PERFORMERID + " = " + performer_id + " AND " + COLUMN_TDONE + "=1", null, null, null, null);

        String query = "SELECT COUNT(*)"+" \n"+
                "FROM "+ OpenHelper.TABLE_TASK+"\n"+
                "WHERE "+COLUMN_PERFORMERID + " = " + performer_id + " AND " + COLUMN_TDONE + "=1" + " AND " + COLUMN_TMONTH + "=" + todayMonth;

        Cursor cursor = database.rawQuery(query,null);
        Log.d("data", query);


        cursor.moveToFirst();
        int num= cursor.getInt(0);
/*
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                num++;
            }

        }*/
        this.close();
        return num;

    }

    //טענת כניסה: ת.ז של משתמש      טענת יציאה: הפעולה סוכמת את מספר המטלות שביצע המשתמש ומחזירה את מספר הנקודות שלו בחודש הקודם
    public int pointsLastMonth(long performer_id) {

        LocalDateTime localDateTime = LocalDateTime.now();
        int todayMonth = localDateTime.getMonthValue();  // 1 - 12

        int lastMonth = 12;

        if (todayMonth != 1){
            lastMonth = todayMonth - 1;
        }

        this.open();
        // Cursor cursor = database.query(OpenHelper.TABLE_TASK, taskAllColumns, COLUMN_PERFORMERID + " = " + performer_id + " AND " + COLUMN_TDONE + "=1", null, null, null, null);
        //  Cursor cursor = database.query(OpenHelper.TABLE_TASK, taskAllColumns, COLUMN_PERFORMERID + " = " + performer_id + " AND " + COLUMN_TDONE + "=1", null, null, null, null);

        String query = "SELECT COUNT(*)"+" \n"+
                "FROM "+ OpenHelper.TABLE_TASK+"\n"+
                "WHERE "+COLUMN_PERFORMERID + " = " + performer_id + " AND " + COLUMN_TDONE + "=1" + " AND " + COLUMN_TMONTH + "=" + lastMonth;

        Cursor cursor = database.rawQuery(query,null);
        Log.d("data", query);


        cursor.moveToFirst();
        int num= cursor.getInt(0);
/*
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                num++;
            }

        }*/
        this.close();
        return num;

    }

    //טענת כניסה: אובייקט מסוג task     טענת יציאה: הפעולה מעדכנת את הנתונים של המטלה ב-database
    public long updateByRow(Task t) {

        ContentValues values = new ContentValues();
        values.put(OpenHelper.COLUMN_DESCRIPTION, t.getDescription());
        values.put(OpenHelper.COLUMN_PERFORMERID, t.getPerformer_id());
        values.put(OpenHelper.COLUMN_FREQUENCY, Task.generateFrequency(t.getFrequency()));
        values.put(OpenHelper.COLUMN_DAY, t.getDay());
        System.out.println(t);

        return database.update(OpenHelper.TABLE_TASK, values, COLUMN_TASKID + "=" + t.getId(), null);

    }

    //טענת כניסה: ת.ז של משפחה
    //טענת יציאה: הפעולה מחזירה את שמו של בן המשפחה המנצח בחודש הקודם
    public String newMonth(long familyId) {

        User [] users = new User [this.userCount(familyId)];
        System.out.println(users.length);

        this.open();
        Cursor cursor = database.query(OpenHelper.TABLE_USERS, userAllColumns, COLUMN_FAMILYID + " = " + familyId, null, null, null, null);

        if (cursor.getCount() > 0) {
            int i = 0;
            while (cursor.moveToNext()) {
                String _name = cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_FIRSTNAME));
                String password = cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_UPASSWORD));
                String image = cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_IMAGE));
                String phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONENUMBER));
                User u = new User(_name, password, familyId, image, phone);
                u.setUserId(cursor.getLong(cursor.getColumnIndex(COLUMN_USERID)));
                users[i] = u;
                i++;
            }

        }
        this.close();

        this.open();
        int maxCount= -1;
        String winner = "no one";
        for (int i = 0; i<users.length; i++){
            int num = this.pointsLastMonth(users[i].getUserId());
            System.out.println(num);
            if (num > maxCount){
                maxCount = num;
                winner = users[i].getFirstName();
            }
            else if (num == maxCount){
                winner += " and " + users[i].getFirstName();
            }
        }

        this.open();
        database.rawQuery("UPDATE " + TABLE_TASK + " SET " + COLUMN_TMONTH + "=" + LocalDateTime.now().getMonthValue() + " WHERE " + COLUMN_TFAMILYID + "=" + familyId + " AND " + COLUMN_TDONE + "=" + 0, null);


        return winner;

    }








   /* private static String convertToValuesString(String[] arr){
        String ret = "";
        for (int i = 0; i < arr.length-1; i++){
            ret += arr[i] + ",";
        }
        ret += arr[arr.length - 1] + " ";
        return ret;
    }


    */



    /* ShoppingItem     */

    public ShoppingItem saveShoppingItem(ShoppingItem s) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_SFAMILYID, s.getFamily_id());
        values.put(COLUMN_SOWNERID, s.getOwner_id());
        values.put(COLUMN_PRODUCT, s.getDescription());
        values.put(COLUMN_SDONE, s.getDone());
        values.put(COLUMN_TYPE, s.getType());
        values.put(COLUMN_AMOUNT, s.getAmount());
        long insertid = database.insert(TABLE_SHOPPING, null, values);
        Log.d("data", "user " + insertid + " insert to database");
        s.setId(insertid);
        return s;
    }


    public long deleteAllShoppingItem(String a)

    {
        if(a.equals("All")) {
            return database.delete(OpenHelper.TABLE_SHOPPING, null, null);
        }
        else{
            return database.delete(OpenHelper.TABLE_SHOPPING, OpenHelper.COLUMN_TYPE + "=\"" + a + "\"", null);
        }

    }

    public ArrayList<Item> getAllShoppingItems(long family_id) {

        ArrayList<Item> l = new ArrayList<Item>();
        this.open();
        Cursor cursor = database.query(OpenHelper.TABLE_SHOPPING, shoppingAllColumns, COLUMN_SFAMILYID + " = " + family_id + " AND " + COLUMN_SDONE + "=0", null, null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                long familyid = cursor.getLong(cursor.getColumnIndex(OpenHelper.COLUMN_SFAMILYID));
                if (familyid == family_id) {
                    long ownerId = cursor.getLong(cursor.getColumnIndex(OpenHelper.COLUMN_SOWNERID));
                    String description = cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_PRODUCT));
                    int done = cursor.getInt(cursor.getColumnIndex(OpenHelper.COLUMN_SDONE));
                    String type = cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_TYPE));
                    int amount = cursor.getInt(cursor.getColumnIndex(OpenHelper.COLUMN_AMOUNT));
                    ShoppingItem s = new ShoppingItem(familyid, ownerId, done, description, type, amount);
                    s.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_SHOPPINGID)));
                    l.add(s);
                }
            }
            this.close();
            return l;
        }
        this.close();
        return l;
    }


    public long updateByRow(ShoppingItem s) {

        ContentValues values = new ContentValues();
        values.put(OpenHelper.COLUMN_PRODUCT, s.getDescription());
        values.put(OpenHelper.COLUMN_AMOUNT, s.getAmount());
        values.put(OpenHelper.COLUMN_TYPE, s.getType());
        System.out.println(s);

        return database.update(OpenHelper.TABLE_SHOPPING, values, COLUMN_SHOPPINGID + "=" + s.getId(), null);

    }


    public ArrayList<Item> getAllType(String type, long family_id) {

        ArrayList<Item> l = new ArrayList<Item>();
        this.open();
        Cursor cursor = database.query(OpenHelper.TABLE_SHOPPING, shoppingAllColumns, COLUMN_SFAMILYID + " = " + family_id + " AND " + COLUMN_SDONE + "=0" + " AND " + COLUMN_TYPE + "=\"" + type + "\"", null, null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                    long familyid = cursor.getLong(cursor.getColumnIndex(OpenHelper.COLUMN_SFAMILYID));
                    long ownerId = cursor.getLong(cursor.getColumnIndex(OpenHelper.COLUMN_SOWNERID));
                    String description = cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_PRODUCT));
                    int done = cursor.getInt(cursor.getColumnIndex(OpenHelper.COLUMN_SDONE));
                    int amount = cursor.getInt(cursor.getColumnIndex(OpenHelper.COLUMN_AMOUNT));
                    ShoppingItem s = new ShoppingItem(familyid, ownerId, done, description, type, amount);
                    l.add(s);
            }
            this.close();
            return l;
        }
        this.close();
        return l;
    }


    public void setItemDone(Item item) {
        item.setUpdateDone(this);
    }


    public void setItemDone(Task t) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TDONE, 1);
        database.update(OpenHelper.TABLE_TASK, cv, COLUMN_TASKID + "=" + t.getId(), null);

    }

    public void setItemDone(ShoppingItem i) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_SDONE, 1);
        database.update(OpenHelper.TABLE_SHOPPING, cv, COLUMN_SHOPPINGID + "=" + i.getId(), null);

    }




    /* Events     */

    public Event saveEvent(Event e) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_EFAMILYID, e.getFamily_id());
        values.put(COLUMN_EOWNERID, e.getOwner_id());
        values.put(COLUMN_EVENTNAME, e.getDescription());
        values.put(COLUMN_EDONE, e.getDone());
        values.put(COLUMN_YEAR, e.getYear());
        values.put(COLUMN_MONTH, e.getMonth());
        values.put(COLUMN_EDAY, e.getDay());
        values.put(COLUMN_MINUTE, e.getMinutes());
        values.put(COLUMN_HOUR, e.getHour());
        long insertid = database.insert(TABLE_EVENT, null, values);
        Log.d("data", "user " + insertid + " insert to database");
        e.setId(insertid);
        return e;
    }


    public ArrayList<Event> getAllEvents(long family_id) {

        ArrayList<Event> l = new ArrayList<Event>();
        this.open();
        Cursor cursor = database.query(OpenHelper.TABLE_EVENT, eventAllColumns, COLUMN_EFAMILYID + " = " + family_id, null, null, null, null);

        LocalDateTime localDateTime = LocalDateTime.now();
        int this_year = localDateTime.getYear();
        int this_month = localDateTime.getMonthValue();
        int this_day = localDateTime.getDayOfMonth();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                long familyid = cursor.getLong(cursor.getColumnIndex(OpenHelper.COLUMN_EFAMILYID));
                long ownerId = cursor.getLong(cursor.getColumnIndex(OpenHelper.COLUMN_EOWNERID));
                String description = cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_EVENTNAME));
                int done = cursor.getInt(cursor.getColumnIndex(OpenHelper.COLUMN_EDONE));
                int year = cursor.getInt(cursor.getColumnIndex(OpenHelper.COLUMN_YEAR));
                int minute = cursor.getInt(cursor.getColumnIndex(OpenHelper.COLUMN_MINUTE));
                int day = cursor.getInt(cursor.getColumnIndex(OpenHelper.COLUMN_EDAY));
                int month = cursor.getInt(cursor.getColumnIndex(OpenHelper.COLUMN_MONTH));
                int hour = cursor.getInt(cursor.getColumnIndex(OpenHelper.COLUMN_HOUR));
                Event e = new Event(familyid, ownerId, done, description, year, month, day, hour, minute);
                e.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_EVENTID)));
                if (this_year < year) {
                    l.add(e);
                }
                else if (this_month < month && this_year == year){
                    l.add(e);
                }
                else if (this_day <= day && this_month == month && this_year == year) {
                    l.add(e);
                }
            }
        }
        this.close();
        return l;
    }


    public long updateByRow(Event e) {

        ContentValues values = new ContentValues();
        values.put(OpenHelper.COLUMN_EVENTNAME, e.getDescription());
        values.put(OpenHelper.COLUMN_YEAR, e.getYear());
        values.put(OpenHelper.COLUMN_MONTH, e.getMonth());
        values.put(OpenHelper.COLUMN_EDAY, e.getDay());
        values.put(OpenHelper.COLUMN_MINUTE, e.getMinutes());
        values.put(OpenHelper.COLUMN_HOUR, e.getHour());
        System.out.println(e);

        return database.update(OpenHelper.TABLE_EVENT, values, COLUMN_EVENTID + "=" + e.getId(), null);


    }





}














