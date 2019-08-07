package com.pos.mahmoud.pos.Network;

import com.pos.mahmoud.pos.Helpers.TransactionModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface APIService {
    @POST()
    Call<TransactionModel> purchase(@Url String url, @Body TransactionModel model);


}
