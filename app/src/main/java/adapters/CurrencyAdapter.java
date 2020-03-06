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
    public void onBindViewHolder(@NonNull final CurrencyViewHolder holder, int position) {

        final CurrencyModel mCurrent = mCurrencyList.get(position);

        holder.myCustomEditTextListener.updatePosition(holder.getAdapterPosition());

        // Set hardcoded currency codes
        holder.currencyCode.setText(mCurrent.getCurrencyCode());
        holder.currencyName.setText(mCurrent.getCurrencyName());
        holder.converted.setText(mCurrent.getCurrencySign() +
                String.format("%.0f", mCurrent.getCurrencyVal()));
        holder.currencySign.setText(mCurrent.getCurrencySign());

        if (position == 0) {
            holder.currencyItem.setCardBackgroundColor(Color.BLUE);
            holder.currencyCode.setTextColor(Color.WHITE);
            holder.currencyName.setTextColor(Color.WHITE);
            holder.converted.setTextColor(Color.WHITE);
            holder.numToConvert.setTextColor(Color.WHITE);
            holder.converted.setVisibility(View.GONE);
            holder.numToConvert.setVisibility(View.VISIBLE);
            holder.currencyCompare.setVisibility(View.GONE);
            holder.currencySign.setVisibility(View.VISIBLE);

        } else {
            holder.currencyItem.setCardBackgroundColor(ContextCompat.getColor(MainActivity.getContext(), R.color.currency_background_default));
            holder.currencyCode.setTextColor(Color.BLACK);
            holder.currencyName.setTextColor(Color.BLACK);
            holder.converted.setTextColor(Color.BLACK);
            holder.numToConvert.setTextColor(Color.BLACK);
            holder.numToConvert.setVisibility(View.GONE);
            holder.currencyCompare.setVisibility(View.VISIBLE);
            holder.currencySign.setVisibility(View.GONE);

        }

    }

    @Override
    public int getItemCount() {
        return mCurrencyList.size();
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
        public void onClick(View v) {

            //Toast.makeText(MainActivity.getContext(), "position: " + getLayoutPosition(), Toast.LENGTH_SHORT).show();
            mRecyclerView.smoothScrollToPosition(0);
            int position = getLayoutPosition();
            Collections.swap(mCurrencyList, position, 0);
            mAdapter.notifyItemRangeChanged(0, position + 1);

        }
    }

    private class MyCustomEditTextListener implements TextWatcher {

        private int position;
        private TextView converted;

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

            /*

            if (!s.toString().equals("")) {
                mCurrencyList.get(position).setFromCurrencyVal(Double.parseDouble(s.toString()));
                mCurrencyList.get(position).setToCurrencyVal(mCurrencyList.get(position).convertCurrency());
                converted.setText(Double.toString(mCurrencyList.get(position).getToCurrencyVal()));
            } else {
                mCurrencyList.get(position).setFromCurrencyVal(0.0);
                converted.setText("0");
            }

             */


        }

    }

} // end of class
