package jp.co.skai.tess;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class Help2Activity extends FragmentActivity
        implements  View.OnClickListener, AdapterView.OnItemSelectedListener,
        LoaderManager.LoaderCallbacks<ArrayList<HashMap<String, String>>>  {

    /** 自身のインスタンス. */
    private final Help2Activity self = this;

    /** ProgressDialog. */
    private ProgressDialog mProgressDialog;

    /** エリア選択Spinner. */
    private Spinner mAreas;
    /** 都道府県選択Spinner. */
    private Spinner mPrefectures;
    /** 路線選択Spinner. */
    private Spinner mLines;
    /** 駅選択Spinner. */
    private Spinner mStations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help2);

        mAreas = (Spinner) findViewById(R.id.spiArea);
        mPrefectures = (Spinner) findViewById(R.id.spiPrefectures);
        mLines = (Spinner) findViewById(R.id.spiLines);
        mStations = (Spinner) findViewById(R.id.spiStation);

        mAreas.setOnItemSelectedListener(self);
        mPrefectures.setOnItemSelectedListener(self);
        mLines.setOnItemSelectedListener(self);

        Button btnSelect = (Button) findViewById(R.id.btnOK);

        btnSelect.setOnClickListener(self);

        getSupportLoaderManager().initLoader(ExpressLoader.AREAS, null, self);
    }

    // LoaderManager.LoaderCallbacks
    @Override
    public Loader<ArrayList<HashMap<String, String>>> onCreateLoader(int i, Bundle bundle) {
        mProgressDialog = ProgressDialog.show(self, "検索中", "しばらくお待ち下さい...");
        String name = bundle == null ? "" : bundle.getString("name");
        return new ExpressLoader(self, name);
    }

    @Override
    public void onLoadFinished(
            Loader<ArrayList<HashMap<String, String>>> loader,
            ArrayList<HashMap<String, String>> list) {
        mProgressDialog.dismiss();
        if (list == null) {
            // 何らかの理由により取得できない
            Toast.makeText(self, "エラー", Toast.LENGTH_SHORT).show();
            return;
        }
        // 取得したデータを対象のSpinnerにセットする
        SimpleAdapter adapter = new SimpleAdapter(
                self, list, android.R.layout.simple_spinner_item,
                new String[] {"name"}, new int[] {android.R.id.text1});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        switch (loader.getId()) {
            case ExpressLoader.AREAS:
                mAreas.setAdapter(adapter);
                break;
            case ExpressLoader.PREFECTURES:
                mPrefectures.setAdapter(adapter);
                break;
            case ExpressLoader.LINES:
                mLines.setAdapter(adapter);
                break;
            case ExpressLoader.STATIONS:
                mStations.setAdapter(adapter);
                break;
        }
        getSupportLoaderManager().destroyLoader(loader.getId());

    }
    @Override
    public void onLoaderReset(Loader<ArrayList<HashMap<String, String>>> loader) {
    }

    // View.OnClickListener
    @Override
    public void onClick(View view) {
        if (mStations.getSelectedItemPosition() == 0) {
            // 駅が選択されていない
            Toast.makeText(self, "駅が選択されていません", Toast.LENGTH_SHORT).show();
            return;
        }
        // 駅名取得
        HashMap<String, String> station = (HashMap<String, String>) mStations.getSelectedItem();
        String strStationName = station.get("name");
        Intent intent = new Intent();
        intent.putExtra("RESULT",strStationName);
        setResult(RESULT_OK,intent);
        finish();
    }

    // AdapterView.OnItemSelectedListener
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == 0) {
            // アイテムが選択されていない
            return;
        }
        // アイテムを取得してリクエストにセット
        HashMap<String, String> item = (HashMap<String, String>) adapterView.getAdapter().getItem(i);
        Bundle bundle = new Bundle();
        bundle.putString("name", item.get("name"));
        // 実行するメソッドを設定
        int loaderId = -1;
        switch (adapterView.getId()) {
            case R.id.spiArea:
                loaderId = ExpressLoader.PREFECTURES;
                break;
            case R.id.spiPrefectures:
                loaderId = ExpressLoader.LINES;
                break;
            case R.id.spiLines:
                loaderId = ExpressLoader.STATIONS;
                break;
        }
        getSupportLoaderManager().initLoader(loaderId, bundle, self);
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
