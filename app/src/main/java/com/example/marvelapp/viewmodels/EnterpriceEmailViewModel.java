package com.example.marvelapp.viewmodels;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.marvelapp.models.EnterpriseEmail;
import com.example.marvelapp.repositories.EnterpriceEmailRepositry;
import com.example.marvelapp.util.Constants;

import java.util.List;

public class EnterpriceEmailViewModel extends ViewModel {

    private Context context;
    private MutableLiveData<List<EnterpriseEmail>> listLiveData;
    private MutableLiveData<Boolean> checkNetwork=new MutableLiveData<>();
    private EnterpriceEmailRepositry enterpriceEmailRepositry;

    public void init(Context context){

        Log.d(Constants.TAG, "EnterpriceEmailViewModel init Called");
        this.context=context;

        if(isNetworkAvailable() ) {
            checkNetwork.setValue(true);
        }
        else
        {
            checkNetwork.setValue(false);
            listLiveData=new MutableLiveData<>();
        }

        if(listLiveData != null || !isNetworkAvailable()){
            Log.d(Constants.TAG, "listLiveData is not null");
            return;
        }

       invokeApi();
    }

    private void invokeApi()
    {
        enterpriceEmailRepositry = EnterpriceEmailRepositry.getInstance();
        listLiveData = enterpriceEmailRepositry.getEnterpriceEmailList();
    }

    public LiveData<List<EnterpriseEmail>> getEnterpriceEmails(){
        Log.d(Constants.TAG, "EnterpriceEmailViewModel getEnterpriceEmails Called");
        return listLiveData;
    }

    public MutableLiveData<Boolean> getCheckNetwork() {
        return checkNetwork;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
