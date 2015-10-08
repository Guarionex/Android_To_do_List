package com.paad.todolist;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ToDoListActivity extends Activity {
  
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  
    // Inflate your View
    setContentView(R.layout.main);
  
    // Get references to UI widgets
    ListView myListView = (ListView)findViewById(R.id.myListView);
    final EditText myEditText = (EditText)findViewById(R.id.myEditText);
    final EditText dateEditText = (EditText)findViewById(R.id.myEditTexDate);
    
    // Create the Array List of to do items
    final ArrayList<String> todoItems = new ArrayList<String>();
   
    // Create the Array Adapter to bind the array to the List View
    final ArrayAdapter<String> aa;
   
    aa = new ArrayAdapter<String>(this,
                                  android.R.layout.simple_list_item_1,
                                  todoItems);
   
    // Bind the Array Adapter to the List View
    myListView.setAdapter(aa);

    myEditText.setOnKeyListener(new View.OnKeyListener() {
      public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN)
          if ((keyCode == KeyEvent.KEYCODE_DPAD_CENTER) ||
                  (keyCode == KeyEvent.KEYCODE_ENTER)) {
            if(dateEditText.getText().toString().equals("")) {
              todoItems.add(0, myEditText.getText().toString());
              aa.notifyDataSetChanged();
              myEditText.setText("");
            }
            else
            {
              todoItems.add(0, myEditText.getText().toString() + " @ " + dateEditText.getText());
              aa.notifyDataSetChanged();
              myEditText.setText("");
              dateEditText.setText("");
            }
            return true;
          }
        return false;
      }
    });

    final Calendar myCalendar = Calendar.getInstance();

    final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

      @Override
      public void onDateSet(DatePicker view, int year, int monthOfYear,
                            int dayOfMonth) {
        // TODO Auto-generated method stub
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateLabel();
      }

      private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        if(!myEditText.getText().toString().equals("")) {
          todoItems.add(0, myEditText.getText().toString() + " @ " + sdf.format(myCalendar.getTime()));
          aa.notifyDataSetChanged();
          myEditText.setText("");
          dateEditText.setText("");
        } else {
          dateEditText.setText(sdf.format(myCalendar.getTime()));
        }
      }

    };

    dateEditText.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        // TODO Auto-generated method stub
        new DatePickerDialog(ToDoListActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();


      }
    });

    



  }

  

}