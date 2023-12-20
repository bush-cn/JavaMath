package com.buaa.JavaMath;

import android.os.Bundle;

import io.github.kexanie.library.MathView;
/**
 *
 * 创建LateX并将矩阵内容显示
 *
 * @author YangChaoyu
 * @date 2023/12/17 17:39
**/
public class ShowMulActivity extends BaseActivity {
    StringBuilder mul_str = new StringBuilder("\\[\\begin{bmatrix}");
    /**
     *
     * 获取对应的LateX,并重置内容
     *
     * @author YangChaoyu
     * @date 2023/12/17 17:41
    **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mul_show);

        MathView result_mul = (MathView) findViewById(R.id.result_mul);
        create_mul_str();
        String result = mul_str.toString();
        result_mul.setText(result);
    }
    /**
     *
     * 将矩阵内容传化为符合LateX的格式要求
     *
     * @author YangChaoyu
     * @date 2023/12/17 17:42
    **/
    private void create_mul_str() {
        for (int i = 0; i < CalMatrixMul.matrix.length; i++) {
            for (int j = 0; j < CalMatrixMul.matrix[i].length; j++) {
                mul_str.append(CalMatrixMul.matrix[i][j]);
                if (j == CalMatrixMul.matrix[i].length - 1 && i != CalMatrixMul.matrix.length - 1) {
                    mul_str.append("\\\\");
                } else if (j == CalMatrixMul.matrix[i].length - 1 && i == CalMatrixMul.matrix.length - 1) {
                    mul_str.append("\\end{bmatrix}\\]");
                } else {
                    mul_str.append("&");
                }
            }
        }
    }
}