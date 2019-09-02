import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.WordDictionary;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Description 测试jieba分词器
 * Created with guoshuai
 * date 2019/8/30 15:00
 */
public class JiebaTest {
    private static final String PROPERTIES_PATH = "jieba.dict";
    public static void main(String args[]){
        JiebaSegmenter segmenter = new JiebaSegmenter();
        String sentences = "浔阳区长虹北路长江一桥红绿灯路口处";
        System.out.println(segmenter.process(sentences, JiebaSegmenter.SegMode.SEARCH).toString());

        // 词典路径为Resource/dicts/jieba.dict
        Path path = Paths.get(new File(JiebaTest.class.getClassLoader().getResource("jieba.dict").getPath()).getAbsolutePath());
        //加载自定义的词典进词库
        WordDictionary.getInstance().loadUserDict(path) ;

        //重新分词
        segmenter = new JiebaSegmenter();
        System.out.println(segmenter.process(sentences, JiebaSegmenter.SegMode.SEARCH).toString());


       /* List<SegToken> tokenList = segmenter.process(sentences, JiebaSegmenter.SegMode.SEARCH);*/
        /*for(SegToken token:tokenList){
            System.out.println(token.word);
        }*/
        /*for(int i=0;i<tokenList.size();i++){
            System.out.println(tokenList.get(i).word);
        }*/
        /*if(tokenList.size()>=3){
            for(int i=0;i<3;i++){
                System.out.println(tokenList.get(i).word);
                //System.out.println("1");

            }
        }
        if(tokenList.isEmpty()){
            for(int i=0;i<3;i++){
                System.out.println(tokenList.get(i).word);
                //System.out.println("2");
            }
        }*/
    }
}
