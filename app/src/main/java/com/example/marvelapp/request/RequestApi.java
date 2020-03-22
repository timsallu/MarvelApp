package com.example.marvelapp.request;

import com.example.marvelapp.models.EnterpriseEmail;
import java.util.List;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface RequestApi {

    @GET("json/api.json")
    Observable<List<EnterpriseEmail>> getEnterpriseEmail();
}
