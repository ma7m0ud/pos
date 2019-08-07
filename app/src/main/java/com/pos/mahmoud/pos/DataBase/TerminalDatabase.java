package com.pos.mahmoud.pos.DataBase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.pos.mahmoud.pos.Helpers.TransactionModel;
import com.pos.mahmoud.pos.models.Configration;
import com.pos.mahmoud.pos.models.payee;

@Database(entities = {Configration.class,payee.class, TransactionModel.class}, version = 6, exportSchema = false)
public abstract  class TerminalDatabase extends RoomDatabase {
    public abstract DaoAccess daoAccess() ;
    private static final String DATABASE_NAME = "Terminal_db";
    private static volatile TerminalDatabase instance;

   public static synchronized TerminalDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }
    private static TerminalDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                TerminalDatabase.class,
                DATABASE_NAME).fallbackToDestructiveMigration().build();
    }


}
