package blogApp.blogApp.entity;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private String commentText;
    private String commentedBy;
    private LocalDateTime commentedAt;
}
