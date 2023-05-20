package id.ac.astra.polman.nim10027.user.api;

public class ApiUtils {
    // this is your server API server IP Address
    // (if you use localhost, look for your computer IP Address)
    public static final String API_URL = "http://10.8.3.96:8080/";

    // if you use emulator / AVD use this:
    // public static final String API_URL = "http://10.0.2.2:8080/";

    private ApiUtils() {

    }

    public static UserService getUserService() {
        return RetrofitClient.getClient(API_URL).create(UserService.class);
    }
}
