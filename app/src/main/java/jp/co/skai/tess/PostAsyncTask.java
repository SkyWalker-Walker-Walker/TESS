package jp.co.skai.tess;

import android.os.AsyncTask;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by a-sekiya on 2015/09/14.
 */
public class PostAsyncTask extends AsyncTask<String, Integer, Integer> {

    @Override
    protected Integer doInBackground(String... contents) {

        String url = "";
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        ArrayList <NameValuePair> params = new ArrayList<NameValuePair>();
        params.add( new BasicNameValuePair("id", contents[0]));
        params.add( new BasicNameValuePair("pass", contents[1]));

        HttpResponse res = null;

        try{
            post.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
            res = httpclient.execute(post);
        } catch (IOException ex){
            ex.printStackTrace();
        }

        return Integer.valueOf(res.getStatusLine().getStatusCode());
    }

}
