package com.lotex.android.currencyexchange;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Entity(tableName = "currency_table")
public class Currency {

    @PrimaryKey
    @NonNull
    private String currencyCode;

    private String currencyName, currencySign, mFlagName;
    private double currencyVal;
    private double exchangeRate;
    private int flag;
    private int priority;

    public Currency(double currencyVal, double exchangeRate,
                         String currencyCode, String currencyName, String currencySign, int priority) {
        this.currencyVal = currencyVal;
        this.exchangeRate = exchangeRate;
        this.currencyCode = currencyCode;
        this.currencyName = currencyName;
        this.currencySign = currencySign;
        this.mFlagName = this.currencyCode.toLowerCase() + "_flag";
        this.priority = priority;
    }

    // copy constructor to set priorities
    public Currency(Currency currency, int priority) {
        this.currencyVal = currency.currencyVal;
        this.exchangeRate = currency.exchangeRate;
        this.currencyCode = currency.currencyCode;
        this.currencyName = currency.currencyName;
        this.currencySign = currency.currencySign;
        this.mFlagName = this.currencyCode.toLowerCase() + "_flag";
        this.priority = priority;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public double getCurrencyVal() {
        return currencyVal;
    }

    public void setCurrencyVal(double mFromCurrencyVal) {
        this.currencyVal = mFromCurrencyVal;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double mExchangeRate) {
        this.exchangeRate = mExchangeRate;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String mFromCurrencyCode) {
        this.currencyCode = mFromCurrencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String mCurrencyName) {
        this.currencyName = mCurrencyName;
    }

    public String getCurrencySign() {
        return currencySign;
    }

    public void setCurrencySign(String mCurrencySign) {
        this.currencySign = mCurrencySign;
    }

    public double convertAmount(String homeCode, double homeValue, double amount) {
        if (Objects.equals(homeCode, "EUR")) {
            return round(exchangeRate * amount, 2);
        } else {
            return round(exchangeRate / homeValue * amount, 2);
        }
    }

    public double compareAmount(String homeCode, double homeValue) {
        if (Objects.equals(homeCode, "EUR")) {
            return round(1 / exchangeRate, 2);
        } else {
            return round(homeValue / exchangeRate, 2);
        }
    }

    public String getFlagName() {
        return mFlagName;
    }

    public void setFlagName(String mFlagName) {
        this.mFlagName = mFlagName;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
