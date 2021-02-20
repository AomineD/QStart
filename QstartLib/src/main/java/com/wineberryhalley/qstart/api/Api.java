package com.wineberryhalley.qstart.api;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wineberryhalley.qstart.R;
import com.wineberryhalley.qstart.base.User;
import com.wineberryhalley.qstart.ui.activity.LoginQStart;
import com.wineberryhalley.qstart.utils.Country;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import static com.wineberryhalley.qstart.utils.Qa.am_mwql;

public class Api {
    RequestQueue queue;
    private Activity activity;
    private String ak;
    protected Api(Activity activity, String ak){
queue = Volley.newRequestQueue(activity);
this.ak = ak;
this.activity = activity;
        SharedPreferences a = activity.getSharedPreferences("qkt", Context.MODE_PRIVATE);

setk(a.getString(am_mwql, ""));
    }

    String URL = "https://wineberryhalley.cloud/api/sqst/api/";
    String API_SECRECT_KEY;
    private void setk(String a){
        API_SECRECT_KEY = a;
    }
    String login = URL+"login";
    String signup = URL+"signup";
    String up = URL+"upImg";

    public static Api get(Activity activity, String ej){
      return new Api(activity, ej);
    }



    public interface StatusListener{
        void onLoad(User user);
        void onError(String erno);
    }

    public interface CheckListener{
        void Exist();
        void Available();
        void onError(String erno);
    }

    public interface CountryListener{
        void onLoad(ArrayList<Country> countries);
        void onError(String erno);
    }

