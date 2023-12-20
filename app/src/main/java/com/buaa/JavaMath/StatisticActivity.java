package com.buaa.JavaMath;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import io.github.kexanie.library.MathView;

/**
 *   统计实验数据并做一元线性回归分析
 * @author Bush
 * @date 2023/12/10 15:36
**/

public class StatisticActivity extends BaseActivity{
    private TextView title;
    private EditText textInX;
    private EditText textInY;
    private ArrayList<Double> dataX = new ArrayList<>();
    private ArrayList<Double> dataY = new ArrayList<>();

    private TableLayout tableLayout;
    private TextView resultView;
    private TextView regressionFactorView;
    private TextView correlationCoefficientView;
    private TextView uncertaintyView;

    /**
     *  判断字符串是否能表示一个浮点数
     * @param s 给定字符串
     * @return boolean   布尔值，是否为一个浮点数
     * @author Bush
     * @date 2023/12/10 15:39
    **/
    public static boolean isDouble(String s){
        try{
            Double.parseDouble(s);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }


    /**
     *  活动创建入口
     * @param savedInstanceState
     * @author Bush
     * @date 2023/12/10 15:41
    **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initTextIn();
        tableLayout = (TableLayout)findViewById(R.id.statistic_table);
        tableLayout.setVisibility(View.GONE);
        resultView = (TextView) findViewById(R.id.result);
    }

    /**
     * 初始化输入框
     * @author Bush
     * @date 2023/12/10 15:41
    **/

    private void initTextIn(){
        textInX = (EditText) findViewById(R.id.statistic_input_x);
        textInY = (EditText) findViewById(R.id.statistic_input_y);

        textInX.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String[] inputs = {};
                if (charSequence.length() > 0){
                    inputs = charSequence.toString().split(" ");
                }
                dataX.clear();
                for(String data: inputs){
                    if(data.length() > 0){
                        if(isDouble(data)){
                            dataX.add(Double.parseDouble(data));
                        }
                        else{
                            resultView.setText("请输入正确形式的数据");
                            return;
                        }
                    }
                }
                calculate();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        textInY.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String[] inputs = {};
                if (charSequence.length() > 0){
                    inputs = charSequence.toString().split(" ");
                }
                dataY.clear();
                for(String data: inputs){
                    if(isDouble(data)){
                        dataY.add(Double.parseDouble(data));
                    }
                    else{
                        resultView.setText("请输入正确形式的数据");
                        return;
                    }
                }
                calculate();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * 主要的计算函数
     * @author Bush
     * @date 2023/12/10 15:42
    **/

    private void calculate(){
        /**
         * delete statistic **and reserve the MathView in the first column**
         */
        tableLayout = ((TableLayout)findViewById(R.id.statistic_table));

        TableRow rowTitle = (TableRow)findViewById(R.id.table_title);
        TableRow rowX = (TableRow) findViewById(R.id.x);
        TableRow rowY = (TableRow) findViewById(R.id.y);
        TableRow rowXy = (TableRow) findViewById(R.id.xy);
        TableRow rowXSquare = (TableRow) findViewById(R.id.x_square);
        TableRow rowYSquare = (TableRow) findViewById(R.id.y_square);

        int childCount = rowTitle.getChildCount();
        rowTitle.removeViewsInLayout(1, childCount - 1);
        rowX.removeViewsInLayout(1, childCount - 1);
        rowY.removeViewsInLayout(1, childCount - 1);
        rowXy.removeViewsInLayout(1, childCount - 1);
        rowXSquare.removeViewsInLayout(1, childCount - 1);
        rowYSquare.removeViewsInLayout(1, childCount - 1);

        /**
         * inspect that the num of statistic if is equal
         */
        if(dataX.size() != dataY.size()){
            resultView = (TextView) findViewById(R.id.result);
            resultView.setText("自变量和因变量的数据个数应相同！");
            tableLayout.setVisibility(View.GONE);
            return;
        }

        int num = dataX.size();

        if (num == 0) {
            tableLayout.setVisibility(View.GONE);
            return;
        }

        /**
         * equal and start to calculate
         */

        resultView.setText("计算完成！结果如下");

        Double xIn,
                yIn,
                xy,
                xSquare,
                ySquare,
                xSum = 0.0,
                ySum = 0.0,
                xySum = 0.0,
                xSquareSum = 0.0,
                ySquareSum = 0.0;
        for(int i = 0; i < num; i++){
            xIn = dataX.get(i);
            yIn = dataY.get(i);

            xSum += xIn;
            ySum += yIn;
            xy = xIn * yIn;
            xySum += xy;
            xSquare = Math.pow(xIn, 2);
            xSquareSum += xSquare;
            ySquare = Math.pow(yIn, 2);
            ySquareSum += ySquare;

            TextView serialNumView = new TextView(this);
            serialNumView.setGravity(Gravity.CENTER);
            serialNumView.setPadding(8,0,8,0);
            serialNumView.setText(String.valueOf(i + 1));
            rowTitle.addView(serialNumView);

            TextView xView = new TextView(this);
            xView.setGravity(Gravity.CENTER);
            xView.setPadding(8,0,8,0);
            xView.setText(xIn.toString());
            rowX.addView(xView);

            TextView yView = new TextView(this);
            yView.setGravity(Gravity.CENTER);
            yView.setPadding(8,0,8,0);
            yView.setText(yIn.toString());
            rowY.addView(yView);

            TextView xyView = new TextView(this);
            xyView.setGravity(Gravity.CENTER);
            xyView.setPadding(8,0,8,0);
            xyView.setText(xy.toString());
            rowXy.addView(xyView);

            TextView xSquareView = new TextView(this);
            xSquareView.setGravity(Gravity.CENTER);
            xSquareView.setPadding(8,0,8,0);
            xSquareView.setText(xSquare.toString());
            rowXSquare.addView(xSquareView);

            TextView ySquareView = new TextView(this);
            ySquareView.setGravity(Gravity.CENTER);
            ySquareView.setPadding(8,0,8,0);
            ySquareView.setText(ySquare.toString());
            rowYSquare.addView(ySquareView);
        }

        /**
         * 表格布局设置为课件，以展示数据x，y，xy，x^2，y^2
         */
        tableLayout.setVisibility(View.VISIBLE);

        /**
         * 各项求和LaTeX
         */
        MathView sumView = (MathView)findViewById(R.id.sum_latex);
        sumView.setText("\\(\\sum_{i=0}^{n} x= " + xSum +"," +
                "\\sum_{i=0}^{n} y=" + ySum + ",\\\\" +
            "\\sum_{i=0}^{n} xy= " + xySum + ",\\\\" +
            "\\sum_{i=0}^{n} x^2=" + xSquareSum + "," +
            "\\sum_{i=0}^{n} y^2=" + ySquareSum + ".\\)");

        /**
         * 平均数计算
         */
        MathView averageView = (MathView) findViewById(R.id.average_latex);
        Double averageOfX = xSum / num,
                averageOfY = ySum / num,
                squareOfAverageX = Math.pow(averageOfX, 2),
                squareOfAverageY = Math.pow(averageOfY, 2),
                averageOfXSquare = xSquareSum / num,
                averageOfYSquare = ySquareSum / num;

        averageView.setText("\\( \\bar x= " + averageOfX + "," +
                "\\bar y = " + averageOfX + ",\\\\ " +
                "{\\bar x}^2= " + squareOfAverageX + ", " +
                "{\\bar y}^2= " + squareOfAverageY + ",\\\\" +
                "\\bar {x^2}= " + averageOfXSquare + "," +
                "\\bar{y^2}= " + averageOfYSquare + " \\)");

        /**
         * 回归系数
         */
        MathView regressionView = (MathView) findViewById(R.id.regression_latex);

        Double averageOfXY = xySum / num;

        Double regressionOfB = (averageOfX * averageOfY - averageOfXY)
                / (squareOfAverageX - averageOfXSquare);
        Double regressionOfA = averageOfY - regressionOfB * averageOfX;

        regressionView.setText("\\(  b = \\cfrac{\\bar x\\bar y - \\bar{xy}}" +
        "{{\\bar x}^2 - \\bar {x^2}} = " + regressionOfB + ", \\\\" +
        "a = \\bar y - b \\bar x = " + regressionOfA +".\\)");

        regressionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cmb = (ClipboardManager) v.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(regressionView.getText());
                Snackbar.make(v, "已复制转换结果", Snackbar.LENGTH_SHORT).show();
            }
        });

