package mydemo.com.mydemo.network;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import mydemo.com.mydemo.model.Listdata;

import static mydemo.com.mydemo.network.StringVollyRequest.ID_GET_ROW_DATA;

/**
 * Created by Namita on 7/23/2018.
 */

public class TypeTokenParam {

    public static Type getTypeToken(int id) {
        Type type = null;

        switch (id) {

            case ID_GET_ROW_DATA:
                type = new TypeToken<Listdata>() {
                }.getType();
                break;
        }
        return type;
    }
}
