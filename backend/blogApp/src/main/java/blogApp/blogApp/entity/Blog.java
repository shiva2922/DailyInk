package blogApp.blogApp.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "UserBlogs")
public class Blog {

    @Id
    private String id;

    private String title;

    private String content;

    private String category;

    private List<String> tags;

    private String image;

    private String userId; // This will link the blog to a specific user

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // Custom constructor for creating new blog posts
    public Blog(String title, String content, String category, List<String> tags, String image, String userId) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.tags = tags;
        this.image = image;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}