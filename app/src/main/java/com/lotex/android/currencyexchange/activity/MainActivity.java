package com.lotex.android.currencyexchange.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = "MainActivityLog";

    public static final int ADD_CURRENCY_REQUEST = 1;

    private CurrencyViewModel currencyViewModel;

    private static MainActivity mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        final RecyclerView mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        final CurrencyAdapter mAdapter = new CurrencyAdapter();
        mRecyclerView.setAdapter(mAdapter);

        currencyViewModel = new ViewModelProvider(this).get(CurrencyViewModel.class);
        currencyViewModel.getAllCurrency().observe(this, new Observer<List<Currency>>() {
            @Override
            public void onChanged(List<Currency> currency) {
                mAdapter.submitList(currency);

                // testing
                for (int i = 0; i < mAdapter.getItemCount(); i++) {
                    Log.d(LOG_TAG, "Currency: " + mAdapter.getCurrencyAt(i).getCurrencyCode()
                            + " || Priority: " + mAdapter.getCurrencyAt(i).getPriority() + "\n");
                }

            }
        });

        // Change action bar layout
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        viewAttach();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.DOWN
                | ItemTouchHelper.UP, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {

                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();
                if (to != 0) {
                    Currency tempFrom = new Currency(mAdapter.getCurrencyAt(from), to);
                    Currency tempTo = new Currency(mAdapter.getCurrencyAt(to), from);
                    currencyViewModel.update(tempFrom, tempTo);
                }
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getAdapterPosition();

                currencyViewModel.delete(mAdapter.getCurrencyAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this,
                        mAdapter.getCurrencyAt(pos).getCurrencyCode()
                                + " deleted", Toast.LENGTH_SHORT).show();

                for (int i = pos; i < mAdapter.getItemCount(); i++) {
                    Currency temp = new Currency(mAdapter.getCurrencyAt(i), i - 1);
                    currencyViewModel.update(temp);
                }
            }

            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                if (viewHolder.getAdapterPosition() == 0) { // disable swiping the home currency
                    return 0;
                }
                return super.getMovementFlags(recyclerView, viewHolder);
            }
        }).attachToRecyclerView(mRecyclerView);

        mAdapter.setOnItemClickListener(new CurrencyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Currency currency, int position) {
                Currency tempHome = new Currency(mAdapter.getCurrencyAt(0), position);
                Currency tempFrom = new Currency(mAdapter.getCurrencyAt(position), 0);
                currencyViewModel.update(tempFrom, tempHome);
                mAdapter.submitList(null);
                mRecyclerView.smoothScrollToPosition(0);
            }
        });
    }

    public void viewAttach() {
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

    public static MainActivity getContext() {
        return mContext;
    }

} // end of class