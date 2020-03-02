package Adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lotex.android.currencyexchange.R;

import java.util.ArrayList;

import Models.CurrencyModel;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder> {

    private final ArrayList<CurrencyModel> mCurrencyList;
    private LayoutInflater mInflater;

    public CurrencyAdapter(Context context, ArrayList<CurrencyModel> mCurrencyList) {
        mInflater = LayoutInflater.from(context);
        this.mCurrencyList = mCurrencyList;
    }

    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.currency_item, parent, false);
        return new CurrencyViewHolder(mItemView, this, new MyCustomEditTextListener());
    }

    @Override
    public void onBindViewHolder(@NonNull final CurrencyViewHolder holder, int position) {
        final CurrencyModel mCurrent = mCurrencyList.get(position);

        holder.myCustomEditTextListener.updatePosition(holder.getAdapterPosition());

        // Set hardcoded currency codes
        holder.fromCode.setText("To: " + mCurrent.getFromCurrencyCode());
        holder.toCode.setText("From: " + mCurrent.getToCurrencyCode());

        /*

        holder.numToConvert.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // convert to hardcoded currency immediately
                // TO DO: research how the different override methods work
                mCurrent.setFromCurrencyVal(Double.parseDouble(holder.numToConvert.getText().toString()));
                holder.converted.setText(Double.toString(mCurrent.convertCurrency()));
            }
        });

         */
    }

    @Override
    public int getItemCount() {
        return mCurrencyList.size();
    }

    class CurrencyViewHolder extends RecyclerView.ViewHolder {

        private final TextView fromCode, toCode, converted;
        private final CurrencyAdapter mAdapter;
        private final EditText numToConvert;
        private MyCustomEditTextListener myCustomEditTextListener;


        public CurrencyViewHolder(final View itemView, CurrencyAdapter adapter, MyCustomEditTextListener myCustomEditTextListener) {
            super(itemView);
            toCode = itemView.findViewById(R.id.tv_to_currency_code);
            fromCode = itemView.findViewById(R.id.tv_from_currency_code);
            converted = itemView.findViewById(R.id.tv_converted_currency);
            numToConvert = itemView.findViewById(R.id.et_convert_amount);

            this.myCustomEditTextListener = myCustomEditTextListener;
            this.numToConvert.addTextChangedListener(myCustomEditTextListener);

            this.mAdapter = adapter;

        }
    }

    private class MyCustomEditTextListener implements TextWatcher {

        private int position;

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
            // convert to hardcoded currency immediately
            // TO DO: research how the different override methods work
            //mCurrent.setFromCurrencyVal(Double.parseDouble(holder.numToConvert.getText().toString()));
            //holder.converted.setText(Double.toString(mCurrent.convertCurrency()));

            //mCurrencyList.get(position).setFromCurrencyVal(Double.parseDouble(holder.numToConvert.getText().toString()));
            //mCurrencyList.get(position).converted.setText(Double.toString(mCurrent.convertCurrency()));
        }

    }

}
