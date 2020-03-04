package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lotex.android.currencyexchange.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import Adapters.CurrencyAdapter;
import Models.CurrencyModel;

public class MainActivity extends AppCompatActivity {

    private final ArrayList<CurrencyModel> mCurrencyList = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private CurrencyAdapter mAdapter;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);



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


        // Get handle to RecyclerView
        mRecyclerView = findViewById(R.id.recyclerview);
        // Create adapter and supply data for display
        mAdapter = new CurrencyAdapter(this, mCurrencyList);
        // Connect adapter with RecyclerView
        mRecyclerView.setAdapter(mAdapter);
        // Give RecyclerView default layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Add gestures to reorder items, and swipe to delete
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT |
                        ItemTouchHelper.DOWN | ItemTouchHelper.UP, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        int from = viewHolder.getAdapterPosition();
                        int to = target.getAdapterPosition();
                        Collections.swap(mCurrencyList, from, to);
                        mAdapter.notifyItemMoved(from, to);
                        return true;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        mCurrencyList.remove(viewHolder.getAdapterPosition());
                        mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

                    }
                });

        helper.attachToRecyclerView(mRecyclerView);

        fab = findViewById(R.id.fab_add);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "ccp", Toast.LENGTH_SHORT).show();
            }
        });

    }

}