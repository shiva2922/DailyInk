package blogApp.blogApp.dto;



import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.xml.stream.events.Comment;
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
    private int likes;
    private int views;
    private List<Comment> comments;

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
        this.likes = blog.getLikes();
        this.views = blog.getViews();
        this.createdAt = blog.getCreatedAt();
        this.updatedAt = blog.getUpdatedAt();
        this.comments = blog.getComments();

    }


}