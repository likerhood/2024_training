package com.example.week3test.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;

public class FileUtil {

    // 把字符串保存到指定路径的文件
    public static void saveText(String path, String txt){
        BufferedWriter os = null;
        try{
            os = new BufferedWriter(new FileWriter(path));
            os.write(txt);  // 将文本内容写入文件
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null){
                try {
                    os.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    // 从指定路径的文本文件中读取内容
    public static String openText(String path){
        BufferedReader is = null;
        StringBuilder sb = new StringBuilder();
        try{
            is = new BufferedReader(new FileReader(path));
            String line = null;
            while ((line = is.readLine()) != null){
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null){
                try {
                    is.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }

    public static void saveImage(String path, Bitmap bitmap) {
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static Bitmap openImage(String path){
        Bitmap bitmap = null;
        FileInputStream fis = null;
        try{
            fis = new FileInputStream(path);
            bitmap = BitmapFactory.decodeStream(fis);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return bitmap;
    }

}
