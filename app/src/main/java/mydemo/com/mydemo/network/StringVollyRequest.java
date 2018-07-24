package mydemo.com.mydemo.network;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.concurrent.Callable;

import mydemo.com.mydemo.commonutils.BusProvider;
import mydemo.com.mydemo.core.MyApplication;
import mydemo.com.mydemo.data.Constants;
import mydemo.com.mydemo.model.Listdata;
/**
 * Created by Namita on 7/23/2018.
 */
public class StringVollyRequest<T> implements Callable<Void> {

    public static final int ID_GET_ROW_DATA = Constants.INCREAMENT;
    private static final String TAG = StringVollyRequest.class.getSimpleName();
    public static int mRequestId;
    private String mRequestUrl;
    private Context _context;
    private BaseVolleyResponseListener<JSONObject> mBaseVolleyResponseListener = new BaseVolleyResponseListener<JSONObject>(){

        @Override
        public void onErrorResponse(VolleyError error) {
            super.onErrorResponse(error);
            String errorType = VolleyErrorHelper.getMessage(error);
            OttoEvent ottoEvent = new OttoEvent();
            ottoEvent.setId(mRequestId);
            ottoEvent.setException(errorType);
            BusProvider.getInstance().post(ottoEvent);
        }

        @Override
        public void onResponse(JSONObject response) {
            super.onResponse(response);
            OttoEvent ottoEvent = new OttoEvent();
            ottoEvent.setId(mRequestId);
            Gson gson = new Gson();
            if (mRequestId == ID_GET_ROW_DATA) {
               Listdata listData = gson.fromJson(response.toString(), TypeTokenParam.getTypeToken(mRequestId));
                ottoEvent.setResponse(listData);
            }
            BusProvider.getInstance().post(ottoEvent);
        }
    };
    public StringVollyRequest(Context context, String requestUrl, int reqId) {
        this.mRequestUrl = requestUrl;
        this.mRequestId = reqId;
        _context = context;

    }

    @Override
    public Void call() throws Exception {
        VolleyRequest stringRequest;

            stringRequest = new VolleyRequest(Request.Method.GET,
                    mRequestUrl,  mBaseVolleyResponseListener, mBaseVolleyResponseListener);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyApplication.getInstance().getRequestQueue().add(stringRequest);

        return null;
    }
}
