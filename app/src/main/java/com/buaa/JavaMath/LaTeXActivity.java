package com.buaa.JavaMath;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Build;
import android.view.View;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.util.Log;


import java.io.File;
import java.io.FileOutputStream;


import io.github.kexanie.library.MathView;

/**
 *
 *  输入LaTeX表达式并渲染成图片保存到系统相册里
 *
 * @author Bush
 * @date 2023/12/15 15:17
**/
public class LaTeXActivity extends BaseActivity{
    private Context mContext;
    private EditText textIn;
    private String rawExpression = "";
    private String escapedExpression;
    private MathView resultView;

    private Bitmap resultImage;
    private String formattedDateTime;
    private Button confirmButton;
    private Button saveButton;
    private static final int REQUEST_CODE_WRITE_STORAGE_PERMISSION = 1;


    /**
     *  活动创建入口
     * @param savedInstanceState
     * @author Bush
     * @date 2023/12/15 15:28
    **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latex);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mContext = this;
        resultView = (MathView)findViewById(R.id.result_latex);
        saveButton = (Button)findViewById(R.id.button_save);
        saveButton.setVisibility(View.GONE);
        initConfirmButton();
        initSaveButton();
        initTextIn();
    }

    /**
     *    获取图片保存的路径
     * @return java.lang.String
     * @author Bush
     * @date 2023/12/15 15:29
    **/
    private String getSavePath() {
        String path;
        if (Build.VERSION.SDK_INT > 29) {
            path = mContext.getExternalFilesDir(null).getAbsolutePath() + "/app/audio/";
        } else {
            path = Environment.getExternalStorageDirectory().getPath() + "/app/audio/";
        }
        return path;
    }


