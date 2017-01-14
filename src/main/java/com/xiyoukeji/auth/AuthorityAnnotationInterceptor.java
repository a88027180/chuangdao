package com.xiyoukeji.auth;

import com.xiyoukeji.entity.Article;
import com.xiyoukeji.tools.ArticleType;
import com.xiyoukeji.tools.BaseDaoImpl;
import com.xiyoukeji.tools.State;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Matilda on 2017/1/13.
 */
public class AuthorityAnnotationInterceptor extends HandlerInterceptorAdapter {
    final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    BaseDaoImpl<Article> articleBaseDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if(handler instanceof HandlerMethod) {
            HandlerMethod handler1 = (HandlerMethod) handler;
            EditAuthority editAuthority = handler1.getMethodAnnotation(EditAuthority.class);

            // 没有声明权限，放行
            if(editAuthority == null) {
                return true;
            }

            logger.info("editAuthority");

            boolean flag = true;
            HttpSession session = request.getSession();
            if(session.getAttribute("type")==null)
                flag = false;
            Integer type = (Integer) session.getAttribute("type");

            if(type == 0)
                return true;


            // 注解权限不足: 2或3没有权限
            if(!editAuthority.value().contains(""+type)) {
                flag = false;
            }

            // 注解有权限
            if(type == 3){
                // 权限3的人添加文章时超出权限范围
                String url = request.getRequestURI();
                if(url.equals("/addArticle") || url.equals("/editArticle")) {
                    String articleType = request.getParameter("type");
                    if(!articleType.equals(ArticleType.RESEARCH_REPORT.name())
                            && !articleType.equals(ArticleType.WEEK_OBSERVATION.name()))
                        flag = false;

                } else if(url.equals("/deleteArticle")) {
                    Integer id = Integer.valueOf(request.getParameter("id"));
                    Article article = articleBaseDao.get(Article.class, id);
                    String articleType = article.getType();
                    if(!articleType.equals(ArticleType.RESEARCH_REPORT.name())
                            && !articleType.equals(ArticleType.WEEK_OBSERVATION.name()))
                        flag = false;
                }
            }

            if(!flag) {
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json; charset=utf-8");

                JSONObject json = new JSONObject();
                json.put("state", State.NO_PERMISSION.value());
                json.put("detail", State.NO_PERMISSION.desc());

                PrintWriter out = null;
                try {
                    out = response.getWriter();
                    out.append(json.toString());
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (out != null) {
                        out.close();
                    }
                }
                return false;
            } else
                return true;
        }

        return true;

    }

}
