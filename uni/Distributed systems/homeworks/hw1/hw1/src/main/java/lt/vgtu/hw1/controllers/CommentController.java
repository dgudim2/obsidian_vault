package lt.vgtu.hw1.controllers;

import com.google.gson.Gson;
import lt.vgtu.hw1.model.Comment;
import lt.vgtu.hw1.model.Product;
import lt.vgtu.hw1.model.User;
import lt.vgtu.hw1.repos.CommentRepo;
import lt.vgtu.hw1.repos.ProductRepo;
import lt.vgtu.hw1.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Properties;

/**
 * The type Comment controller.
 */
@RestController
public class CommentController {

    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private UserRepo userRepo;


    /**
     * Gets all comments.
     *
     * @return the all comments
     */
    @GetMapping(value = "/getAllComments")
    public @ResponseBody Iterable<Comment> getAllComments() {
        return commentRepo.findAll();
    }

    /**
     * Create comment comment.
     *
     * @param comment the comment
     * @return the comment
     */
    @PostMapping(value = "/createComment")
    public @ResponseBody Comment createComment(@RequestBody Comment comment) {
        commentRepo.save(comment);
        return new Comment();
    }

    /**
     * Create comment with ids comment.
     *
     * @param info the info
     * @return the comment
     */
    @PostMapping(value = "/createCommentWithIds")
    public @ResponseBody Comment createCommentWithIds(@RequestBody String info) {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(info, Properties.class);

        var commentTitle = properties.getProperty("commentTitle");
        var commentBody = properties.getProperty("commentBody");
        int whichProductCommented = Integer.parseInt(properties.getProperty("whichProductCommented"));
        int commentOwner = Integer.parseInt(properties.getProperty("commentOwner"));
        var parentComment = properties.getProperty("parentComment");
        var rating = Float.parseFloat(properties.getProperty("rating"));


        Comment comment = new Comment();

        comment.setCommentTitle(commentTitle);
        comment.setCommentBody(commentBody);
        comment.setRating(rating);

        Optional<Product> productOptional = productRepo.findById(whichProductCommented);
        productOptional.ifPresent(product -> {
            product.getComments().add(comment);
            comment.setWhichProductCommented(product);
        });

        Optional<User> userOptional = userRepo.findById(commentOwner);
        userOptional.ifPresent(user -> {
            user.getComments().add(comment);
            comment.setCommentOwner(user);
        });

        if (parentComment != null && !parentComment.isEmpty()) {
            int parentCommentId = Integer.parseInt(parentComment);

            Optional<Comment> commentOptional = commentRepo.findById(parentCommentId);
            commentOptional.ifPresent(comment1 -> {
                comment1.getReplies().add(comment);
            });

        }

        commentRepo.save(comment);

        return comment;
    }


    /**
     * Updatee comment comment.
     *
     * @param comment the comment
     * @return the comment
     */
    @PutMapping(value = "/updateCommentObject")
    public @ResponseBody Comment updateeComment(@RequestBody Comment comment) {
        commentRepo.save(comment);
        return new Comment();
    }

    /**
     * Update comment object comment.
     *
     * @param info the info
     * @return the comment
     */
    @PostMapping(value = "/updateComment")
    public @ResponseBody Comment updateCommentObject(@RequestBody String info) {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(info, Properties.class);

        var id = Integer.parseInt(properties.getProperty("id"));
        var commentTitle = properties.getProperty("commentTitle");
        var commentBody = properties.getProperty("commentBody");
        int whichProductCommented = Integer.parseInt(properties.getProperty("whichProductCommented"));
        int commentOwner = Integer.parseInt(properties.getProperty("commentOwner"));
        var parentComment = properties.getProperty("parentComment");
        var rating = Float.parseFloat(properties.getProperty("rating"));


        Optional<Comment> optionalComment = commentRepo.findById(id);
        if (optionalComment.isPresent()) {

            Comment comment = optionalComment.get();

            comment.setCommentTitle(commentTitle);
            comment.setCommentBody(commentBody);
            comment.setRating(rating);

            commentRepo.save(comment);

            return comment;
        } else {
            throw new RuntimeException("Comment with ID " + id + " not found");
        }
    }

    /**
     * Delete comment response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping(value = "/deleteComment/{id}")
    public @ResponseBody ResponseEntity<String> deleteComment(@PathVariable(name = "id") int id) {
        Optional<Comment> commentOptional = commentRepo.findById(id);

        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            deleteReplies(comment);
            unlinkComment(comment);
            commentRepo.deleteById(id);
            return ResponseEntity.ok().body("Comment with ID " + id + " has been deleted successfully.");
        } else {
            return new ResponseEntity<>("Comment with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }

    private void deleteReplies(Comment comment) {
        if (comment.getReplies() != null) {
            for (Comment reply : comment.getReplies()) {
                deleteReplies(reply);
                unlinkComment(reply);
                commentRepo.deleteById(reply.getId());
            }
        }
    }

    private void unlinkComment(Comment comment) {
        if (comment.getWhichProductCommented() != null) {
            Product product = comment.getWhichProductCommented();
            product.getComments().remove(comment);
            productRepo.save(product);
            comment.setWhichProductCommented(null);
        }
        if (comment.getCommentOwner() != null) {
            User user = comment.getCommentOwner();
            user.getComments().remove(comment);
            userRepo.save(user);
            comment.setCommentOwner(null);
        }
        if (comment.getParentComment() != null) {
            Comment parentComment = comment.getParentComment();
            parentComment.getReplies().remove(comment);
            commentRepo.save(comment);
            comment.setParentComment(null);
        }
        if (comment.getReplies() != null) {
            for (Comment reply : comment.getReplies()) {
                reply.setParentComment(null);
                commentRepo.save(reply);
            }
            comment.getReplies().clear();
        }
    }
}
