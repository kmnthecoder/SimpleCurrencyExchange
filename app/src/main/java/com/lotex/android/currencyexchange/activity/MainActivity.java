package com.lotex.android.currencyexchange.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lotex.android.currencyexchange.R;
import com.lotex.android.currencyexchange.adapter.CurrencyAdapter;
import com.lotex.android.currencyexchange.model.CurrencyModel;



import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final ArrayList<CurrencyModel> mCurrencyList = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private CurrencyAdapter mAdapter;
    private FloatingActionButton fab;
    private static MainActivity mContext;

    private HashMap<String, Integer> flags = new HashMap<String, Integer>()
    {{
        put("USD", R.drawable.usd_flag);
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        // Change action bar layout
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        initDummyData();
        viewAttach();
        recyclerViewSetup();
        listTouchHelper();
    }

    public void viewAttach() {
        mRecyclerView = findViewById(R.id.recyclerview);
        fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_add:
                Toast.makeText(MainActivity.this, "ccp", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    public void initDummyData() {
        mCurrencyList.add(new CurrencyModel(0.0, 1.537144,
                "CAD", "Canadian Dollar", "$"));

        mCurrencyList.add(new CurrencyModel(0.0, 1.134985,
                "USD", "US Dollar", "$"));

        mCurrencyList.add(new CurrencyModel(0.0, 1,
                "EUR", "Euro", "€"));

        mCurrencyList.add(new CurrencyModel(0.0, 23.355529,
                "MXN", "Mexican Peso", "$"));

        mCurrencyList.add(new CurrencyModel(0.0, 26337.8948,
                "VND", "Vietnamese Dong", "₫"));

        mCurrencyList.add(new CurrencyModel(0.0, 118.324426,
                "JPY", "Japanese Yen", "¥"));

        mCurrencyList.add(new CurrencyModel(0.0, 7.867599,
                "CNY", "Chinese Yuan", "¥"));

        // Put initial data into the word list.
        for (int i = 1; i < 20; i++) {
            mCurrencyList.add(new CurrencyModel(0.0, Double.valueOf(i), "cCode " + i,
                    "cName " + i, "$"));
        }
    }

    public void listTouchHelper() {
        // Add gestures to reorder items, and swipe to delete
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.DOWN | ItemTouchHelper.UP,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        int from = viewHolder.getAdapterPosition();
                        int to = target.getAdapterPosition();
                        if (to != 0) {
                            Collections.swap(mCurrencyList, from, to);
                            mAdapter.notifyItemMoved(from, to);
                        }
                        return true;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        mCurrencyList.remove(viewHolder.getAdapterPosition());
                        mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                    }

                    @Override
                    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                        if (viewHolder.getAdapterPosition() == 0) { // disable swiping the home currency
                            return 0;
                        }
                        return super.getMovementFlags(recyclerView, viewHolder);
                    }
                });
        helper.attachToRecyclerView(mRecyclerView);
    }

    public void recyclerViewSetup() {
        mAdapter = new CurrencyAdapter(this, mCurrencyList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public static MainActivity getContext() {
        return mContext;
    }

} // end of class