package cn.javanese.nlp;


import cn.javanese.nlp.summary.FileUtil;
import cn.javanese.nlp.summary.NewsSummary;

import java.io.*;
import java.util.logging.Logger;

/**
 * 计算
 */
public class ApplicationMain {

    private static final Logger logger = Logger.getLogger(ApplicationMain.class.getName());

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        //需要计算的目录
        String questionFilesPath;

        if (args.length == 2) {
            questionFilesPath = args[0];
            FileUtil.RESULT_FILE_PATH = args[1];
        } else if (args.length == 0) {
            String workplacePrefix = System.getProperty("user.dir") + File.separator +
                    "workspace" + File.separator;
            questionFilesPath = workplacePrefix + "newsSummary";
            FileUtil.RESULT_FILE_PATH = workplacePrefix + "result.txt";
        } else {
            throw new Exception("USAGE: \n  java **.jar \n  java **.jar questionsFilesPath resultFilePath");
        }
        //清空结果文件
        FileUtil.cleanResult();

        //一句话新闻拟标题 测试题目目录
        File newsQuestionDir = new File(questionFilesPath);

        if (newsQuestionDir.exists() && newsQuestionDir.isDirectory()) {
            File[] newsFiles = newsQuestionDir.listFiles();
            try {
                for (File f : newsFiles) {
                    //每个题目创建一个 NewsSummary 类保存该题目的信息
                    NewsSummary newsSummary = FileUtil.readFile(f);
                    logger.info(newsSummary.toString());
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
}
