package Adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lotex.android.currencyexchange.R;

import java.util.LinkedList;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder> {

    private final LinkedList<String> mCurrencyList;
    private LayoutInflater mInflater;

    public CurrencyAdapter(Context context, LinkedList<String> mCurrencyList) {
        mInflater = LayoutInflater.from(context);
        this.mCurrencyList = mCurrencyList;
    }

    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.currency_item, parent, false);
        return new CurrencyViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder holder, int position) {
        String mCurrent = mCurrencyList.get(position);
        holder.currencyItemView.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return mCurrencyList.size();
    }

    class CurrencyViewHolder extends RecyclerView.ViewHolder {

        public final TextView currencyItemView;
        final CurrencyAdapter mAdapter;

        public CurrencyViewHolder(View itemView, CurrencyAdapter adapter) {
            super(itemView);
            currencyItemView = itemView.findViewById(R.id.word);
            this.mAdapter = adapter;
        }

    }

}
