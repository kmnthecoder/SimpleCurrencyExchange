package adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.lotex.android.currencyexchange.R;

import java.util.ArrayList;
import java.util.Collections;

import activities.MainActivity;
import de.hdodenhof.circleimageview.CircleImageView;
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
        // Set hardcoded currency codes
        holder.currencyCode.setText(mCurrent.getCurrencyCode());
        holder.currencyName.setText(mCurrent.getCurrencyName());
        holder.converted.setText(mCurrent.getCurrencySign() +
                String.format("%.2f", mCurrent.getCurrencyVal()));
        holder.currencySign.setText(mCurrent.getCurrencySign());
        holder.currencyCompare.setText(1 + " " + mCurrent.getCurrencyCode() + " = " +
                String.format("%.2f", mCurrent.compareAmount(mCurrencyList.get(0).getCurrencyCode(), mCurrencyList.get(0).getExchangeRate()))
                + " " + mCurrencyList.get(0).getCurrencyCode());
        holder.numToConvert.setText(Double.toString(mCurrent.getCurrencyVal()));

        holder.currencyFlag.setImageResource(MainActivity.getContext().getResources().getIdentifier(
                holder.mAdapter.mCurrencyList.get(position).getFlagName(),
                "drawable",
                MainActivity.getContext().getPackageName()));

        changeCurrencyLook(holder, position);
    }

    @Override
    public int getItemCount() {
        return mCurrencyList.size();
    }

    // Changes currency look depending on if it is home currency or not
    public void changeCurrencyLook(CurrencyViewHolder holder, int position) {

        /*
        if (!holder.mAdapter.mCurrencyList.get(position).getVisibility()) {
            holder.itemView.setVisibility(View.GONE);
        } else {
            holder.itemView.setVisibility(View.VISIBLE);
        }

         */

        if (position == 0) {
            holder.currencyItem.setCardBackgroundColor(ContextCompat.getColor(MainActivity.getContext(), R.color.home_currency));
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
            holder.currencyName.setTextColor(ContextCompat.getColor(MainActivity.getContext(), R.color.colorSecondaryText));
            holder.converted.setTextColor(Color.BLACK);
            holder.numToConvert.setTextColor(Color.BLACK);
            holder.converted.setVisibility(View.VISIBLE);
            holder.currencyCompare.setVisibility(View.VISIBLE);
            holder.numToConvert.setVisibility(View.GONE);
            holder.currencySign.setVisibility(View.GONE);
        }
    }

    class CurrencyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView currencyCode, currencyName, converted, currencyCompare, currencySign;
        private final CardView currencyItem;
        private final CurrencyAdapter mAdapter;
        private final EditText numToConvert;
        private final CircleImageView currencyFlag;

        private customEditorActionListener myCustomEditorActionListener;

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
            currencyFlag = itemView.findViewById(R.id.iv_currency_flag);

            this.myCustomEditorActionListener = new customEditorActionListener(numToConvert);
            numToConvert.setOnEditorActionListener(myCustomEditorActionListener);

            this.mAdapter = adapter;
        }

        @Override
        public void onClick(View v) { // Change home currency if clicked on
            int position = getLayoutPosition();
            if (position != 0) {
                mRecyclerView.smoothScrollToPosition(0);
                Collections.swap(mCurrencyList, position, 0);
                mAdapter.notifyItemRangeChanged(0, mAdapter.mCurrencyList.size());
            }
        }
    }

    private class customEditorActionListener implements TextView.OnEditorActionListener {

        private String homeCode;
        private double homeValue, amount;
        private EditText s;

        private customEditorActionListener(EditText s) {
            this.s = s;
        }

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                homeCode = mCurrencyList.get(0).getCurrencyCode();
                homeValue = mCurrencyList.get(0).getExchangeRate();

                if (!s.getText().toString().equals("")) {
                    amount = Double.valueOf(s.getText().toString());
                    mCurrencyList.get(0).setCurrencyVal(Double.valueOf(s.getText().toString()));
                    for (int i = 1; i < mCurrencyList.size(); i++) {
                        mCurrencyList.get(i).setCurrencyVal(mCurrencyList.get(i).convertAmount(homeCode, homeValue, amount));
                    }
                } else {
                    for (int i = 1; i < mCurrencyList.size(); i++) {
                        mCurrencyList.get(i).setCurrencyVal(0.0);
                    }
                }

                mRecyclerView.getAdapter().notifyDataSetChanged();
            }
            return handled;
        }
    }

} // end of class
