package com.sun.overweight.common.utils;
import java.io.*;

public class CopyFiles {

    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { // 文件存在时
                InputStream inStream = new FileInputStream(oldPath); // 读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; // 字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }

    }


    public   void copyFile(File sourceFile,File targetFile){
        if(!sourceFile.canRead()){
            System.out.println("源文件" + sourceFile.getAbsolutePath() + "不可读，无法复制！");
            return;
        }else{
            System.out.println("开始复制文件" + sourceFile.getAbsolutePath() + "到" + targetFile.getAbsolutePath());
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            FileOutputStream fos = null;
            BufferedOutputStream bos = null;

            try{
                fis = new FileInputStream(sourceFile);
                bis = new BufferedInputStream(fis);
                fos = new FileOutputStream(targetFile);
                bos = new BufferedOutputStream(fos);
                int len = 0;
                while((len = bis.read()) != -1){
                    bos.write(len);
                }
                bos.flush();

            }catch(FileNotFoundException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }finally{
                try{
                    if(fis != null){
                        fis.close();
                    }
                    if(bis != null){
                        bis.close();
                    }
                    if(fos != null){
                        fos.close();
                    }
                    if(bos != null){
                        bos.close();
                    }
                    System.out.println("文件" + sourceFile.getAbsolutePath() + "复制到" + targetFile.getAbsolutePath() + "完成");
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public   void copyDirectory(String sourcePathString,String targetPathString){
        if(!new File(sourcePathString).canRead()){
            System.out.println("源文件夹" + sourcePathString + "不可读，无法复制！");
            return;
        }else{
            (new File(targetPathString)).mkdirs();
            System.out.println("开始复制文件夹" + sourcePathString + "到" + targetPathString);
            File[] files = new File(sourcePathString).listFiles();
            for(int i = 0; i < files.length; i++){
                if(files[i].isFile()){
                    copyFile(new File(sourcePathString + File.separator + files[i].getName()),new File(targetPathString + File.separator + files[i].getName()));
                }else if(files[i].isDirectory()){
                    copyDirectory(sourcePathString + File.separator + files[i].getName(),targetPathString + File.separator + files[i].getName());
                }
            }
            System.out.println("复制文件夹" + sourcePathString + "到" + targetPathString + "结束");
        }
    }

}
