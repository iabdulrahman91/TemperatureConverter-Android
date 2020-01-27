package edu.depaul.csc472.temperatureconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // UI components
    private TextView history;
    private TextView resultField;
    private TextView fromLabel;
    private TextView toLabel;
    private EditText inputField;
    private Button fToCBtn;
    private Button cToFBtn;

    // Variable for calculation
    private Boolean fToC = true;


    // Activity Lifecycle Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        history = findViewById(R.id.history);

        resultField = findViewById(R.id.resultField);
        inputField = findViewById(R.id.inputField);
        history.setMovementMethod(new ScrollingMovementMethod()); // to make textView scrollable

        fromLabel = findViewById(R.id.fromLabel);
        toLabel = findViewById(R.id.toLabel);

        fToCBtn = findViewById(R.id.fToCBtn);
        cToFBtn = findViewById(R.id.cToFBtn);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString("HISTORY", history.getText().toString());
        outState.putString("RESULT", resultField.getText().toString());
        outState.putBoolean("fToC", this.fToC);

        // Call super last
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // Call super first
        super.onRestoreInstanceState(savedInstanceState);

        history.setText(savedInstanceState.getString("HISTORY"));
        resultField.setText(savedInstanceState.getString("RESULT"));
        this.fToC = savedInstanceState.getBoolean("fToC");
        (fToC ? this.fToCBtn : this.cToFBtn).callOnClick(); // this expression will return the right button and then call onClick to updated labels

    }


    public void fToC(View view) {
        this.fToC = true;
        fromLabel.setText("Fahrenheit Degree:");
        toLabel.setText("Celsius Degree:");
    }

    public void cToF(View view) {
        this.fToC = false;
        fromLabel.setText("Celsius Degree:");
        toLabel.setText("Fahrenheit Degree:");
    }

    public void convert(View view) {
        String input = inputField.getText().toString();

        if (input.trim().isEmpty()) {
            Toast.makeText(this, "empty input", Toast.LENGTH_SHORT).show();
            return;
        }
        Double x = Double.parseDouble(input);
        Double y = (fToC) ? ((x - 32) / 1.8) : ((x * 1.8) + 32);
        String res = String.format("%.1f", y);
        resultField.setText(res);

        String his = history.getText().toString();
        String newRes = (fToC) ? "F to C: " : "C to F: ";
        newRes += (x.toString() + " ==> " + res + "\n");
        history.setText(newRes + his);

    }

    public void clear(View view) {
        history.setText("");
    }
}
