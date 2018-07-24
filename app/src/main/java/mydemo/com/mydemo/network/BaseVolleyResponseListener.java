package mydemo.com.mydemo.network;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by Namita on 7/23/2018.
 */
public class BaseVolleyResponseListener<T> implements Response.Listener<T>, Response.ErrorListener {
    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(T response) {

    }
}
