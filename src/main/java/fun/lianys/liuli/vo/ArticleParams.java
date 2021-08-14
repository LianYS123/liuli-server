package fun.lianys.liuli.vo;

import lombok.Data;

@Data
public class ArticleParams {
    private Integer page = 1;
    private Integer pageSize = 20;
    private String keyword;
    private String order;
    private String orderType = "asc";
}
