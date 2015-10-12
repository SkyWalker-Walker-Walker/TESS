package jp.co.skai.tess;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by sdcsdc on 2015/09/27.
 */
public class HttpAsyncLoader extends AsyncTaskLoader<String> {
    //private String url = null; //
    private HttpUriRequest _request = null;

    public HttpAsyncLoader(Context context) {
        super(context);
        //this.url = url;
    }
    public HttpAsyncLoader(Context context, String url, List<NameValuePair> postData){
        super(context);
        try {
            HttpPost request = new HttpPost(url);
            request.setEntity(new UrlEncodedFormEntity(postData, "UTF-8"));
            this._request = request;
        } catch(UnsupportedEncodingException e) {
            Log.e(this.getClass().getSimpleName(), e.getMessage());
        }

    }

//    @Override
//    public String loadInBackground() {
//       HttpClient httpClient = new DefaultHttpClient();
//
//        try {
//            String responseBody = httpClient.execute(new HttpPost(this.url),
//
//                    //
//                    new ResponseHandler<String>() {
//
//                        @Override
//                        public String handleResponse(HttpResponse response)
//                                throws ClientProtocolException,	IOException {
//
//                            //
//                            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()){
//                                return EntityUtils.toString(response.getEntity(), "UTF-8");
//                            }
//                            return null;
//                        }
//                    });
//
//            return responseBody;
//        }
//        catch (Exception e) {
//            Log.e(this.getClass().getSimpleName(),e.getMessage());
//      }
//      finally {
//           //
//            httpClient.getConnectionManager().shutdown();
//        }
//        return null;
//    }
@Override
public String loadInBackground() {
    HttpClient httpClient = new DefaultHttpClient();

    try {
        String responseBody = httpClient.execute(_request,

                new ResponseHandler<String>() {

                    @Override
                    public String handleResponse(HttpResponse response)
                            throws ClientProtocolException, IOException {

                        if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()){
                            return EntityUtils.toString(response.getEntity(), "UTF-8");
                        }
                        return null;
                    }
                });

        return responseBody;
    }
    catch (Exception e) {
        Log.d("MyApi",  "HttpAsyncLoader error:" + e.getMessage());
        Log.e(this.getClass().getSimpleName(),e.getMessage());
    }
    finally {
    // 通信終了時は、接続を閉じる
        httpClient.getConnectionManager().shutdown();
    }

    return null;
}


}
