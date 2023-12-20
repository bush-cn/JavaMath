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
public class ShowSubActivity extends BaseActivity {
    StringBuilder sub_str = new StringBuilder("\\[\\begin{bmatrix}");
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
        setContentView(R.layout.activity_sub_show);

        MathView result_sub = (MathView) findViewById(R.id.result_sub);
        create_sub_str();
        String result = sub_str.toString();
        result_sub.setText(result);
    }
    /**
     *
     * 将矩阵内容传化为符合LateX的格式要求
     *
     * @author YangChaoyu
     * @date 2023/12/17 17:43
    **/
    private void create_sub_str() {
        for (int i = 0; i < CalMatrixSub.matrix1.length; i++) {
            for (int j = 0; j < CalMatrixSub.matrix1[i].length; j++) {
                sub_str.append(CalMatrixSub.matrix1[i][j]);
                if (j == CalMatrixSub.matrix1[i].length - 1 && i != CalMatrixSub.matrix1.length - 1) {
                    sub_str.append("\\\\");
                } else if (j == CalMatrixSub.matrix1[i].length - 1 && i == CalMatrixSub.matrix1.length - 1) {
                    sub_str.append("\\end{bmatrix}\\]");
                } else {
                    sub_str.append("&");
                }
            }
        }
    }
}

