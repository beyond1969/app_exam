package pe.beyond.hw2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText m_day;
    EditText m_place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void Onclick(View v){
        Intent it;
        switch(v.getId()){
            case R.id.btn_search_0:
                m_day = (EditText)findViewById(R.id.edit_day);
                m_place = (EditText)findViewById(R.id.edit_location_0);
                it = new Intent(getApplicationContext(),Location0.class);
                it.putExtra("day",m_day.getText().toString());
                it.putExtra("place",m_place.getText().toString());
                startActivity(it);
                break;
            case R.id.btn_search_1:
                m_place = (EditText)findViewById(R.id.edit_location_1);
                it = new Intent(getApplicationContext(), Location1.class);
                it.putExtra("place",m_place.getText().toString());
                startActivity(it);
                break;
            case R.id.btn_hw2:
                it = new Intent(getApplicationContext(), MyWebBrowser.class);
                startActivity(it);
                break;
        }
    }
}
