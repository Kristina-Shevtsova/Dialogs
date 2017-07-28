package kristinashevtsova.dialogs.ServerConnections;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Kristina Shevtsova.
 */

public class RetrofitClass {

    public static RetrofitService getContacts() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://a11d.firebaseio.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(RetrofitService.class);
    }

}