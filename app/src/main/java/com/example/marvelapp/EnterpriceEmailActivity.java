package com.example.marvelapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.marvelapp.adapters.RecyclerEnterpriseEmailAdapter;
import com.example.marvelapp.models.EnterpriseEmail;
import com.example.marvelapp.util.Constants;
import com.example.marvelapp.util.VerticalSpacingItemDecorator;
import com.example.marvelapp.viewmodels.EnterpriceEmailViewModel;

import java.util.List;

public class EnterpriceEmailActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private RecyclerEnterpriseEmailAdapter enterpriseEmailAdapter;
    private ProgressBar mProgressBar;
    private EnterpriceEmailViewModel enterpriceEmailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);
        mProgressBar =  findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);
        initRecyclerView1();
        enterpriceEmailViewModel = ViewModelProviders.of(this).get(EnterpriceEmailViewModel.class);
        enterpriceEmailViewModel.init(this);

        dataChanges();
    }

    private void dataChanges()
    {
        enterpriceEmailViewModel.getEnterpriceEmails().observe(this, new Observer<List<EnterpriseEmail>>() {
            @Override
            public void onChanged(List<EnterpriseEmail> enterpriseEmails) {

                Log.d(Constants.TAG, "enterpriceEmailViewModel onChanged Called");

                mProgressBar.setVisibility(View.GONE);
                invalidateOptionsMenu();
                enterpriseEmailAdapter.setEnterpriseEmail(enterpriseEmails);
            }
        });

        enterpriceEmailViewModel.getCheckNetwork().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Log.d(Constants.TAG, "enterpriceEmailViewModel getCheckNetwork Called");
                if(!aBoolean) {

                    Toast.makeText(EnterpriceEmailActivity.this,"Network Not Available",Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(EnterpriceEmailActivity.this);
                    builder.setCancelable(false);
                    builder.setTitle("Network Error");
                    builder.setMessage("Network Not Available,Do you want to Exit?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                        }
                    });
                    builder.show();
                }
            }
        });
    }

    private void initRecyclerView1(){

        enterpriseEmailAdapter = new RecyclerEnterpriseEmailAdapter();
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(30);
        mRecyclerView.addItemDecoration(itemDecorator);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(enterpriseEmailAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_search);

        if(mProgressBar.isShown())
            searchItem.setVisible(false);
        else
            searchItem.setVisible(true);

        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                enterpriseEmailAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
