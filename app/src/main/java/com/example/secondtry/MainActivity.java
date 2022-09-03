package com.example.secondtry;

import androidx.appcompat.app.AppCompatActivity;
import com.fathzer.soft.javaluator.*;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.*;
import java.util.Locale;
import java.util.Objects;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

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

             if (Math.abs(rounded) > 10e9) {
                 return "Infinity or undefined";
             }
             else if (Math.abs(rounded) < 10e-6){
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

             if (Math.abs(lim) > 10e9) {
                 return "Infinity or undefined";
             }
             else if (Math.abs(lim) < 10e-6){
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

             if (Math.abs(lim2) > 10e9){
                 return "Infinity or undefined";
             }
             else if (Math.abs(lim2) < 10e-6){
                 return "0";
             }
             String roundlim2 = String.valueOf(lim2);
             return roundlim2;
         }
         return "FUCK";

     }


    public void handiest(View v) {
        TextView tv1 = findViewById(R.id.f_val);
        TextView tv2 = findViewById(R.id.der1);
        TextView tv3 = findViewById(R.id.der2);
        tv1.setText(function(0, "regular"));
        tv2.setText(function(0.00001, "dev1"));
        tv3.setText(function(0.00001, "dev2"));
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
        TextView tv = (TextView) v;
        if (firstTextsel) {
            TextView edit1 = findViewById(R.id.function);
            edit1.append("x");
        }
        else if (secTextsel) {
            TextView edit1 = findViewById(R.id.xvar);
            edit1.append("x");
        }

    }

}