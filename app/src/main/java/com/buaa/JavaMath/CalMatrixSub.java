package com.buaa.JavaMath;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.view.View;
import android.widget.Button;
/**
 *
 * 这个类用于接收来自MatrixSub类传递的矩阵行列数，然后生成对应大小的矩阵。
 * 在点击确定按钮后，读取用户输入，进行计算，并将结果传递给ShowSubActivity类。
 *
 * @author YangChaoyu
 * @date 2023/12/17 17:17
**/
public class CalMatrixSub extends BaseActivity {
    public static int[][] matrix1;
    private int[][] matrix2;
    private int rows, columns;
    /**
     *
     * 接受从MatrixSub类中传递来的参数，并创建对应大小的矩阵。
     * 获取两个按钮的引用，在用户点击时做出响应。
     *
     * @author YangChaoyu
     * @date 2023/12/17 17:22
    **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix_sub);

        // 获取对Button的引用
        Button Button_yes = (Button) findViewById(R.id.put_yes_sub);

        // 设置Button的点击事件
        Button_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 在按钮被点击时执行的操作
                CalSub();
                Intent intent = new Intent(CalMatrixSub.this, ShowSubActivity.class);
                startActivity(intent);
            }
        });

        // 获取对Button的引用
        Button Button_cancel = (Button) findViewById(R.id.put_cancel_sub);

        // 设置Button的点击事件
        Button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 在按钮被点击时执行的操作
                Intent intent = new Intent(CalMatrixSub.this, MatrixSub.class);
                startActivity(intent);
            }
        });

        // 获取传递过来的参数
        Intent intent = getIntent();
        rows = intent.getIntExtra("ROWS", 0);
        columns = intent.getIntExtra("COLUMNS", 0); // 0 是默认值
        matrix1 = new int[rows][columns];
        matrix2 = new int[rows][columns];
        createTable1();
        createTable2();
    }
    /**
     *
     * 从指定的TableLayout读取用户的输入
     *
     * @author YangChaoyu
     * @date 2023/12/17 17:25
    **/
    private int[][] readMatrixFromTable(TableLayout tableLayout, int[][] matrix) {
        for (int i = 0; i < rows; i++) {
            TableRow row = (TableRow) tableLayout.getChildAt(i);

            for (int j = 0; j < columns; j++) {
                // 获取EditText中的文本
                EditText editText = (EditText) row.getChildAt(j);
                String text = editText.getText().toString();

                try {
                    // 将文本转换为整数并存放到矩阵中
                    int value = Integer.parseInt(text);
                    matrix[i][j] = value;
                } catch (NumberFormatException e) {
                    // 处理转换失败的情况，例如用户输入的不是数字
                    e.printStackTrace();
                    // 在这里你可以添加适当的错误处理或者提醒用户输入有效的数字
                }
            }
        }
        return matrix;
    }
    /**
     *
     * 创建对应的矩阵
     *
     * @author YangChaoyu
     * @date 2023/12/17 17:27
    **/
    private void createTable1() {
        TableLayout tableLayout1 = (TableLayout) findViewById(R.id.tableLayout1_add);
        tableLayout1.removeAllViews();  // 清空表格

        // 创建一个布局参数，将行居中显示
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );
        rowParams.gravity = Gravity.CENTER;

        for (int i = 0; i < rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(rowParams); // 设置行的布局参数

            for (int j = 0; j < columns; j++) {
                EditText editText = new EditText(this);

                //数字键盘
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);

                //设置初始宽度
                editText.setWidth(6);

                //文本变化监听器，用于随用户的输入长度改变而改变
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        //根据输入内容调节宽度
                        int minwidth=6;
                        int newwidth=Math.max(minwidth,(int)editText.getPaint().measureText(editable.toString()+3));
                        editText.setWidth(newwidth);
                    }
                });
                row.addView(editText);
            }
            tableLayout1.addView(row);
        }
    }
    /**
     *
     * 创建对应的矩阵
     *
     * @author YangChaoyu
     * @date 2023/12/17 17:27
    **/
    private void createTable2() {
        TableLayout tableLayout2 = (TableLayout) findViewById(R.id.tableLayout2_mul);
        tableLayout2.removeAllViews();  // 清空表格

        // 创建一个布局参数，将行居中显示
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );
        rowParams.gravity = Gravity.CENTER;

        for (int i = 0; i < rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(rowParams); // 设置行的布局参数

            for (int j = 0; j < columns; j++) {
                EditText editText = new EditText(this);

                //数字键盘
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);

                //设置初始宽度
                editText.setWidth(6);

                //文本变化监听器，用于随用户的输入长度改变而改变
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        //根据输入内容调节宽度
                        int minwidth=6;
                        int newwidth=Math.max(minwidth,(int)editText.getPaint().measureText(editable.toString()+3));
                        editText.setWidth(newwidth);
                    }
                });
                row.addView(editText);
            }
            tableLayout2.addView(row);
        }
    }
    /**
     *
     * 将两个矩阵相减
     *
     * @author YangChaoyu
     * @date 2023/12/17 17:29
    **/
    private void CalSub() {
        //注意要在用户输入之后读取
        TableLayout tableLayout1 = (TableLayout) findViewById(R.id.tableLayout1_add);
        TableLayout tableLayout2 = (TableLayout) findViewById(R.id.tableLayout2_mul);
        matrix1 = readMatrixFromTable(tableLayout1, matrix1);
        matrix2 = readMatrixFromTable(tableLayout2, matrix2);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix1[i][j] = matrix1[i][j] - matrix2[i][j];
            }
        }
    }
}