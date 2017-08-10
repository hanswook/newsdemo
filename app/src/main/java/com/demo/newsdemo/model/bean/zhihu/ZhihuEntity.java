package com.demo.newsdemo.model.bean.zhihu;

import java.util.List;


/**
 * Created by hans on 2017/5/11 15:56.
 */

public class ZhihuEntity {

    /**
     * date : 20170511
     * stories : [{"images":["https://pic4.zhimg.com/v2-abf262f484c3cc4d9282749f1fccc1ef.jpg"],"type":0,"id":9412923,"ga_prefix":"051114","title":"它已经诞生了一年，可「国产主机」却似乎离我们更远了"},{"images":["https://pic2.zhimg.com/v2-ddcbe7cc3f330bff62e0ccb218983269.jpg"],"type":0,"id":9413446,"ga_prefix":"051113","title":"话剧演员看不起影视剧演员，会有这样的「鄙视链」吗？"},{"images":["https://pic3.zhimg.com/v2-9b73118a0eaa3cac624e40784b356c6e.jpg"],"type":0,"id":9412486,"ga_prefix":"051112","title":"大误 · 你这么大个人，只吃这么点？"},{"images":["https://pic4.zhimg.com/v2-f0974595392eb5aa6cf612de34bb0653.jpg"],"type":0,"id":9412930,"ga_prefix":"051111","title":"足球世界里最难打破的纪录，还真不是你现在想到的那个"},{"images":["https://pic2.zhimg.com/v2-4783259f81ea5a729cf7fb0d4be2b041.jpg"],"type":0,"id":9409932,"ga_prefix":"051109","title":"霸王条款写得爽，有没有考虑过广大网友的感受\u2026\u2026"},{"images":["https://pic1.zhimg.com/v2-bb3684ce7796050b04c7dad5d73f5648.jpg"],"type":0,"id":9412918,"ga_prefix":"051108","title":"游戏的 AI 是让游戏更好玩，而不是虐到你不想玩"},{"images":["https://pic1.zhimg.com/v2-6fcede9934f77d42b30613740ade151c.jpg"],"type":0,"id":9412459,"ga_prefix":"051107","title":"不用统计数据，一张夜景也能看出经济发展水平"},{"images":["https://pic3.zhimg.com/v2-a9195970796cdb4165193b53da363126.jpg"],"type":0,"id":9412643,"ga_prefix":"051107","title":"日本贫富差距小吗？嗯\u2026\u2026看起来年轻人很不开心啊"},{"images":["https://pic1.zhimg.com/v2-7ddd68ea72df596f89ccee3f37f7f200.jpg"],"type":0,"id":9412493,"ga_prefix":"051107","title":"刷一会手机看了好几条，短视频怎么就越来越火？"},{"images":["https://pic1.zhimg.com/v2-1d969f75849e94c2465d8f54c1c4bed0.jpg"],"type":0,"id":9412077,"ga_prefix":"051106","title":"瞎扯 · 如何正确地吐槽"}]
     * top_stories : [{"image":"https://pic1.zhimg.com/v2-6b8c3f499f0d027b23407381f67422fc.jpg","type":0,"id":9412923,"ga_prefix":"051114","title":"它已经诞生了一年，可「国产主机」却似乎离我们更远了"},{"image":"https://pic2.zhimg.com/v2-f840d9881387360f09dcd7780f200df9.jpg","type":0,"id":9412493,"ga_prefix":"051107","title":"刷一会手机看了好几条，短视频怎么就越来越火？"},{"image":"https://pic3.zhimg.com/v2-9632c46d471691a9c0c0fceb0b3397c2.jpg","type":0,"id":9412038,"ga_prefix":"051015","title":"「死亡游戏」的可怕之处，在于把自杀意念一步步变成行动"},{"image":"https://pic3.zhimg.com/v2-68c9e0dab06245794aca8816d9de4842.jpg","type":0,"id":9410616,"ga_prefix":"051008","title":"日本签证放宽，主要宽在对收入的要求上"},{"image":"https://pic2.zhimg.com/v2-e485e8d05df7d15f9ce3c53657676abd.jpg","type":0,"id":9410567,"ga_prefix":"051007","title":"《银河护卫队 2》几个出彩的配角有何来路？"}]
     */

    private String date;
    private List<StoriesBean> stories;
    private List<TopStoriesBean> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public List<TopStoriesBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoriesBean> top_stories) {
        this.top_stories = top_stories;
    }



}
