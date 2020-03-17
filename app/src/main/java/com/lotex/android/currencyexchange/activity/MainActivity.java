package com.lotex.android.currencyexchange.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lotex.android.currencyexchange.Currency;
import com.lotex.android.currencyexchange.CurrencyViewModel;
import com.lotex.android.currencyexchange.R;
import com.lotex.android.currencyexchange.adapter.CurrencyAdapter;
import com.lotex.android.currencyexchange.model.CurrencyModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int ADD_CURRENCY_REQUEST = 1;

    private CurrencyViewModel currencyViewModel;

    //private final ArrayList<Currency> mCurrencyList = new ArrayList<>();

    //private RecyclerView mRecyclerView;
    //private CurrencyAdapter mAdapter;
    //private FloatingActionButton fab;
    private static MainActivity mContext;

    /*
    private HashMap<String, Integer> flags = new HashMap<String, Integer>()
    {{
        put("USD", R.drawable.usd_flag);
    }};

     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;


        RecyclerView mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        final CurrencyAdapter mAdapter = new CurrencyAdapter();
        mRecyclerView.setAdapter(mAdapter);


        currencyViewModel = new ViewModelProvider(this).get(CurrencyViewModel.class);
        currencyViewModel.getAllCurrency().observe(this, new Observer<List<Currency>>() {
            @Override
            public void onChanged(List<Currency> currency) {
                // update RecyclerView
                //Toast.makeText(MainActivity.this, "onChanged", Toast.LENGTH_SHORT).show();
                mAdapter.setCurrency(currency);
            }
        });

        // Change action bar layout
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        //initDummyData();
        viewAttach();
        //recyclerViewSetup();
        //listTouchHelper(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.DOWN
                | ItemTouchHelper.UP, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
/*
                        int from = viewHolder.getAdapterPosition();
                        int to = target.getAdapterPosition();
                        if (to != 0) {
                            Collections.swap(mCurrencyList, from, to);

                            mAdapter.notifyItemMoved(from, to);
                        }




                return true;

 */

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //mCurrencyList.remove(viewHolder.getAdapterPosition());
                //mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                currencyViewModel.delete(mAdapter.getCurrencyAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Currency deleted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                if (viewHolder.getAdapterPosition() == 0) { // disable swiping the home currency
                    return 0;
                }
                return super.getMovementFlags(recyclerView, viewHolder);
            }
        }).attachToRecyclerView(mRecyclerView);
    }

    public void viewAttach() {
        //mRecyclerView = findViewById(R.id.recyclerview);

        FloatingActionButton fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_add:
                Toast.makeText(MainActivity.this, "Adding Currency", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, AddCurrencyActivity.class);
                startActivityForResult(intent, ADD_CURRENCY_REQUEST);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_CURRENCY_REQUEST && resultCode == RESULT_OK) {
            // add extras

            /*
            String code = data.getStringExtra(AddCurrencyActivity.EXTRA_CURRENCY);
            int priority = 0;

            Currency currency = new Currency(args);
            currencyViewModel.insert(currency);
             */


            Toast.makeText(this, "Currency added", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Currency add cancelled", Toast.LENGTH_SHORT).show();
        }

    }

    public void initDummyData() {
        /*
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

         */
    }

/*
    public void listTouchHelper(final CurrencyAdapter mAdapter) {
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
                        //mCurrencyList.remove(viewHolder.getAdapterPosition());
                        //mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                        currencyViewModel.delete(mAdapter.getCurrencyAt(viewHolder.getAdapterPosition()));
                        Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                        if (viewHolder.getAdapterPosition() == 0) { // disable swiping the home currency
                            return 0;
                        }
                        return super.getMovementFlags(recyclerView, viewHolder);
                    }
                });//.attachToRecyclerView(mRecyclerView);
        helper.attachToRecyclerView(mRecyclerView);
    }

 */








    /*

    public void recyclerViewSetup() {
        mAdapter = new CurrencyAdapter(this, mCurrencyList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

     */


    public static MainActivity getContext() {
        return mContext;
    }

} // end of class