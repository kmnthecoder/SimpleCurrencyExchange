package activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lotex.android.currencyexchange.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import adapters.CurrencyAdapter;
import models.CurrencyModel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final ArrayList<CurrencyModel> mCurrencyList = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private CurrencyAdapter mAdapter;
    private FloatingActionButton fab;
    private static MainActivity mContext;

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
        mCurrencyList.add(new CurrencyModel(0.0, 0.745636,
                "CAD", "Canadian Dollar", "$"));

        mCurrencyList.add(new CurrencyModel(0.0, 0.745636,
                "USD", "US Dollar", "$"));

        mCurrencyList.add(new CurrencyModel(0.0, 0.676187,
                "EUR", "Euro", "€"));

        mCurrencyList.add(new CurrencyModel(0.0, 14.6246,
                "MXN", "Mexican Peso", "$"));

        mCurrencyList.add(new CurrencyModel(0.0, 17360.58,
                "VND", "Vietnamese Dong", "₫"));

        mCurrencyList.add(new CurrencyModel(0.0, 80.5959,
                "JPY", "Japanese Yen", "¥"));

        mCurrencyList.add(new CurrencyModel(0.0, 5.21321,
                "CNY", "Chinese Yuan", "¥"));

        // Put initial data into the word list.
        for (int i = 0; i < 20; i++) {
            mCurrencyList.add(new CurrencyModel(0.0, i, "currencyCode " + i,
                    "currencyName " + i));
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
        // Create adapter and supply data for display
        mAdapter = new CurrencyAdapter(this, mCurrencyList);
        // Connect adapter with RecyclerView
        mRecyclerView.setAdapter(mAdapter);
        // Give RecyclerView default layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public static MainActivity getContext() {
        return mContext;
    }

} // end of class