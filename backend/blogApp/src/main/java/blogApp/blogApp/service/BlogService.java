package blogApp.blogApp.service;



import blogApp.blogApp.dto.BlogResponse;
import blogApp.blogApp.dto.CreateBlogRequest;
import blogApp.blogApp.entity.Blog;
import blogApp.blogApp.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.Comment;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    // Create a new blog post
    public BlogResponse createBlog(CreateBlogRequest request, String userId) {
        Blog blog = new Blog();
        blog.setTitle(request.getTitle());
        blog.setContent(request.getContent());
        blog.setCategory(request.getCategory());
        blog.setTags(request.getTags());
        blog.setImage(request.getImage());
        blog.setCreatedAt(LocalDateTime.now());
        blog.setUpdatedAt(LocalDateTime.now());
        blog.setUserId(userId); // <- Link the user to blog

        Blog saved=blogRepository.save(blog);

        return new BlogResponse(saved);
    }


    // Get all blogs (for home page)
    public List<Blog> getAllBlogs() {
        return blogRepository.findAllByOrderByCreatedAtDesc();
    }

    // Get blog by ID
    public Optional<Blog> getBlogById(String id) {
        return blogRepository.findById(id);
    }
    //---
    public Optional<Blog> getBlogByIdAndUpdateViews(String blogId, String currentUserId) {
        Optional<Blog> blogOpt = blogRepository.findById(blogId);
        if (blogOpt.isPresent()) {
            Blog blog = blogOpt.get();

            // Check if user has already viewed
            if (!blog.getViewedByUsers().contains(currentUserId)) {
                blog.getViewedByUsers().add(currentUserId);
                blog.setViews(blog.getViews() + 1);
                blogRepository.save(blog);
            }

            return Optional.of(blog);
        } else {
            return Optional.empty();
        }
    }

    //--

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

    //---like
    public Optional<Blog> toggleLike(String blogId, String userId) {
        Optional<Blog> blogOpt = blogRepository.findById(blogId);
        if (blogOpt.isPresent()) {
            Blog blog = blogOpt.get();

            if (blog.getLikedByUsers().contains(userId)) {
                // Already liked → remove like
                blog.getLikedByUsers().remove(userId);
                blog.setLikes(blog.getLikes() - 1);

            } else {
                // Not liked → add like
                blog.getLikedByUsers().add(userId);
                blog.setLikes(blog.getLikes() + 1);

            }

            blog.setUpdatedAt(LocalDateTime.now());
            blogRepository.save(blog);
            return Optional.of(blog);

        }
        return Optional.empty();
    }

    //-------comment
    public Optional<Blog> addComment(String blogId, String userId, String commentText) {
        Optional<Blog> blogOpt = blogRepository.findById(blogId);
        if (blogOpt.isPresent()) {
            Blog blog = blogOpt.get();

            Comment comment = new Comment();
            comment.setCommentText(commentText);
            comment.setCommentedBy(userId);
            comment.setCommentedAt(LocalDateTime.now());

            blog.getComments().add(comment);
            blog.setUpdatedAt(LocalDateTime.now());

            blogRepository.save(blog);
            return Optional.of(blog);
        }
        return Optional.empty();
    }




}