package com.ssafy.forest.domain.dto.response;

import com.ssafy.forest.domain.entity.Article;
import com.ssafy.forest.domain.entity.ArticleImage;
import com.ssafy.forest.domain.entity.ArticleTemp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ArticleResDto {

    private Long id;
    private Long memberId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<String> imageList;

    public static ArticleResDto from(Article article) {
        return new ArticleResDto(
            article.getId(),
            article.getMember().getId(),
            article.getContent(),
            article.getCreatedAt(),
            article.getModifiedAt(),
            article.getImages().stream().map(ArticleImage::getImageURL).collect(Collectors.toList())
        );
    }

    public static ArticleResDto fromTemp(ArticleTemp articleTemp) {
        return new ArticleResDto(
            articleTemp.getId(),
            articleTemp.getMember().getId(),
            articleTemp.getContent(),
            articleTemp.getCreatedAt(),
            articleTemp.getModifiedAt(),
            articleTemp.getImages().stream().map(ArticleImage::getImageURL).collect(Collectors.toList())
        );
    }

}