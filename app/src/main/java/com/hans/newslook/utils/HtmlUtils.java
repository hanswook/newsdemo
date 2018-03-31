package com.hans.newslook.utils;

import com.hans.newslook.model.bean.zhihu.TheStoryBean;

/**
 * Created by hans
 * date: 2018/3/23 15:56.
 * e-mail: hxxx1992@163.com
 */
public class HtmlUtils {
    public static String structHtml(TheStoryBean storyDetailsEntity) {
        StringBuilder sb = new StringBuilder();
        sb.append("<div class=\"img-wrap\">")
                .append("<h1 class=\"headline-title\">")
                .append(storyDetailsEntity.getTitle()).append("</h1>")
                .append("<span class=\"img-source\">")
                .append(storyDetailsEntity.getImage_source()).append("</span>")
                .append("<img src=\"").append(storyDetailsEntity.getImage())
                .append("\" alt=\"\">")
                .append("<div class=\"img-mask\"></div>");
        //news_content_style.css和news_header_style.css都是在assets里的
        String mNewsContent = "<link rel=\"stylesheet\" type=\"text/css\" href=\"news_content_style.css\"/>"
                + "<link rel=\"stylesheet\" type=\"text/css\" href=\"news_header_style.css\"/>"
                + storyDetailsEntity.getBody().replace("<div class=\"img-place-holder\">", sb.toString());
        mNewsContent = getHtmlData(mNewsContent);
        return mNewsContent;
    }

    private static String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }
}