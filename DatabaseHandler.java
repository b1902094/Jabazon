import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import com.vicky.jabazon.Util.User;
import com.vicky.jabazon.Util.Util;

public class DatabaseHandler extends SQLiteOpenHelper {


    public DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase Jab_DB) {
        //creating the user table
        String CREATE_USER_TABLE = "CREATE TABLE " +
                Util.USER_TABLE_NAME +
                " (" +
                Util.KEY_USERNAME + " TEXT, " +
                Util.KEY_PASSWORD + " TEXT )";

        Jab_DB.execSQL(CREATE_USER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase Jab_DB, int i, int i1) {
        Jab_DB.execSQL("DROP TABLE IF EXISTS " + Util.USER_TABLE_NAME);
        onCreate(Jab_DB);
    }

    public boolean isUsernameExists(String userName){
        try{
            SQLiteDatabase Jab_DB = this.getWritableDatabase();
            String sql = "SELECT * FROM " +
                    Util.USER_TABLE_NAME +
                    " WHERE " +
                    Util.KEY_USERNAME + " =? ";


            Cursor c = Jab_DB.rawQuery(sql, new String[]{userName});

            if(c != null){
                if(c.getCount() > 0){
                    c.close();
                    Jab_DB.close();
                    return true;
                }
            }

            c.close();
            Jab_DB.close();
            return false;

        } catch(SQLException ex){
            return false;
        }
    }

    public void addUser(@NonNull User user) {
        SQLiteDatabase Jab_DB = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(Util.KEY_USERNAME, user.getUserName());
        cv.put(Util.KEY_PASSWORD, user.getUserPassword());

        Jab_DB.insert(Util.USER_TABLE_NAME, null, cv);
        Jab_DB.close();
    }

    public User getUser(String userName, String userPassword){
        User user = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " +
                Util.USER_TABLE_NAME +
                " WHERE " +
                Util.KEY_USERNAME + " =? AND " + Util.KEY_PASSWORD + " =? ";

        Cursor c = db.rawQuery(sql, new String[]{userName, userPassword});

        if(c != null){
            if(c.getCount() > 0){
                user = new User();
                c.moveToFirst();
                user.setUserName(userName);
                user.setUserPassword(userPassword);
            }
            c.close();
            db.close();
        }

        return user;
    }
    
    public void updateUser(@NonNull User user){
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = Util.KEY_USERNAME + " =? ";
        ContentValues cv = new ContentValues();
        cv.put(Util.KEY_USERNAME, user.getUserName());
        cv.put(Util.KEY_PASSWORD, user.getUserPassword());
        db.update(Util.USER_TABLE_NAME, cv, whereClause, new String[]{String.valueOf(user.getUserName())});
        db.close();
    }

}
