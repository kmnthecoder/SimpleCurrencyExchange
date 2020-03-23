package com.lotex.android.currencyexchange;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CurrencyDao {

    @Insert
    void insert(Currency currency);

    @Update
    void update(Currency... currency);

    @Delete
    void delete(Currency currency);

    @Query("DELETE FROM currency_table")
    void deleteAllCurrency();

    @Query("SELECT * FROM currency_table ORDER BY priority ASC")
    LiveData<List<Currency>> getAllCurrency();
}
