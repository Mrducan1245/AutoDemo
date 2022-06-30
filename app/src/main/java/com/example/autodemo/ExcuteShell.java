package com.example.autodemo;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;

public class ExcuteShell {
    public static void excuteShell(String cmd){
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmd);
            Log.e("result",""+process.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取输出流
        DataOutputStream dataOutputStream=new DataOutputStream(process.getOutputStream());
        //将命令写入
        try {
            dataOutputStream.write(cmd.getBytes());
            dataOutputStream.writeBytes("\n");
            dataOutputStream.writeBytes("exit\n");
            //提交命令
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //关闭流操作
        try {
            dataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
