package jp.co.skai.tess;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class TopActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);

        Button button_From = (Button)findViewById(R.id.btn_Fromhelp);
        button_From.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(TopActivity.this, "Fromヘルプを呼び出しました", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplication(),HelpActivity.class);
                startActivity(intent);
            }
        });

        Button button_To = (Button)findViewById(R.id.btn_Tohelp);
        button_To.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(TopActivity.this, "Toヘルプを呼び出しました", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplication(),HelpActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_top, menu);
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
}
