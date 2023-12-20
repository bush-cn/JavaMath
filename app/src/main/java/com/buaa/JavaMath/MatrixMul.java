package com.buaa.JavaMath;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
/**
 *
 * 选择相加的两个矩阵的大小，并传递给CalMatrixMul
 *
 * @author YangChaoyu
 * @date 2023/12/17 17:35
**/
public class MatrixMul extends BaseActivity {
    /**
     *
     * 初始化界面
     *
     * @author YangChaoyu
     * @date 2023/12/17 17:36
    **/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//下面的返回，home键等
        showMatrixSizeDialog();
    }
    /**
     *
     * 创建对话框，用于用户选择矩阵大小
     *
     * @author YangChaoyu
     * @date 2023/12/17 17:37
    **/
    private void showMatrixSizeDialog() {
        // 使用AlertDialog构建一个简单的对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择矩阵大小");

        // 设置对话框的自定义布局
        View view = getLayoutInflater().inflate(R.layout.activity_select_mulmatrixsize, null);
        builder.setView(view);

        // 获取对话框中的EditText
        final EditText editTextRows1 = (EditText) view.findViewById(R.id.mul_editTextRows1);
        final EditText editTextColumns1 = (EditText) view.findViewById(R.id.mul_editTextColumns1);
        final TextView editTextRows2 = (TextView) view.findViewById(R.id.mul_TextRows2);
        final EditText editTextColumns2 = (EditText) view.findViewById(R.id.mul_editTextColumns2);

        //实时改变第二个矩阵的行数
        editTextColumns1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // 获取editTextColumns1每次改变后的文本内容
                String result = editable.toString();

                // 将结果设置到editTextRows2中
                editTextRows2.setText(result);
            }
        });

        // 设置对话框的确认按钮
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 获取用户输入的矩阵大小
                int rows1 = Integer.parseInt(editTextRows1.getText().toString());
                int columns1 = Integer.parseInt(editTextColumns1.getText().toString());
                int rows2 = columns1;
                int columns2 = Integer.parseInt(editTextColumns2.getText().toString());
                //传递矩阵大小
                Intent intent = new Intent(MatrixMul.this, CalMatrixMul.class);
                intent.putExtra("ROWS1", rows1);
                intent.putExtra("COLUMNS1", columns1);
                intent.putExtra("ROWS2", rows2);
                intent.putExtra("COLUMNS2", columns2);
                startActivity(intent);
            }
        });

        // 设置对话框的取消按钮
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MatrixMul.this, MatrixActivityGeneral.class);
                startActivity(intent);
                dialog.dismiss();
                //取消之后退回原来界面
            }
        });

        // 显示对话框
        builder.show();
    }
}