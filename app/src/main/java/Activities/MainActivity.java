package Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.lotex.android.currencyexchange.R;

import java.util.LinkedList;

import Adapters.CurrencyAdapter;

public class MainActivity extends AppCompatActivity {

    private final LinkedList<String> mCurrencyList = new LinkedList<>();

    private RecyclerView mRecyclerView;
    private CurrencyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Put initial data into the word list.
        for (int i = 0; i < 20; i++) {
            mCurrencyList.addLast("Currency " + i);
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
