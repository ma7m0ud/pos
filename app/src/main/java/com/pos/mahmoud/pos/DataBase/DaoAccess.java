package com.pos.mahmoud.pos.DataBase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.pos.mahmoud.pos.Helpers.TransactionModel;
import com.pos.mahmoud.pos.models.Configration;
import com.pos.mahmoud.pos.models.payee;

import java.util.List;

@Dao
public interface DaoAccess {

    @Insert
    void insertConfigration (Configration config);

    @Query("SELECT * FROM Configration WHERE id = :id")
    Configration fetchConfigration (String id);

    @Update
    void updateConfigration (Configration config);

    @Delete
    void deleteConfigration (Configration config);

    @Insert
    void insertPayeeList (List<payee> payees);

    @Query("SELECT * FROM payee WHERE payeeName = :id")
    payee fetchPayee (String id);

    @Query("DELETE FROM payee")
     void DeleteAllPayee();

    @Insert
    void insertHistory (TransactionModel m);

    @Query("SELECT * FROM TransactionModel WHERE systemTraceAuditNumber = :id")
    TransactionModel fetchTransactionModel (int id);

    @Query("SELECT * FROM TransactionModel WHERE tranDateTime like :id")
    List<TransactionModel> fetchAllTransactionModel (String id);
}
