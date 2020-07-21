package com.lotex.android.currencyexchange;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Update;

import java.util.List;

public class CurrencyRepository {

    private CurrencyDao currencyDao;
    private LiveData<List<Currency>> allCurrency;

    public CurrencyRepository(Application application) {
        CurrencyDatabase database = CurrencyDatabase.getInstance(application);
        currencyDao = database.currencyDao();
        allCurrency = currencyDao.getAllCurrency();
    }

    public void insert(Currency currency) {
        new InsertCurrencyAsyncTask(currencyDao).execute(currency);
    }

    public void update(Currency... currency) {
        new UpdateCurrencyAsyncTask(currencyDao).execute(currency);
    }

    public void delete(Currency currency) {
        new DeleteCurrencyAsyncTask(currencyDao).execute(currency);
    }

    public void deleteAllCurrency() {
        new DeleteAllCurrencyAsyncTask(currencyDao).execute();
    }

    public LiveData<List<Currency>> getAllCurrency() {
        return allCurrency;
    }

    private static class InsertCurrencyAsyncTask extends AsyncTask<Currency, Void, Void> {
        private CurrencyDao currencyDao;

        private InsertCurrencyAsyncTask(CurrencyDao currencyDao) {
            this.currencyDao = currencyDao;
        }

        @Override
        protected Void doInBackground(Currency... currencies) {
            currencyDao.insert(currencies[0]);
            return null;
        }
    }

    private static class UpdateCurrencyAsyncTask extends AsyncTask<Currency, Void, Void> {
        private CurrencyDao currencyDao;

        private UpdateCurrencyAsyncTask(CurrencyDao currencyDao) {
            this.currencyDao = currencyDao;
        }

        @Override
        protected Void doInBackground(Currency... currencies) {

            for (int i = 0; i < currencies.length; i++) {
                currencyDao.update(currencies[i]);
            }

            return null;
        }
    }

    private static class DeleteCurrencyAsyncTask extends AsyncTask<Currency, Void, Void> {
        private CurrencyDao currencyDao;

        private DeleteCurrencyAsyncTask(CurrencyDao currencyDao) {
            this.currencyDao = currencyDao;
        }

        @Override
        protected Void doInBackground(Currency... currencies) {
            currencyDao.delete(currencies[0]);
            return null;
        }
    }

    private static class DeleteAllCurrencyAsyncTask extends AsyncTask<Void, Void, Void> {
        private CurrencyDao currencyDao;

        private DeleteAllCurrencyAsyncTask(CurrencyDao currencyDao) {
            this.currencyDao = currencyDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            currencyDao.deleteAllCurrency();
            return null;
        }
    }

}
