package mydemo.com.mydemo;

import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import com.squareup.otto.Subscribe;

import mydemo.com.mydemo.network.OttoEvent;
/**
 * Created by Namita on 7/23/2018.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Subscribe
    public void onOttoEventReceived(OttoEvent event) throws Exception {

            Object response = event.getResponse();

            if(response != null){
                onResponse(response, event.getId());
            }else{
                onErrorResponse(event.getException());
            }

    }

    protected abstract <T> void onResponse(T t, int id) throws Exception;
    protected void onErrorResponse(String errorMsg) throws Exception{

    }
}
