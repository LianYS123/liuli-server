package fun.lianys.liuli.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Alias(value = "article")
@Data
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
}
