package com.buaa.JavaMath;

import android.os.Bundle;

import io.github.kexanie.library.MathView;
/**
 *
 * 创建LateX并将矩阵内容显示
 *
 * @author YangChaoyu
 * @date 2023/12/17 17:38
**/
public class ShowAddActivity extends BaseActivity {
    StringBuilder add_str = new StringBuilder("\\[\\begin{bmatrix}");
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
        setContentView(R.layout.activity_add_show);

        MathView result_add = (MathView) findViewById(R.id.result_add);
        create_add_str();
        String result = add_str.toString();
        result_add.setText(result);
    }
    /**
     *
     * 将矩阵内容传化为符合LateX的格式要求
     *
     * @author YangChaoyu
     * @date 2023/12/17 17:41
    **/
    private void create_add_str() {
        for (int i = 0; i < CalMatrixAdd.matrix1.length; i++) {
            for (int j = 0; j < CalMatrixAdd.matrix1[i].length; j++) {
                add_str.append(CalMatrixAdd.matrix1[i][j]);
                if (j == CalMatrixAdd.matrix1[i].length - 1 && i != CalMatrixAdd.matrix1.length - 1) {
                    add_str.append("\\\\");
                } else if (j == CalMatrixAdd.matrix1[i].length - 1 && i == CalMatrixAdd.matrix1.length - 1) {
                    add_str.append("\\end{bmatrix}\\]");
                } else {
                    add_str.append("&");
                }
            }
        }
    }
}