    /**
     *  检查是否授予权限
     * @param requestCode
     * @param permissions
     * @param grantResults
     * @author Bush
     * @date 2023/12/15 15:29
    **/
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE_WRITE_STORAGE_PERMISSION) {
            // 检查用户是否授予了写入存储的权限
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 用户授予了权限，执行文件操作
            } else {
                // 用户拒绝了权限请求，可以提供适当的提示或处理
            }
        }
    }

    /**
     *   将 View 渲染为 Bitmap
     * @param view 要渲染的 View
     * @return android.graphics.Bitmap
     * @author Bush
     * @date 2023/12/15 15:32
    **/
    public static Bitmap createBitmapFromView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    /**
     *  保存 Bitmap 到相册
     * @param context 上下文
     * @param bitmap 要保存的图像
     * @author Bush
     * @date 2023/12/15 15:33
     */
    public static void saveImageToGallery(Context context, Bitmap bitmap) {
        ContentResolver resolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "JavaMathFormula" + System.currentTimeMillis() + ".png");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/JavaMath");

        Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        try {
            OutputStream outputStream = resolver.openOutputStream(imageUri);
            // 在这里将图像数据写入 outputStream

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();

            if (outputStream != null) {
                outputStream.close();
            }

            // 通知系统相册刷新
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            mediaScanIntent.setData(imageUri);
            context.sendBroadcast(mediaScanIntent);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && false){
            /**
             *  在Android 10及更高版本中，直接在 /storage/emulated/0/Pictures/ 目录下创建文件可能会受到限制，
             *  因为在这些版本中，应用默认处于沙盒化的沙盒存储环境中，无法直接访问大多数共享存储空间目录。
             */
            // 在公共图库中创建一个文件
            File imageFile = createImageFile(context);

            if (imageFile != null) {
                try {
                    // 将图像数据写入文件
                    try (OutputStream outputStream = new FileOutputStream(imageFile)) {
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                        outputStream.flush();
                    }

                    // 通知图库刷新
                    values = new ContentValues();
                    values.put(MediaStore.Images.Media.DISPLAY_NAME, imageFile.getName());
                    values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");

                    ContentResolver contentResolver = context.getContentResolver();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        // Android 10及以上版本
                        values.put(MediaStore.Images.Media.IS_PENDING, 1);

                        Uri uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                        if (uri != null) {
                            MediaScannerConnection.scanFile(context, new String[]{imageFile.getAbsolutePath()}, null, (path, uri1) -> {
                                // 扫描完成后更新图像状态
                                values.clear();
                                values.put(MediaStore.Images.Media.IS_PENDING, 0);
                                contentResolver.update(uri, values, null, null);
                                // 打印Uri以进行调试
                                Log.d("MediaScanner", "Scanned file uri: " + uri.toString());
                            });

                        }
                    } else {
                        // Android 10以下版本
                        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *   返回一个File类型对象
     * @param context
     * @return java.io.File
     * @author Bush
     * @date 2023/12/15 15:34
    **/
    private static File createImageFile(Context context) {// 获取当前日期和时间
        Date currentDate = new Date();

        // 定义日期时间格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH:mm:ss");

        // 格式化日期时间
        String formattedDateTime = sdf.format(currentDate);
        String imageFileName = "JavaMathFormula" + formattedDateTime + ".png";

        // 公用媒体目录
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "JavaMath");

        // 确保目录存在
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                // 处理目录创建失败的情况
                Log.e("DirectoryCreation", "Failed to create directory");
            }
        }


        File storageDir;

        // 获取外部文件目录
        File externalFilesDir = context.getExternalFilesDir(null);
        if (externalFilesDir != null) {
            // 创建目录（如果不存在）
            File audioDirectory = new File(externalFilesDir, "app/audio/");
            if (!audioDirectory.exists()) {
                if (audioDirectory.mkdirs()) {
                    Log.d("DirectoryCreation", "Directory created successfully");
                } else {
                    Log.e("DirectoryCreation", "Failed to create directory");
                }
            }

            // 获取目录路径
            String path = audioDirectory.getAbsolutePath();

            // 这里继续使用 path
        } else {
            Log.e("DirectoryCreation", "External files directory is null");
        }

        String path;
        if (Build.VERSION.SDK_INT > 29) {
//            path = context.getExternalFilesDir(null).getAbsolutePath() + "/app/audio/";
            path = mediaStorageDir.getPath() + File.separator;
        } else {
            path = Environment.getExternalStorageDirectory().getPath() + "/app/audio/";
        }

        File imageFile = new File(path, imageFileName);
        return imageFile;
    }


    /**
     *   将字符串转义
     * @param rawString
     * @return java.lang.String
     * @author Bush
     * @date 2023/12/15 15:35
    **/
    public String escapeString(String rawString){
        /**
         * String类不可变，需要StringBuffer
         */
        StringBuffer stringBuffer = new StringBuffer(rawString);

        for(int i = 0; i < stringBuffer.length(); i++){
            if(stringBuffer.charAt(i) == '\\'){
                stringBuffer.insert(i, '\\');
                i++;
            }
        }

        return stringBuffer.toString();
    }

    /**
     *  初始化输入框
     * @author Bush
     * @date 2023/12/15 15:37
    **/
    protected void initTextIn(){
        textIn = (EditText) findViewById(R.id.latex_input);
        textIn.setInputType(InputType.TYPE_CLASS_TEXT);
        textIn.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);


        textIn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                escapedExpression = escapeString(charSequence.toString());
                // 不需要转义！！！！
                  rawExpression = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * 初始化确定键
     * @author Bush
     * @date 2023/12/15 15:37
    **/
    protected void initConfirmButton(){
        confirmButton = (Button)findViewById(R.id.button_confirm);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                // 在按钮点击时执行的操作
                if(rawExpression.length() == 0){
                    Snackbar.make(v, "请输入LaTeX表达式", Snackbar.LENGTH_SHORT).show();
                }
                else {
                    resultView.setText("\\[ " + rawExpression + " \\]") ;

                    saveButton.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     *  初始化保存键
     * @author Bush
     * @date 2023/12/15 15:38
    **/
    protected void initSaveButton() {
        saveButton = (Button) findViewById(R.id.button_save);
        saveButton.setVisibility(View.GONE);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                // 如果没有输入则不反应
                if(rawExpression.length() == 0){
                    return;
                }

                // 获取当前日期和时间
                Date currentDate = new Date();

                // 定义日期时间格式
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH:mm:ss");

                // 格式化日期时间
                String formattedDateTime = sdf.format(currentDate);

                // 保存图像
                // 检查是否有写入存储的权限
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)
                {
                    // 如果没有权限，请求权限
                    requestPermissions(
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_CODE_WRITE_STORAGE_PERMISSION);
                }
                else
                {
                    // 如果已经有权限，执行文件操作
//                    saveBitmapToGallery(LaTeXActivity.this, createBitmapFromView(resultView),
//                            "JavaMathFormula" + formattedDateTime);
                    saveImageToGallery(LaTeXActivity.this, createBitmapFromView(resultView));

                    Snackbar.make(v, "保存成功！", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     *  启动活动
     * @param context
     * @author Bush
     * @date 2023/12/17 15:38
    **/
    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, LaTeXActivity.class));
    }
}
