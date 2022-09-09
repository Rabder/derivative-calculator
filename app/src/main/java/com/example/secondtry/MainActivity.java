package com.example.secondtry;

import androidx.appcompat.app.AppCompatActivity;
import com.fathzer.soft.javaluator.*;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.*;
import java.util.Locale;
import java.util.Objects;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    public enum WindowSizeClass { COMPACT, MEDIUM, EXPANDED }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    private View.OnClickListener btnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub

        }
    };



    public class BaseActivity extends Activity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
                    Log.e("Error"+Thread.currentThread().getStackTrace()[2],paramThrowable.getLocalizedMessage());
                }
            });
        }
    }




     public String function(double h, String method) {
         TextView func = findViewById(R.id.function);
         android.widget.TextView xvar = findViewById(R.id.xvar);
         final DoubleEvaluator eval = new DoubleEvaluator();
         String user_func = func.getText().toString();
         String x_str = xvar.getText().toString();
         DoubleEvaluator evaluator = new DoubleEvaluator();
         double x_eval = evaluator.evaluate(x_str);


         if (h == 0 && Objects.equals(method, "regular")) {

             final StaticVariableSet<Double> variables = new StaticVariableSet<Double>();
             variables.set("x", x_eval);

             Double result = eval.evaluate(user_func, variables);
             double rounded = Math.round(result * 1000.0) / 1000.0;

             if (Math.abs(rounded) > 10e50) {
                 return "Infinity or undefined";
             }
             else if (Math.abs(rounded) < 10e-50){
                 return "0";
             }
             String rounded1 = String.valueOf(rounded);
             return rounded1;
         }
         else if (h == 0.00001 && Objects.equals(method, "dev1")) {
             final StaticVariableSet<Double> variables = new StaticVariableSet<Double>();
             variables.set("x", x_eval);
             Double fx = eval.evaluate(user_func, variables);
             variables.set("x", x_eval + h);
             Double fxh = eval.evaluate(user_func, variables);
             double lim = Math.round(((fxh - fx) / h) * 1000.0) / 1000.0;

             if (Math.abs(lim) > 10e100) {
                 return "Infinity or undefined";
             }
             else if (Math.abs(lim) < 10e-100){
                 return "0";
             }
             String roundlim = String.valueOf(lim);
             return roundlim;
         }
         else if (h == 0.00001 && Objects.equals(method, "dev2")){
             final StaticVariableSet<Double> variables = new StaticVariableSet<Double>();

             variables.set("x", x_eval);
             Double fx = eval.evaluate(user_func, variables);
             variables.set("x", x_eval+h);
             Double fxh = eval.evaluate(user_func, variables);
             double der1 = (fxh-fx)/h;

             variables.set("x", x_eval+h);
             Double fx2 = eval.evaluate(user_func, variables);
             variables.set("x", x_eval+h+h);
             Double fxh2 = eval.evaluate(user_func, variables);
             double der2 = (fxh2-fx2)/h;

             double lim2 = Math.round(((der2-der1)/h)*10000.0)/10000.0;

             if (Math.abs(lim2) > 10e100){
                 return "Infinity or undefined";
             }
             else if (Math.abs(lim2) < 10e-100){
                 return "0";
             }
             String roundlim2 = String.valueOf(lim2);
             return roundlim2;
         }
         return "FUCK";

     }

     public void exceptionManager(String test) {
        final DoubleEvaluator exception = new DoubleEvaluator();

        try{
            exception.evaluate(test);
        }
        catch (IllegalArgumentException a){

        }
     }


    public void handiest(View view) {
        Boolean check = false;
        TextView tv1 = findViewById(R.id.f_val);
        TextView tv2 = findViewById(R.id.der1);
        TextView tv3 = findViewById(R.id.der2);
        TextView inv_1 = findViewById(R.id.inv_1);

        TextView func = findViewById(R.id.function);
        android.widget.TextView xvar = findViewById(R.id.xvar);
        DoubleEvaluator exception = new DoubleEvaluator();

        if ((func.getText().toString().length() < 1) || (xvar.getText().toString().length() < 1)){
            inv_1.setText("Invalid input");
            check = true;
        }
        try{
            final StaticVariableSet<Double> variable = new StaticVariableSet<Double>();
            variable.set("x", 3.00);
            exception.evaluate(func.getText().toString(), variable);
        } catch (Exception a) {
            inv_1.setText("Invalid input");
            check = true;
        }

        try{
            exception.evaluate(xvar.getText().toString());
        } catch (Exception b) {
            check = true;
        }

        finally {
            if (!check) {
                tv1.setText(function(0, "regular"));
                tv2.setText(function(0.00001, "dev1"));
                tv3.setText(function(0.00001, "dev2"));
                inv_1.setText("");
            }
        }
    }

    Boolean firstTextsel = false;
    Boolean secTextsel = false;

    public void textId1(View v){
         firstTextsel = true;
         secTextsel = false;
    }

    public void textId2(View v){
        firstTextsel = false;
        secTextsel = true;
    }


    public void xbtn(View v) {
        Button b = (Button)v;
        String buttonText = b.getText().toString();
        CharSequence btntxt = buttonText;
        if (firstTextsel) {
            TextView edit1 = findViewById(R.id.function);

            edit1.append(btntxt);
        }
        else if (secTextsel && !Objects.equals(btntxt.toString(), "x")) {
            TextView edit1 = findViewById(R.id.xvar);
            edit1.append(btntxt);
        }
    }

    public void del(View v) {
        if (firstTextsel) {
            TextView edit1 = findViewById(R.id.function);
            String txtstr = edit1.getText().toString();
            if (txtstr.length() > 0){
                edit1.setText(txtstr.substring(0, txtstr.length() - 1));
            }
        }
        else if (secTextsel) {
            TextView edit1 = findViewById(R.id.xvar);
            String txtstr = edit1.getText().toString();
            if (txtstr.length() > 0){
                edit1.setText(txtstr.substring(0, txtstr.length() - 1));
            }
        }
    }

    public void div(View v) {
        if (firstTextsel) {
            TextView edit1 = findViewById(R.id.function);
            edit1.append("1/");
        }
        else if (secTextsel) {
            TextView edit1 = findViewById(R.id.xvar);
            edit1.append("1/");
        }
    }

    public void pi(View v) {
        if (firstTextsel) {
            TextView edit1 = findViewById(R.id.function);
            edit1.append("pi");
        }
        else if (secTextsel) {
            TextView edit1 = findViewById(R.id.xvar);
            edit1.append("pi");
        }
    }

}