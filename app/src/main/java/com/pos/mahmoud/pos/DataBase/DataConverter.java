package com.pos.mahmoud.pos.DataBase;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pos.mahmoud.pos.models.TransactionItem;

import java.lang.reflect.Type;

public class DataConverter {

    @TypeConverter
    public String fromTransactionItem(TransactionItem item) {
        if (item == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<TransactionItem>() {
        }.getType();
        String json = gson.toJson(item,type);
        return json;
    }

    @TypeConverter
    public TransactionItem toTransactionItem(String item) {
        if (item == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<TransactionItem>() {
        }.getType();
        TransactionItem itemob = gson.fromJson(item, type);
        return itemob;
    }
}
