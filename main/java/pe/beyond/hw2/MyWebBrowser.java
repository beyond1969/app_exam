package pe.beyond.hw2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

public class MyWebBrowser extends AppCompatActivity {
    WebView wb;
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hw2);
        et = (EditText)findViewById(R.id.edit_address);
        wb = (WebView)findViewById(R.id.web_view1);
        wb.getSettings().setJavaScriptEnabled(true);

    }

    public class WebViewClientClass extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            view.loadUrl(url);
            return true;
        }
    }
    public void onclick(View v){
        switch(v.getId()){
            case R.id.btn_web:
                String url = et.getText().toString();
                wb.loadUrl(url);
                wb.setWebViewClient(new WebViewClientClass());
                break;
        }
    }
}
