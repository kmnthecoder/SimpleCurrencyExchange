package Models;

import android.widget.ImageView;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyModel {

    private String mCurrencyName;
    private String mCurrencyCode;
    private String mCurrencySign;
    private double mCurrencyVal;

    private double mExchangeRate;

    private ImageView mFlag;

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
    }

    public double convertCurrency() {
        return round(mCurrencyVal * mExchangeRate, 2);
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

    public void setFromCurrencyCode(String mFromCurrencyCode) {
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
}
