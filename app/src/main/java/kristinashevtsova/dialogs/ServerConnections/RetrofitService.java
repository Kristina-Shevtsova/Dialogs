package kristinashevtsova.dialogs.ServerConnections;

import java.util.List;

import kristinashevtsova.dialogs.User;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Kristina Shevtsova.
 */

public interface RetrofitService {
    @GET("users.json")
    Call<List<User>> getData();
}
