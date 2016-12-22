package vs.solutionviewer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {

    ArrayList<Row> rowList;
    TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rowList = new ArrayList<>();
        try {
            FileInputStream file = new FileInputStream(Environment.getExternalStorageDirectory()+"/"+"questions.xls");
            HSSFWorkbook book = new HSSFWorkbook(file);
            Sheet sheet = book.getSheetAt(0);
            Iterator<Row> it = sheet.iterator();
            while(it.hasNext()){
                Row r = it.next();
                if (r.getCell(0).toString() == "") {
                    Row r1 = rowList.get(rowList.size() - 1);
                    r1.getCell(1).setCellValue(r1.getCell(1).toString() + r.getCell(1));
                }
                else rowList.add(r);
            }
            file.close();
            Toast.makeText(this,"Вопросы загружены! Всего их " + rowList.size(),Toast.LENGTH_LONG).show();
            table = (TableLayout) findViewById(R.id.table);
            table.setShrinkAllColumns(true);
            ((EditText) findViewById(R.id.editText)).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    table.removeAllViews();
                    if (charSequence.length() < 5) return;
                    for (Row r: rowList) {
                        if (r.getCell(0).toString().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                            TableRow tr = new TableRow(getApplicationContext());
                            TextView tv = new TextView(getApplicationContext());
                            tv.setText(r.getCell(0).toString());
                            tv.setTextColor(Color.BLACK);
                            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                    TableRow.LayoutParams.WRAP_CONTENT, 1f));
                            TextView tv2 = new TextView(getApplicationContext());
                            tv2.setText(r.getCell(1).toString());
                            tv2.setTextColor(Color.BLACK);
                            tv2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                    TableRow.LayoutParams.WRAP_CONTENT, 1f));
                            tr.addView(tv);
                            tr.addView(tv2);
                            table.addView(tr);
                            TextView v = new TextView(getApplicationContext());
                            v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
                            v.setBackgroundColor(Color.GRAY);
                            table.addView(v);
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {}
            });
        }
        catch (Exception e) {
            Toast.makeText(this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            Log.d("DEBUG",e.getMessage());
        }
    }

}
