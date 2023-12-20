package com.buaa.JavaMath;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
/**
 *
 * 选择相加的两个矩阵的大小，并传递给CalMatrixTrans
 *
 * @author YangChaoyu
 * @date 2023/12/17 17:35
**/
public class MatrixTrans extends BaseActivity {
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
        View view = getLayoutInflater().inflate(R.layout.activity_select_addmatrixsize, null);
        //这里可以共用一个
        builder.setView(view);

        // 获取对话框中的EditText
        final EditText editTextRows = (EditText) view.findViewById(R.id.mul_editTextRows1);
        final EditText editTextColumns = (EditText) view.findViewById(R.id.mul_editTextColumns1);

        // 设置对话框的确认按钮
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 获取用户输入的矩阵大小
                int rows = Integer.parseInt(editTextRows.getText().toString());
                int columns = Integer.parseInt(editTextColumns.getText().toString());
                //传递矩阵大小
                Intent intent = new Intent(MatrixTrans.this, CalMatrixTrans.class);
                intent.putExtra("ROWS", rows);
                intent.putExtra("COLUMNS", columns);
                startActivity(intent);
            }
        });

        // 设置对话框的取消按钮
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MatrixTrans.this, MatrixActivityGeneral.class);
                startActivity(intent);
                dialog.dismiss();
                //取消之后退回原来界面
            }
        });

        // 显示对话框
        builder.show();
    }
}