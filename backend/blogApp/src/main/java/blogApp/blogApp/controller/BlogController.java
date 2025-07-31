package blogApp.blogApp.controller;



import blogApp.blogApp.dto.BlogResponse;
import blogApp.blogApp.dto.CreateBlogRequest;
import blogApp.blogApp.entity.Blog;
import blogApp.blogApp.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/blogs")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Allow all origins for now, configure properly in production
public class BlogController {

    private final BlogService blogService;

    // Create a new blog post
    @PostMapping
    public ResponseEntity<?> createBlog(@Valid @RequestBody CreateBlogRequest request) {
        try {
            Blog createdBlog = blogService.createBlog(
                    request.getTitle(),
                    request.getContent(),
                    request.getCategory(),
                    request.getTags(),
                    request.getImage(),
                    request.getUserId()
            );

            BlogResponse response = new BlogResponse(createdBlog);
            return ResponseEntity.status(HttpStatus.CREATED).body("Blog successfully added");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating blog: " + e.getMessage());
        }
    }

    // Get all blogs (for home page)
    @GetMapping
    public ResponseEntity<List<BlogResponse>> getAllBlogs() {
        try {
            List<Blog> blogs = blogService.getAllBlogs();
            List<BlogResponse> response = blogs.stream()
                    .map(BlogResponse::new)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Get blog by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getBlogById(@PathVariable String id) {
        try {
            Optional<Blog> blog = blogService.getBlogById(id);
            if (blog.isPresent()) {
                BlogResponse response = new BlogResponse(blog.get());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Blog not found with id: " + id);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching blog: " + e.getMessage());
        }
    }

    // Get blogs by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BlogResponse>> getBlogsByUserId(@PathVariable String userId) {
        try {
            List<Blog> blogs = blogService.getBlogsByUserId(userId);
            List<BlogResponse> response = blogs.stream()
                    .map(BlogResponse::new)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Get blogs by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<BlogResponse>> getBlogsByCategory(@PathVariable String category) {
        try {
            List<Blog> blogs = blogService.getBlogsByCategory(category);
            List<BlogResponse> response = blogs.stream()
                    .map(BlogResponse::new)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Search blogs by title
    @GetMapping("/search")
    public ResponseEntity<List<BlogResponse>> searchBlogs(@RequestParam String keyword) {
        try {
            List<Blog> blogs = blogService.searchBlogsByTitle(keyword);
            List<BlogResponse> response = blogs.stream()
                    .map(BlogResponse::new)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Get blogs by tag
    @GetMapping("/tag/{tag}")
    public ResponseEntity<List<BlogResponse>> getBlogsByTag(@PathVariable String tag) {
        try {
            List<Blog> blogs = blogService.getBlogsByTag(tag);
            List<BlogResponse> response = blogs.stream()
                    .map(BlogResponse::new)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Update blog
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBlog(@PathVariable String id, @Valid @RequestBody CreateBlogRequest request) {
        try {
            Optional<Blog> updatedBlog = blogService.updateBlog(
                    id,
                    request.getTitle(),
                    request.getContent(),
                    request.getCategory(),
                    request.getTags(),
                    request.getImage()
            );

            if (updatedBlog.isPresent()) {
                BlogResponse response = new BlogResponse(updatedBlog.get());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Blog not found with id: " + id);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating blog: " + e.getMessage());
        }
    }

    // Delete blog
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBlog(@PathVariable String id) {
        try {
            boolean deleted = blogService.deleteBlog(id);
            if (deleted) {
                return ResponseEntity.ok("Blog deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Blog not found with id: " + id);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting blog: " + e.getMessage());
        }
    }
}