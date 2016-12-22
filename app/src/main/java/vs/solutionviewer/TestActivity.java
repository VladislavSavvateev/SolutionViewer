package vs.solutionviewer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TableLayout;

public class TestActivity extends AppCompatActivity {

    TableLayout table;

    @Override
    protected void onCreate(Bundle savedShit) {
        super.onCreate(savedShit);
        setContentView(R.layout.activity_test);
        table = (TableLayout) findViewById(R.id.testTable);
        table.setShrinkAllColumns(true);
    }

}
