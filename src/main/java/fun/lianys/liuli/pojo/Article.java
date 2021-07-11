package fun.lianys.liuli.pojo;

import org.apache.ibatis.type.Alias;

@Alias(value = "article")
public class Article {
    private long id;
    private long time;
    private String href;
    private String imgSrc;
    private String tags;
    private String content;
    private String title;
    private int ratingCount;
    private float ratingScore;
    private String uid;

    public Article() {
    }

    public Article(long id, long time, String href, String imgSrc, String tags, String content, String title, int ratingCount, float ratingScore, String uid) {
        this.id = id;
        this.time = time;
        this.href = href;
        this.imgSrc = imgSrc;
        this.tags = tags;
        this.content = content;
        this.title = title;
        this.ratingCount = ratingCount;
        this.ratingScore = ratingScore;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public float getRatingScore() {
        return ratingScore;
    }

    public void setRatingScore(float ratingScore) {
        this.ratingScore = ratingScore;
    }
}
