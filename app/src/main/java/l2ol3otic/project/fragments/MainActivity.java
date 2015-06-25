package l2ol3otic.project.fragments;

import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    MyPageAdapter adapter;
    ViewPager pager;

    public static final String matrix = "http://www.borktor.com/rest/smartscreen.php";
    public int countData1 = 0;
    //private int ImageviewID[] = new int[]{R.id.imageView5};
    private int LayoutID[] = new int[]{R.id.lu1};
    public View rootView;

    public Integer[] IDs = new Integer[32];
    public String[] Urls = new String[32];
    public Integer[] Pages = new Integer[32];

    ActionBar actionBar;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        new SimpleTask().execute(matrix);


    }
    private class SimpleTask  extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            try {

                HttpGet httpGet = new HttpGet(urls[0]);
                HttpClient client = new DefaultHttpClient();

                HttpResponse response = client.execute(httpGet);

                int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode == 200) {
                    InputStream inputStream = response.getEntity().getContent();
                    BufferedReader reader = new BufferedReader
                            (new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result += line;
                    }
                }

            } catch (ClientProtocolException e) {

            } catch (IOException e) {

            }
            return result;
        }
        protected void onPostExecute(String jsonString)  {
            // Dismiss ProgressBar

            Data(jsonString);
        }
        private void Data(String jsonString) {
            Gson gson = new Gson();
            Bigsize blog = gson.fromJson(jsonString, Bigsize.class);
            List<Post> smartscreen = blog.getSmartscreen();

            StringBuilder builder1 = new StringBuilder();
            builder1.setLength(0);
            StringBuilder builder2 = new StringBuilder();
            builder2.setLength(0);
            StringBuilder builder3 = new StringBuilder();
            builder3.setLength(0);

            SharedPreferences sp = getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            for (Post post : smartscreen) {

                builder1.append(post.getIntro_id().toString());
                String ID = builder1.toString();
                IDs[countData1] = Integer.valueOf(ID);
                Log.i("ID", ID);

                builder2.append(post.getIntro_icon().toString());
                String URL = builder2.toString();
                Urls[countData1] = URL;
                editor.putString("URl" + countData1 , URL);
                editor.commit();
                Log.i("URL", URL);

                builder3.append(post.getPage().toString());
                String PAGE = builder3.toString();
                Pages[countData1] = Integer.valueOf(PAGE);
                Log.i("PAGE", PAGE);

                countData1++;

                builder1.setLength(0);
                builder2.setLength(0);
                builder3.setLength(0);


            }
            Log.i("Test", String.valueOf(countData1));

            adapter = new MyPageAdapter(getSupportFragmentManager());
            pager = (ViewPager) findViewById(R.id.pager);
            pager.setAdapter(adapter);
        }

    }

    private class PicTask  extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }

    private Bitmap fetchImage(String matrix) {
        try
        {
            URL url = new URL(matrix); // imageUrl
            // คือ url ของรูปภาพ
            InputStream input = null;
            URLConnection conn = url.openConnection();
            HttpURLConnection httpConn = (HttpURLConnection)conn;
            httpConn.setRequestMethod("GET");
            httpConn.setReadTimeout(40000); // ตั้งเวลา  connect timeout
            httpConn.connect(); // connection

            if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                input = httpConn.getInputStream(); // จับใส่ InputStream
            }
            Bitmap bitmap = BitmapFactory.decodeStream(input); //แปลงเป็น Bitmap
            input.close();
            httpConn.disconnect();
            return bitmap;

        }
        catch ( MalformedURLException e ){
            Log.d("fetchImage","MalformedURLException invalid URL: " + matrix );
        }catch ( IOException e ){
            Log.d("fetchImage","IO exception: " + e);
        }catch(Exception e){
            Log.d("fetchImage","Exception: " + e);
        }
        return null;
    }

}
