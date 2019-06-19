package com.example.financerepublicassign.Database;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;


import com.example.financerepublicassign.Model.StockModel;
import com.example.financerepublicassign.Model.UserModel;


@Database(entities = {StockModel.class, UserModel.class},version = 1,exportSchema = false)
public abstract class DBTools extends RoomDatabase {


    private static DBTools INSTANCE;

    public static synchronized DBTools getDatabase(Context context) {

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    DBTools.class, "data")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();

        }

        return INSTANCE;
    }

    public abstract DAO feedDao();


    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }


}
