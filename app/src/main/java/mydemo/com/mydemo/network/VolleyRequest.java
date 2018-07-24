package mydemo.com.mydemo.network;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Namita on 7/23/2018.
 */
public class VolleyRequest extends Request<JSONObject> {

private com.android.volley.Response.Listener<JSONObject> listener;


public VolleyRequest(int method,String url,  Response.Listener<JSONObject> responseListener,
        Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = responseListener;

        }


@Override
protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
        return null;
        }

@Override
public String getBodyContentType() {
        return "application/json; charset=utf-8";
        }

@Override
public byte[] getBody() throws AuthFailureError {

        return null;
        }

@Override
public Map<String, String> getHeaders() throws AuthFailureError {
        return super.getHeaders();
        }

@Override
protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
        String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

        return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
        return Response.error(new ParseError(e));
        } catch (JSONException je) {
        return Response.error(new ParseError(je));
        }
        }

@Override
protected void deliverResponse(JSONObject response) {
        listener.onResponse(response);
        }

        }
