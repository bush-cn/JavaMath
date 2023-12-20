package com.buaa.JavaMath;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
/**
 *
 * 作为初始界面选择四种运算
 *
 * @author YangChaoyu
 * @date 2023/12/17 17:30
**/
public class MatrixActivityGeneral extends BaseActivity {
    /**
     *
     * 判断用户点击的按钮，并跳转到对应界面
     *
     * @author YangChaoyu
     * @date 2023/12/17 17:31
    **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrixgeneral);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*矩阵加法*/
        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatrixActivityGeneral.this, MatrixAdd.class);
                startActivity(intent);
            }
        });

        /*矩阵减法*/
        Button sub = (Button) findViewById(R.id.sub);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatrixActivityGeneral.this, MatrixSub.class);
                startActivity(intent);
            }
        });

        /*矩阵乘法*/
        Button mul = (Button) findViewById(R.id.mul);
        mul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatrixActivityGeneral.this, MatrixMul.class);
                startActivity(intent);
            }
        });

        /*矩阵转置*/
        Button trans = (Button) findViewById(R.id.trans);
        trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatrixActivityGeneral.this, MatrixTrans.class);
                startActivity(intent);
            }
        });
    }
    /**
     *
     * 将跳转封装到一个方法
     *
     * @author YangChaoyu
     * @date 2023/12/17 17:33
    **/
    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, MatrixActivityGeneral.class));
    }
}