package mydemo.com.mydemo.network;

/**
 * Created by Namita on 7/23/2018.
 */
public class OttoEvent {

    private int id;
    private String exception;
    private Object response;

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "OttoEvent{" +
                "id=" + id +
                ", exception='" + exception + '\'' +

                '}';
    }
}
