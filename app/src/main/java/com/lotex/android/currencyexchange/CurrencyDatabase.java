package com.lotex.android.currencyexchange;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.lotex.android.currencyexchange.model.CurrencyModel;

@Database(entities = {Currency.class}, version = 1)
public abstract class CurrencyDatabase extends RoomDatabase {

    private static CurrencyDatabase instance;

    public abstract CurrencyDao currencyDao();

    public static synchronized CurrencyDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    CurrencyDatabase.class, "currency_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private CurrencyDao currencyDao;

        private PopulateDbAsyncTask(CurrencyDatabase db) {
            currencyDao = db.currencyDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            currencyDao.insert(new Currency(0.0, 1.537144,
                    "CAD", "Canadian Dollar", "$", 1));

            currencyDao.insert(new Currency(0.0, 1.134985,
                    "USD", "US Dollar", "$", 0));

            currencyDao.insert(new Currency(0.0, 1,
                    "EUR", "Euro", "€", 0));

            currencyDao.insert(new Currency(0.0, 23.355529,
                    "MXN", "Mexican Peso", "$", 0));

            currencyDao.insert(new Currency(0.0, 26337.8948,
                    "VND", "Vietnamese Dong", "₫", 0));

            currencyDao.insert(new Currency(0.0, 118.324426,
                    "JPY", "Japanese Yen", "¥", 0));

            currencyDao.insert(new Currency(0.0, 7.867599,
                    "CNY", "Chinese Yuan", "¥", 0));

            return null;
        }
    }
}
