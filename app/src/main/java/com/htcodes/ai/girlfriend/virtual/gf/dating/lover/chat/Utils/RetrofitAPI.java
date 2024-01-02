package com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Utils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;


public interface RetrofitAPI {
    @GET
    Call<MsgModel> getMassage(@Url String str);
}
