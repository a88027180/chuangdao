import com.xiyoukeji.entity.Article;
import com.xiyoukeji.tools.ArticleType;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Matilda on 2016/12/13.
 */
public class Test {
    public static void main(String[] args) {
        List<Article> list = new ArrayList<>();
        Date date1 = new Date();
        Article article1 = new Article(1,date1);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        cal.add(Calendar.DATE,1);
        Date date2 =cal.getTime();
        Article article2 = new Article(2,date2);
        list.add(article1);
        list.add(article2);
        Collections.reverse(list);
        System.out.println(list.get(0).getId());
    }
}
