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
import android.widget.RadioButton;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity implements LoaderCallbacks<String>{

    private Button btnLogout = null;
    private Button btnKakutei = null;
    private Button btnFromHelp = null;
    private Button btnToHelp = null;
    private List<String> paramNames = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnKakutei = (Button) findViewById(R.id.btnKakutei);
        btnFromHelp= (Button) findViewById(R.id.btnFromHelp);
        btnToHelp = (Button) findViewById(R.id.btnToHelp);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickLogout(view);
            }
        });
        btnKakutei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickKakutei(view);
            }
        });
        btnFromHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickFromHelp(view);
            }
        });
        btnToHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickToHelp(view);
            }
        });

        //ログイン画面より取得
        Intent intent = getIntent();
        String strShainId = intent.getStringExtra("ShainID");
        String strShainName = intent.getStringExtra("ShainName");
        TextView tvShainbangou = (TextView)findViewById(R.id.tvShainbangou);
        TextView tvShainmei = (TextView)findViewById(R.id.tvShainmei);
        tvShainbangou.setText(strShainId);
        tvShainmei.setText(strShainName);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void onClickLogout(View view) {
        //Intent intent = new Intent();
        //intent.putExtra("RESULT", result);
        //setResult(RESULT_OK, intent);
        finish();
    }

    public void onClickKakutei(View view) {
        Bundle bundle = new Bundle();
        TextView tvShainbangou = (TextView)findViewById(R.id.tvShainbangou);
        TextView tvShainmei = (TextView)findViewById(R.id.tvShainmei);
        EditText etRiyoubi = (EditText)findViewById(R.id.etRiyoubi);
        EditText etFrom = (EditText)findViewById(R.id.etFrom);
        EditText etTo = (EditText)findViewById(R.id.etTo);
        EditText etUnchin = (EditText)findViewById(R.id.etUnchin);
        EditText etYouto = (EditText)findViewById(R.id.etYouto);
        RadioButton rbKatamichi = (RadioButton)findViewById(R.id.rbKatamichi);
        RadioButton rbOufuku = (RadioButton)findViewById(R.id.rbOufuku);

        String strShainbangou = tvShainbangou.getText().toString();
        String strShainmei = tvShainmei.getText().toString();
        String strRiyoubi = etRiyoubi.getText().toString();
        String strFrom = etFrom.getText().toString();
        String strTo = etTo.getText().toString();
        String strUnchin = etUnchin.getText().toString();
        String strYouto = etYouto.getText().toString();
        String strKataou = " ";
        if (rbKatamichi.isChecked()) {
            strKataou = "0";
        }
        if (rbOufuku.isChecked()) {
            strKataou = "1";
        }

        bundle.putString("url", "http://skaiweb.sakura.ne.jp/KSS/index.html");
        bundle.putString("id", strShainbangou);
        bundle.putString("name", strShainmei);
        bundle.putString("startname", strFrom);
        bundle.putString("endname", strTo);
        bundle.putString("fare", strUnchin);
        bundle.putString("farereason", strYouto);
        bundle.putString("onewayflag", strKataou);

        paramNames.add("id");
        paramNames.add("name");
        paramNames.add("startname");
        paramNames.add("endname");
        paramNames.add("fare");
        paramNames.add("farereason");
        paramNames.add("onewaflag");

        getLoaderManager().initLoader(0, bundle, this);

    }

    public void onClickFromHelp(View view) {
        Intent intentHelpAct = new Intent(getApplicationContext(), Help2Activity.class);

        int intReqCode = 1001;

        startActivityForResult(intentHelpAct, intReqCode);
    }

    public void onClickToHelp(View view) {
        Intent intentHelpAct = new Intent(getApplicationContext(), Help2Activity.class);

        int intReqCode = 1002;

        startActivityForResult(intentHelpAct, intReqCode);
    }

    @Override
    public void onLoaderReset(Loader<String> loader){

    }

    @Override
    public void onLoadFinished(Loader<String> loader, String body){
        if (loader.getId() == 0) {
            if(body != null){
                Log.d("body", body);
            }
        }
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle bundle) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        Integer idx;
        String paramName, paramValue;

        for (idx=0; idx<paramNames.size(); idx++) {
            paramName = paramNames.get(idx);
            paramValue = bundle.getString(paramName);
            params.add(new BasicNameValuePair(paramName, paramValue));
        }

        HttpAsyncLoader loader = new HttpAsyncLoader(this, bundle.getString("url"), params);
        loader.forceLoad();
        return loader;
    }

    @Override
    public void onActivityResult( int requestCode, int resultCode, Intent intent )
    {
        // startActivityForResult()の際に指定した識別コードとの比較
        if( requestCode == 1001 ){

            // 返却結果ステータスとの比較
            if( resultCode == RESULT_OK ){

                // 返却されてきたintentから値を取り出す
                String str = intent.getStringExtra( "RESULT" );

                EditText etFrom = (EditText)findViewById(R.id.etFrom);

                etFrom.setText(str);
            }
        } else if( requestCode == 1002 ){
            // 返却結果ステータスとの比較
            if( resultCode == RESULT_OK ){

                // 返却されてきたintentから値を取り出す
                String str = intent.getStringExtra( "RESULT" );

                EditText etTo = (EditText)findViewById(R.id.etTo);

                etTo.setText(str);
            }
        }
    }
}