    //SIGNUP
    public void signup(User user, final StatusListener listenerGenre){

        String url = signup;
     //   Log.e("MAIN", "sign: "+url );
        StringRequest jsonArrayRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String st) {

                try {
                    JSONObject response = new JSONObject(st);
               //     Log.e("MAIN", "onResponse: "+response.toString() );
if(success(response)) {
    User models = new User();

    models.status = response.getString("status").equals("success");
    models.user_id = response.getString("user_id");
    models.username = response.getString("name");
    models.imageUri = response.getString("img");
    models.gender = response.getString("gender");
    models.country = response.getString("country");

    listenerGenre.onLoad(models);
}else{
    listenerGenre.onError(handleError(response));
}

                } catch (JSONException e) {
                    //Log.e("MAIN", "onResponse BY GENRE: "+e.getMessage());
                    //  e.printStackTrace();
                    listenerGenre.onError(e.getMessage());
                }






                queue.getCache().clear();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listenerGenre.onError(error.getMessage());
                queue.getCache().clear();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> ma = getPostP();

  ma.put("usn", user.username);
  if(!user.country.isEmpty())
      ma.put("cot", user.country);

  if(!user.imageUri.isEmpty()){
      ma.put("img", user.imageUri);
  }

  String gende = user.gender;
if(!gende.equals("NONE")){
    if(gende.equals(activity.getString(R.string.male))){
        gende = "1";
    }else if(gende.equals(activity.getString(R.string.female))){
        gende = "0";
    }
}

                ma.put("gender", gende);
                return ma;
            }
        };

        queue.add(jsonArrayRequest);
    }

    //LOGIN AND AVAILIBITY
    public void login(String usr, final StatusListener listenerGenre){

        String url = login;
       //   Log.e("MAIN", "login: "+usr );
        StringRequest jsonArrayRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String responsea) {

          //    Log.e("MAIN", "onResponse: "+responsea );
                try {
                    JSONObject response = new JSONObject(responsea);
                    //Log.e("MAIN", "onResponse: "+response.has("status") );
                    if(response.getString("status").equals("success")) {
                        User models = new User();

                        models.status = response.getString("status").equals("success");
                        models.user_id = response.getString("user_id");
models.imageUri = response.getString("img");
models.country = response.getString("country");
models.gender = response.getString("gender");

                        models.username = response.getString("name");

                        listenerGenre.onLoad(models);
                    }else if(response.getString("status").equals("error")){
                        listenerGenre.onError(response.getString("data"));
                    }

                } catch (JSONException e) {
                    //Log.e("MAIN", "onResponse BY GENRE: "+e.getMessage());
                    //  e.printStackTrace();
                    listenerGenre.onError(e.getMessage());
                }






                queue.getCache().clear();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listenerGenre.onError(error.getMessage());
                queue.getCache().clear();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
             Map<String, String> map = getPostP();
             map.put("dam", usr);

                return map;
            }
        };

        queue.add(jsonArrayRequest);
    }

    //LOGIN AND AVAILIBITY
    public void checkUserName(String usr, final CheckListener listenerGenre){
        //    Log.e("MAIN", "login: "+url );
        StringRequest jsonArrayRequest = new StringRequest(Request.Method.POST, URL+"username", new Response.Listener<String>() {
            @Override
            public void onResponse(String responsea) {

                // Log.e("MAIN", "onResponse: "+responsea );
                try {
                    JSONObject response = new JSONObject(responsea);
                    //Log.e("MAIN", "onResponse: "+response.has("status") );
                    if(success(response)) {

                        if(response.getString("data").equals("exist")){
listenerGenre.Exist();
                        }else{
                            listenerGenre.Available();
                        }

                    }else{
                        listenerGenre.onError(response.getString("data"));
                    }

                } catch (JSONException e) {
                    //Log.e("MAIN", "onResponse BY GENRE: "+e.getMessage());
                    //  e.printStackTrace();
                    listenerGenre.onError(e.getMessage());
                }






                queue.getCache().clear();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listenerGenre.onError(error.getMessage());
                queue.getCache().clear();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = getPostP();
                map.put("nsame", usr);

                return map;
            }
        };

        queue.add(jsonArrayRequest);
    }

    public void getCountries(CountryListener listener){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "https://restcountries.eu/rest/v2/all", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson googleJson = new Gson();

                try {
                ArrayList<Country> countries = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject obj = response.getJSONObject(i);
                   Country country = googleJson.fromJson(obj.toString(), Country.class);
                     //   Log.e("MAIN", "onResponse: "+country.getName() );
                        JSONObject al = obj.getJSONObject("translations");
                        Type mapType = new TypeToken<Map<String, String>>(){}.getType();
                        Map<String, String> son = new Gson().fromJson(al.toString(), mapType);
                       /* for (String key : son.keySet()) {
                            String value = son.get(key);
                          //  Toast.makeText(ctx, "Key: " + key + " Value: " + value, Toast.LENGTH_LONG).show();
                          //  Log.e("MAIN", "onResponse: "+key+" value: "+value );
                        }*/
                    country.setLangObjs(son);

                    countries.add(country);
                }
                    listener.onLoad(countries);

                } catch (JSONException e) {
                    listener.onError("JSON EXC "+e.getMessage());
                    e.printStackTrace();
                }
                queue.getCache().clear();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
listener.onError(error.getMessage());
queue.getCache().clear();
            }
        });

        queue.add(jsonArrayRequest);

    }

    private String[] codeCountry(){
        return new String[]{

        };
    }

    private Map<String, String> getPostP(){
          Map<String, String> ma = new HashMap<>();
        ma.put("package", API_SECRECT_KEY);
        return ma;
    }

    private boolean success(JSONObject a) throws JSONException {
        if(a.getString("status").equals("success")){
            return true;
        }
        return false;
    }

    private String handleError(JSONObject a) throws JSONException {
        switch (a.getString("message")){
            case "Internal error":
                return activity.getString(R.string.error_internal);
            case "Deja de intentarlo, no robarás nada":
                return activity.getString(R.string.error_c);
            default:
                return activity.getString(R.string.error_general);
        }
    }

    public interface UploadListener{
        void onUploadSuccess(String url);
        void onUploadError(String erno);
    }

    public void sendImage( final String image, final String usern, final UploadListener listener) {
     StringRequest stringRequest = new StringRequest(Request.Method.POST, up, new Response.Listener<String>() {
         @Override
         public void onResponse(String response) {
           Log.e("MAIN", response);

             try {
                 JSONObject jsonObject = new JSONObject(response);
                 if(success(jsonObject)) {
                     String url = jsonObject.getString("url");
                     listener.onUploadSuccess(url);
                 }else{
                     listener.onUploadError(handleError(jsonObject));
                 }
             } catch (JSONException e) {
                 listener.onUploadError(e.getMessage());
                 e.printStackTrace();
             }
         }
     }, new Response.ErrorListener() {
         @Override
         public void onErrorResponse(VolleyError error) {
             listener.onUploadError(error.getMessage());
         }
     }) {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {

            Map<String, String> params = new Hashtable<String, String>();

            params.put("qsa_img", image);
            params.put("usrn", usern);
            params.put("package", API_SECRECT_KEY);

            return params;
        }
    };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        queue.add(stringRequest);
    }

}
