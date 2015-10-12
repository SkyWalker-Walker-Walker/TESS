package jp.co.skai.tess;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends ActionBarActivity implements LoaderCallbacks<String>{

    private Button btn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn = (Button)findViewById(R.id.btnLogin);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickLogin(view);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void onClickLogin(View view) {
        Bundle bundle = new Bundle();
        EditText txtId = (EditText)findViewById(R.id.Uid);
        EditText txtPd = (EditText)findViewById(R.id.Pwd);

        String id = txtId.getText().toString();
        String ps = txtPd.getText().toString();

        bundle.putString("url", "http://skaiweb.sakura.ne.jp/KSS/index.html");
        bundle.putString("Id", id);
        bundle.putString("Pw", ps);

        getLoaderManager().initLoader(0, bundle, this);

    }

    @Override
    public void onLoaderReset(Loader<String> loader){

    }
    @Override
    public void onLoadFinished(Loader<String> loader, String body){
        if (loader.getId() == 0) {
            if(body != null){
                try {
                    //JSONObject rootObject = new JSONObject(body);
                    //JSONObject itemObject = (JSONObject)rootObject.get("id");
                    JSONArray json = new JSONArray(body);
                    String strID = "";
                    String strName = "";
                    for (int i = 0; i < json.length(); i++){
                        JSONObject jsonResult = json.getJSONObject(i);

                        strID = jsonResult.getString("id");
                        strName = jsonResult.getString("name");

                    }
                    //String strID = itemObject.toString();

                    //itemObject = (JSONObject)rootObject.get("name");

                    //String strName = itemObject.toString();

                    Intent MainAct = new Intent(getApplicationContext(), MainActivity.class);

                    MainAct.putExtra("ShainID", strID);
                    MainAct.putExtra("ShainName", strName);

                    startActivity(MainAct);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("body", body);
            }
        }
    }
    @Override
    public Loader<String> onCreateLoader(int id, Bundle bundle) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("loginid", bundle.getString("Id")));
        params.add(new BasicNameValuePair("loginpass", bundle.getString("Pw")));

        HttpAsyncLoader loader = new HttpAsyncLoader(this, bundle.getString("url"), params);
        loader.forceLoad();
        return loader;
    }
}
