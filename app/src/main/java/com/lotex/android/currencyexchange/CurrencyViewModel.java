package com.lotex.android.currencyexchange;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CurrencyViewModel extends AndroidViewModel {
    private CurrencyRepository repository;
    private LiveData<List<Currency>> allCurrency;

    public CurrencyViewModel(@NonNull Application application) {
        super(application);
        repository = new CurrencyRepository(application);
        allCurrency = repository.getAllCurrency();
    }

    public void insert(Currency currency) {
        repository.insert(currency);
    }

    public void update(Currency... currency) {
        repository.update(currency);
    }

    public void delete(Currency currency) {
        repository.delete(currency);
    }

    public void deleteAll() {
        repository.deleteAllCurrency();
    }

    public LiveData<List<Currency>> getAllCurrency() {
        return allCurrency;
    }
}
