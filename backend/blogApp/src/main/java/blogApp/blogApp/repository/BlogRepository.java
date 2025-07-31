package blogApp.blogApp.repository;



import blogApp.blogApp.entity.Blog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends MongoRepository<Blog, String> {

    // Find all blogs by a specific user
    List<Blog> findByUserId(String userId);

    // Find blogs by category
    List<Blog> findByCategory(String category);

    // Find blogs by title containing keyword (case insensitive)
    List<Blog> findByTitleContainingIgnoreCase(String keyword);

    // Find blogs by tags containing specific tag
    List<Blog> findByTagsContaining(String tag);

    // Find all blogs ordered by creation date (newest first)
    List<Blog> findAllByOrderByCreatedAtDesc();
}