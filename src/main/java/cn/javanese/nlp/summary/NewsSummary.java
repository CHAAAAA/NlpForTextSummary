package cn.javanese.nlp.summary;

import com.hankcs.hanlp.HanLP;
import org.apdplat.word.analysis.SimHashPlusHammingDistanceTextSimilarity;
import org.apdplat.word.analysis.TextSimilarity;

import java.util.Iterator;
import java.util.List;

/**
 * 一句话新闻拟标题
 */
public class NewsSummary {

    //题目的出处
    String title;

    //题目的参考答案
    String modelAnswer;

    //题目内容
    String content;

    //计算得到的答案
    String nlpAnswer;

    //计算得到的答案与参考答案之间的语义距离
    double distance;

    //相似度计算类
    static TextSimilarity textSimilarity = new SimHashPlusHammingDistanceTextSimilarity();

    public void setContent(String content) {
        this.content = content;
    }

    public void setModelAnswer(String modelAnswer) {
        this.modelAnswer = modelAnswer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *  根据Hanlp计算材料的标题
     */
    public void doCompute() {
        List<String> sentenceList = HanLP.extractSummary(content, 1);
        Iterator<String> iterable = sentenceList.iterator();
        StringBuilder stringBuilder = new StringBuilder();
        if (iterable.hasNext()) {
            stringBuilder.append(iterable.next());
        }
        nlpAnswer = stringBuilder.toString();
        getDistance();
    }

    /**
     * 根据 SimHash + 汉明距离 计算参考答案与计算得到答案之间的语义距离
     * @return
     */
    private double getDistance() {
        this.distance = textSimilarity.similarScore(this.nlpAnswer, this.modelAnswer);
        return this.distance;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("题目出处: ").append(this.title).append("\n");
        stringBuilder.append("参考答案: ").append(this.modelAnswer).append("\n");
        stringBuilder.append("NLP答案: ").append(this.nlpAnswer).append("\n");
        stringBuilder.append("准确度: ").append(this.distance).append("\n");
        return stringBuilder.toString();
    }
}
