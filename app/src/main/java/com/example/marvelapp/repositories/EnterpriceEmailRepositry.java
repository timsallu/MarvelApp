package com.example.marvelapp.repositories;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.marvelapp.models.EnterpriseEmail;
import com.example.marvelapp.request.ServiceGenerator;
import com.example.marvelapp.util.Constants;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class EnterpriceEmailRepositry {

    private static EnterpriceEmailRepositry instance;

    private Context context;
    private MutableLiveData<List<EnterpriseEmail>> listLiveData=new MutableLiveData<>();

    public static EnterpriceEmailRepositry getInstance(){
        if(instance == null){
            instance = new EnterpriceEmailRepositry();
        }
        return instance;
    }

    public MutableLiveData<List<EnterpriseEmail>> getEnterpriceEmailList()
    {
        Log.d(Constants.TAG, "EnterpriceEmailRepositry getEnterpriceEmailList Called");

        try {
            getEnterpriseEmail()
                    .filter(new Predicate<List<EnterpriseEmail>>() {
                        @Override
                        public boolean test(List<EnterpriseEmail> enterpriseEmails) throws Exception {

                            Collections.sort(enterpriseEmails, new Comparator<EnterpriseEmail>() {
                                @Override
                                public int compare(EnterpriseEmail enterpriseEmail, EnterpriseEmail t1) {
                                    return enterpriseEmail.getTitle().compareTo(t1.getTitle());
                                }
                            });

                            return true;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<EnterpriseEmail>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            Log.d(Constants.TAG, "onSubscribe");
                        }

                        @Override
                        public void onNext(List<EnterpriseEmail> enterpriseEmails) {

                            listLiveData.setValue(enterpriseEmails);

                            for(EnterpriseEmail em  : enterpriseEmails) {
                                Log.d(Constants.TAG, "Name: " + em.getTitle());
                                Log.d(Constants.TAG, "getBy: " + em.getBy());
                                Log.d(Constants.TAG, "getNumbackers: " + em.getNumbackers());
                            }

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(Constants.TAG, "onError: " + e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                            Log.d(Constants.TAG, "All items are emitted!");

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listLiveData;
    }

    private Observable<List<EnterpriseEmail>> getEnterpriseEmail() {
        Log.d(Constants.TAG, "getEnterpriseEmail Called");

        try {
            return ServiceGenerator.getRequestApi()
                    .getEnterpriseEmail()
                    .map(new Function<List<EnterpriseEmail>, List<EnterpriseEmail>>() {
                        @Override
                        public List<EnterpriseEmail> apply(List<EnterpriseEmail> enterpriseEmails) throws Exception {
                            return enterpriseEmails;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    ;
        } catch (Exception e) {
            e.printStackTrace();
          return null;
        }
    }

}
