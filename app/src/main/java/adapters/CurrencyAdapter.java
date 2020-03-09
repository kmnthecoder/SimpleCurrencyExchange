package adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.lotex.android.currencyexchange.R;

import java.util.ArrayList;
import java.util.Collections;

import activities.MainActivity;
import models.CurrencyModel;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder> {

    private final ArrayList<CurrencyModel> mCurrencyList;
    private LayoutInflater mInflater;
    private RecyclerView mRecyclerView;
    private static final String LOG_TAG = "CurrencyAdapter";

    public CurrencyAdapter(Context context, ArrayList<CurrencyModel> mCurrencyList) {
        mInflater = LayoutInflater.from(context);
        this.mCurrencyList = mCurrencyList;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.currency_item, parent, false);
        return new CurrencyViewHolder(mItemView, this);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull final CurrencyViewHolder holder, int position) {

        final CurrencyModel mCurrent = mCurrencyList.get(position);

        holder.myCustomEditTextListener.updatePosition(holder.getAdapterPosition());

        // Set hardcoded currency codes
        holder.currencyCode.setText(mCurrent.getCurrencyCode());
        holder.currencyName.setText(mCurrent.getCurrencyName());

        //holder.numToConvert.setText(Double.toString(mCurrent.getAmount()));


        holder.converted.setText(mCurrent.getCurrencySign() +
                String.format("%.2f", mCurrent.getCurrencyVal()));


        holder.currencySign.setText(mCurrent.getCurrencySign());
        holder.currencyCompare.setText(1 + " " + mCurrent.getCurrencyCode() + " = " + 69 +
                " " + mCurrencyList.get(0).getCurrencyCode());

        changeCurrencyLook(holder, position);
    }

    @Override
    public int getItemCount() {
        return mCurrencyList.size();
    }

    // Changes currency look depending on if it is home currency or not
    public void changeCurrencyLook(CurrencyViewHolder holder, int position) {
        if (position == 0) {
            holder.currencyItem.setCardBackgroundColor(Color.BLUE);
            holder.currencyCode.setTextColor(Color.WHITE);
            holder.currencyName.setTextColor(Color.WHITE);
            holder.converted.setTextColor(Color.WHITE);
            holder.numToConvert.setTextColor(Color.WHITE);
            holder.converted.setVisibility(View.GONE);
            holder.currencyCompare.setVisibility(View.GONE);
            holder.numToConvert.setVisibility(View.VISIBLE);
            holder.currencySign.setVisibility(View.VISIBLE);

        } else {
            holder.currencyItem.setCardBackgroundColor(ContextCompat.getColor(MainActivity.getContext(), R.color.currency_background_default));
            holder.currencyCode.setTextColor(Color.BLACK);
            holder.currencyName.setTextColor(Color.BLACK);
            holder.converted.setTextColor(Color.BLACK);
            holder.numToConvert.setTextColor(Color.BLACK);
            holder.numToConvert.setVisibility(View.GONE);
            holder.currencySign.setVisibility(View.GONE);
            holder.currencyCompare.setVisibility(View.VISIBLE);
        }
    }

    class CurrencyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView currencyCode, currencyName, converted, currencyCompare, currencySign;
        private final CardView currencyItem;
        private final CurrencyAdapter mAdapter;
        private final EditText numToConvert;
        private MyCustomEditTextListener myCustomEditTextListener;

        public CurrencyViewHolder(final View itemView, CurrencyAdapter adapter) {
            super(itemView);
            itemView.setOnClickListener(this);

            currencyName = itemView.findViewById(R.id.tv_currency_name);
            currencyCode = itemView.findViewById(R.id.tv_currency_code);
            converted = itemView.findViewById(R.id.tv_converted_currency);
            numToConvert = itemView.findViewById(R.id.et_convert_amount);
            currencyItem = itemView.findViewById(R.id.cv_currency_item);
            currencyCompare = itemView.findViewById(R.id.tv_currency_compare);
            currencySign = itemView.findViewById(R.id.tv_currency_sign);

            this.myCustomEditTextListener = new MyCustomEditTextListener(converted);
            this.numToConvert.addTextChangedListener(myCustomEditTextListener);
            this.mAdapter = adapter;
        }

        @Override
        public void onClick(View v) { // Change home currency if clicked on

            int position = getLayoutPosition();

            if (position != 0) {

/*
                mCurrencyList.get(0).setCurrencyVal(mCurrencyList.get(0).convertAmount(
                        mCurrencyList.get(position).getCurrencyCode(),
                        mCurrencyList.get(position).getExchangeRate(),
                        mCurrencyList.get(position).getCurrencyVal()));

 */




                mRecyclerView.smoothScrollToPosition(0);
                Collections.swap(mCurrencyList, position, 0);

                EditText et = mRecyclerView.getChildAt(0).findViewById(R.id.et_convert_amount);
                et.setText(Double.toString(mCurrencyList.get(0).getCurrencyVal()));


                mAdapter.notifyItemRangeChanged(0, mAdapter.mCurrencyList.size());
                //mCurrencyList.get(position).setCurrencyVal(mCurrencyList);
                //mAdapter.notifyDataSetChanged();


            }








            //EditText et = mRecyclerView.getLayoutManager().findViewByPosition(0).findViewById(R.id.et_convert_amount);
            //itemView.setText(null);

        }
    }

    private class MyCustomEditTextListener implements TextWatcher {

        private int position;
        private TextView converted;
        private String homeCode;
        private double homeValue, amount;

        private MyCustomEditTextListener(TextView converted) {
            this.converted = converted;
        }

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {

            homeCode = mCurrencyList.get(0).getCurrencyCode();
            homeValue = mCurrencyList.get(0).getExchangeRate();




            if (!s.toString().equals("")) {

                amount = Double.valueOf(s.toString());
                mCurrencyList.get(0).setCurrencyVal(Double.valueOf(s.toString()));

                for (int i = 1; i < mCurrencyList.size(); i++) {
                    //mCurrencyList.get(i).setCurrencyVal(mCurrencyList.get(i).convertAmount());
                    mCurrencyList.get(i).setCurrencyVal(mCurrencyList.get(i).convertAmount(homeCode, homeValue, amount));
                }
                //mCurrencyList.get()
                //mCurrencyList.get(position).setFromCurrencyVal(Double.parseDouble(s.toString()));
                //mCurrencyList.get(position).setToCurrencyVal(mCurrencyList.get(position).convertCurrency());
                //converted.setText(Double.toString(mCurrencyList.get(position).getToCurrencyVal()));
            } else {
                //mCurrencyList.get(position).setCurrencyVal(0.0);
                //converted.setText("0");

                for (int i = 1; i < mCurrencyList.size(); i++) {
                    //mCurrencyList.get(i).setCurrencyVal(mCurrencyList.get(i).convertAmount());
                    mCurrencyList.get(i).setCurrencyVal(0.0);
                }

            }

            mRecyclerView.getAdapter().notifyDataSetChanged();

        }
    }

} // end of class
