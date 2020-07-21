package com.lotex.android.currencyexchange;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.lotex.android.currencyexchange.model.CurrencyModel;

@Database(entities = {Currency.class}, version=2, exportSchema = false)
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

            currencyDao.insert(new Currency(0.0, 1.54887,
                    "CAD", "Canadian Dollar", "$", 0));

            currencyDao.insert(new Currency(0.0, 1.15279,
                    "USD", "US Dollar", "$", 1));

            currencyDao.insert(new Currency(0.0, 1,
                    "EUR", "Euro", "€", 2));

            currencyDao.insert(new Currency(0.0, 25.7162,
                    "MXN", "Mexican Peso", "$", 3));

            currencyDao.insert(new Currency(0.0, 26791.37,
                    "VND", "Vietnamese Dong", "₫", 4));

            currencyDao.insert(new Currency(0.0, 123.038,
                    "JPY", "Japanese Yen", "¥", 5));

            currencyDao.insert(new Currency(0.0, 8.04765,
                    "CNY", "Chinese Yuan", "¥", 6));

            currencyDao.insert(new Currency(0.0, 0.904480,
                    "GBP", "British Pound", "£", 7));

            currencyDao.insert(new Currency(0.0, 1.61806,
                    "AUD", "Australian Dollar", "$", 8));

            currencyDao.insert(new Currency(0.0, 1.07524,
                    "CHF", "Swiss Franc", "CHf", 9));

            currencyDao.insert(new Currency(0.0, 8.93630,
                    "HKD", "Hong Kong Dollar", "$", 10));

            currencyDao.insert(new Currency(0.0, 1.73678,
                    "NZD", "New Zealand Dollar", "$", 11));

            /*
            for (int i = 7; i < 20; i++) {
                currencyDao.insert(new Currency(0.0, Double.valueOf(i), "cCode " + i,
                        "cName " + i, "$", i));
            }

             */

            return null;
        }
    }
}
