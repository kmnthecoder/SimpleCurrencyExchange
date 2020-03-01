package Models;

public class CurrencyModel {

    private double mFromCurrencyVal;
    private double mToCurrencyVal;
    private double mExchangeRate;
    private String mToCurrencyCode;
    private String mFromCurrencyCode;

    public CurrencyModel(double mFromCurrencyVal, double mToCurrencyVal, double mExchangeRate,
                         String mFromCurrencyCode, String mToCurrencyCode) {
        this.mFromCurrencyVal = mFromCurrencyVal;
        this.mToCurrencyVal = mToCurrencyVal;
        this.mExchangeRate = mExchangeRate;
        this.mFromCurrencyCode = mFromCurrencyCode;
        this.mToCurrencyCode = mToCurrencyCode;
    }

    public double convertCurrency() {
        return mFromCurrencyVal * mExchangeRate;
    }

    public double getFromCurrencyVal() {
        return mFromCurrencyVal;
    }

    public void setFromCurrencyVal(double mFromCurrencyVal) {
        this.mFromCurrencyVal = mFromCurrencyVal;
    }

    public double getToCurrencyVal() {
        return mToCurrencyVal;
    }

    public void setToCurrencyVal(double mToCurrencyVal) {
        this.mToCurrencyVal = mToCurrencyVal;
    }

    public double getExchangeRate() {
        return mExchangeRate;
    }

    public void setExchangeRate(double mExchangeRate) {
        this.mExchangeRate = mExchangeRate;
    }

    public String getToCurrencyCode() {
        return mToCurrencyCode;
    }

    public void setToCurrencyCode(String mToCurrencyCode) {
        this.mToCurrencyCode = mToCurrencyCode;
    }

    public String getFromCurrencyCode() {
        return mFromCurrencyCode;
    }

    public void setFromCurrencyCode(String mFromCurrencyCode) {
        this.mFromCurrencyCode = mFromCurrencyCode;
    }
}
