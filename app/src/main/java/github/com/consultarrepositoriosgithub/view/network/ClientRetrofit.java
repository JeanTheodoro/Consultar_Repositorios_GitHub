package github.com.consultarrepositoriosgithub.view.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientRetrofit {

    private static final String URL_BASE = "https://api.github.com";
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit() {

        if(retrofit == null){

            retrofit = new Retrofit.Builder()
                    .baseUrl(URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retrofit;
    }
}
