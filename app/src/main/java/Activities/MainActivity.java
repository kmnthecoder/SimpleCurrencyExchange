package Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.lotex.android.currencyexchange.R;

import java.util.ArrayList;
import java.util.LinkedList;

import Adapters.CurrencyAdapter;
import Models.CurrencyModel;

public class MainActivity extends AppCompatActivity {

    private final ArrayList<CurrencyModel> mCurrencyList = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private CurrencyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCurrencyList.add(new CurrencyModel(0.0, 0.0,
                0.745636, "CAD", "USD"));

        mCurrencyList.add(new CurrencyModel(0.0, 0.0,
                0.676187, "CAD", "EUR"));

        mCurrencyList.add(new CurrencyModel(0.0, 0.0,
                14.6246, "CAD", "MXN"));

        mCurrencyList.add(new CurrencyModel(0.0, 0.0,
                17360.58, "CAD", "VND"));

        mCurrencyList.add(new CurrencyModel(0.0, 0.0,
                17360.58, "CAD", "VND"));

        mCurrencyList.add(new CurrencyModel(0.0, 0.0,
                80.5959, "CAD", "JPY"));

        mCurrencyList.add(new CurrencyModel(0.0, 0.0,
                5.21321, "CAD", "CNY"));




        // Put initial data into the word list.
        for (int i = 0; i < 20; i++) {
            mCurrencyList.add(new CurrencyModel(i, i, i, "toCode " + i, "fromCode " + i));
        }


        // Get handle to RecyclerView
        mRecyclerView = findViewById(R.id.recyclerview);
        // Create adapter and supply data for display
        mAdapter = new CurrencyAdapter(this, mCurrencyList);
        // Connect adapter with RecyclerView
        mRecyclerView.setAdapter(mAdapter);
        // Give RecyclerView default layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}