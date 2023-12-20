package com.buaa.JavaMath;

import android.os.Bundle;

import io.github.kexanie.library.MathView;
/**
 *
 * 创建LateX并将矩阵内容显示
 *
 * @author YangChaoyu
 * @date 2023/12/17 17:40
**/
public class ShowTransActivity extends BaseActivity {
    StringBuilder trans_str = new StringBuilder("\\[\\begin{bmatrix}");
    /**
     *
     * 获取对应的LateX,并重置内容
     *
     * @author YangChaoyu
     * @date 2023/12/17 17:40
    **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_show);

        MathView result_trans = (MathView) findViewById(R.id.result_trans);
        create_trans_str();
        String result = trans_str.toString();
        result_trans.setText(result);
    }
    /**
     *
     * 将矩阵内容传化为符合LateX的格式要求
     *
     * @author YangChaoyu
     * @date 2023/12/17 17:43
    **/
    private void create_trans_str() {
        for (int i = 0; i < CalMatrixTrans.matrix2.length; i++) {
            for (int j = 0; j < CalMatrixTrans.matrix2[i].length; j++) {
                trans_str.append(CalMatrixTrans.matrix2[i][j]);
                if (j == CalMatrixTrans.matrix2[i].length - 1 && i !=CalMatrixTrans.matrix2.length - 1) {
                    trans_str.append("\\\\");
                } else if (j == CalMatrixTrans.matrix2[i].length - 1 && i == CalMatrixTrans.matrix2.length - 1) {
                    trans_str.append("\\end{bmatrix}\\]");
                } else {
                    trans_str.append("&");
                }
            }
        }
    }
}