        /**
         * 相关系数
         */
        MathView correlationView = (MathView) findViewById(R.id.correlation_latex);

        Double correlationFactorOfR = (averageOfXY - averageOfX * averageOfY)
                / Math.sqrt((averageOfXSquare - squareOfAverageX) * (averageOfYSquare - squareOfAverageY));

        correlationView.setText("\\(  r = \\cfrac{\\bar {xy} - \\bar x \\bar y}"+
        "{\\sqrt{(\\bar{x^2} - {\\bar x}^2) (\\bar{y^2} - {\\bar y}^2)}} = " +
        correlationFactorOfR + ".        \\)");

        correlationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cmb = (ClipboardManager) v.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(correlationView.getText());
                Snackbar.make(v, "已复制计算结果", Snackbar.LENGTH_SHORT).show();
            }
        });

        /**
         * 不确定度
         */
        MathView uncertaintyView = (MathView) findViewById(R.id.uncertainty_latex);

        /**
         * 当数据个数num <= 2时，不输出不确定度
         */
        if(num > 2){
            Double uncertaintyOfB =
                    (regressionOfB * Math.sqrt((1.0 / (num - 2)) * (Math.pow(correlationFactorOfR, -2) - 1)));
            Double uncertaintyOfA = Math.sqrt(averageOfXSquare) * uncertaintyOfB;

            uncertaintyView.setText("\\(  u(b)= b\\sqrt{\\cfrac{1}{k-2} (\\cfrac{1}{r^2} - 1) } = "
                    + uncertaintyOfB + ",\\\\" +
                    "u(a) = \\sqrt{\\bar{x^2}} \\cdot u(b) = " + uncertaintyOfA + ". \\\\" +
                    "\\)");
        }

        uncertaintyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cmb = (ClipboardManager) v.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(uncertaintyView.getText());
                Snackbar.make(v, "已复制计算结果", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    /**
     *  活动启动
     * @param context
     * @author Bush
     * @date 2023/12/10 15:43
    **/

    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, StatisticActivity.class));
    }
}
