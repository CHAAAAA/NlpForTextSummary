package cn.javanese.nlp.summary;

import java.io.*;

/**
 * io 操作
 */
public class FileUtil {
    private final static String charset = "UTF-8";
    public static String RESULT_FILE_PATH = "";

    /**
     * 读取题目文件 并生成NewsSummary类
     */
    public static NewsSummary readFile(File file) {
        NewsSummary newsSummary = new NewsSummary();
        if (file.isFile()) {
            FileInputStream fileInputStream;
            InputStreamReader inputStreamReader;
            BufferedReader bufferedReader = null;
            try {
                fileInputStream = new FileInputStream(file);
                inputStreamReader = new InputStreamReader(fileInputStream, charset);
                bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder content = new StringBuilder();
                String s;
                int i = 1;
                //建立 NewsSummary 类
                while ((s = bufferedReader.readLine()) != null) {
                    if (i == 1) {
                        newsSummary.setTitle(s);
                    } else if (i == 2) {
                        newsSummary.setModelAnswer(s);
                    } else {
                        content.append(s);
                    }
                    i++;
                }
                newsSummary.setContent(content.toString());
                //计算该材料的标题
                newsSummary.doCompute();
                writeResult(newsSummary);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
        }
        return newsSummary;
    }

    /**
     * 将结果写入 result.txt 文件中
     *
     * @param newsSummary
     */
    public static void writeResult(NewsSummary newsSummary) throws IOException {
        File result = new File(RESULT_FILE_PATH);
        if (!result.exists()) {
            result.createNewFile();
        }

        OutputStreamWriter outputStreamWriter;
        FileOutputStream fileOutputStream;
        BufferedWriter bufferedWriter = null;
        try {
            fileOutputStream = new FileOutputStream(result, true);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, charset);
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.append(newsSummary.toString());
            bufferedWriter.append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (Exception ioe) {
                ioe.printStackTrace();
            }
        }
    }

    /**
     * 删除 result.txt 文件
     */
    public static void cleanResult() {
        File result = new File(RESULT_FILE_PATH);
        if (result.exists()) {
            result.delete();
        }
    }
}
