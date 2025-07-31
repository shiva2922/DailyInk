package blogApp.blogApp.dto;



import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogResponse {

    private String id;
    private String title;
    private String content;
    private String category;
    private List<String> tags;
    private String image;
    private String userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructor to create response from entity
    public BlogResponse(blogApp.blogApp.entity.Blog blog) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.content = blog.getContent();
        this.category = blog.getCategory();
        this.tags = blog.getTags();
        this.image = blog.getImage();
        this.userId = blog.getUserId();
        this.createdAt = blog.getCreatedAt();
        this.updatedAt = blog.getUpdatedAt();
    }
}