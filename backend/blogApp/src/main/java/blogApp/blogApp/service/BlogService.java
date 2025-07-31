package blogApp.blogApp.service;



import blogApp.blogApp.entity.Blog;
import blogApp.blogApp.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    // Create a new blog post
    public Blog createBlog(String title, String content, String category, List<String> tags, String image, String userId) {
        Blog blog = new Blog(title, content, category, tags, image, userId);
        return blogRepository.save(blog);
    }

    // Get all blogs (for home page)
    public List<Blog> getAllBlogs() {
        return blogRepository.findAllByOrderByCreatedAtDesc();
    }

    // Get blog by ID
    public Optional<Blog> getBlogById(String id) {
        return blogRepository.findById(id);
    }

    // Get all blogs by a specific user
    public List<Blog> getBlogsByUserId(String userId) {
        return blogRepository.findByUserId(userId);
    }

    // Get blogs by category
    public List<Blog> getBlogsByCategory(String category) {
        return blogRepository.findByCategory(category);
    }

    // Search blogs by title
    public List<Blog> searchBlogsByTitle(String keyword) {
        return blogRepository.findByTitleContainingIgnoreCase(keyword);
    }

    // Get blogs by tag
    public List<Blog> getBlogsByTag(String tag) {
        return blogRepository.findByTagsContaining(tag);
    }

    // Update blog
    public Optional<Blog> updateBlog(String id, String title, String content, String category, List<String> tags, String image) {
        Optional<Blog> optionalBlog = blogRepository.findById(id);
        if (optionalBlog.isPresent()) {
            Blog blog = optionalBlog.get();
            blog.setTitle(title);
            blog.setContent(content);
            blog.setCategory(category);
            blog.setTags(tags);
            blog.setImage(image);
            blog.setUpdatedAt(LocalDateTime.now());
            return Optional.of(blogRepository.save(blog));
        }
        return Optional.empty();
    }

    // Delete blog
    public boolean deleteBlog(String id) {
        if (blogRepository.existsById(id)) {
            blogRepository.deleteById(id);
            return true;
        }
        return false;
    }
}