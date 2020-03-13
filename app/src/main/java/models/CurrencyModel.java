package models;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class CurrencyModel {

    private String mCurrencyName, mCurrencySign, mFlagName;
    private double mCurrencyVal;

    //@SerializedName("")
    private String mCurrencyCode;


    private double mExchangeRate;

    private int mFlag;

    public CurrencyModel(double mCurrencyVal, double mExchangeRate,
                         String mCurrencyCode, String mCurrencyName) {
        this.mCurrencyVal = mCurrencyVal;
        this.mExchangeRate = mExchangeRate;
        this.mCurrencyCode = mCurrencyCode;
        this.mCurrencyName = mCurrencyName;
    }

    public CurrencyModel(double mCurrencyVal, double mExchangeRate,
                         String mCurrencyCode, String mCurrencyName, String mCurrencySign) {
        this.mCurrencyVal = mCurrencyVal;
        this.mExchangeRate = mExchangeRate;
        this.mCurrencyCode = mCurrencyCode;
        this.mCurrencyName = mCurrencyName;
        this.mCurrencySign = mCurrencySign;
        this.mFlagName = this.mCurrencyCode.toLowerCase() + "_flag";
    }

    public double getCurrencyVal() {
        return mCurrencyVal;
    }

    public void setCurrencyVal(double mFromCurrencyVal) {
        this.mCurrencyVal = mFromCurrencyVal;
    }

    public double getExchangeRate() {
        return mExchangeRate;
    }

    public void setExchangeRate(double mExchangeRate) {
        this.mExchangeRate = mExchangeRate;
    }

    public String getCurrencyCode() {
        return mCurrencyCode;
    }

    public void setCurrencyCode(String mFromCurrencyCode) {
        this.mCurrencyCode = mFromCurrencyCode;
    }

    public String getCurrencyName() {
        return mCurrencyName;
    }

    public void setCurrencyName(String mCurrencyName) {
        this.mCurrencyName = mCurrencyName;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public String getCurrencySign() {
        return mCurrencySign;
    }

    public void setCurrencySign(String mCurrencySign) {
        this.mCurrencySign = mCurrencySign;
    }

    public double convertAmount(String homeCode, double homeValue, double amount) {
        if (Objects.equals(homeCode, "EUR")) {
            return round(mExchangeRate * amount, 2);
        } else {
            return round(mExchangeRate / homeValue * amount, 2);
        }
    }

    public double compareAmount(String homeCode, double homeValue) {
        if (Objects.equals(homeCode, "EUR")) {
            return round(1 / mExchangeRate, 2);
        } else {
            return round(homeValue / mExchangeRate, 2);
        }
    }

    public String getFlagName() {
        return mFlagName;
    }

    public void setFlagName(String mFlagName) {
        this.mFlagName = mFlagName;
    }
}
