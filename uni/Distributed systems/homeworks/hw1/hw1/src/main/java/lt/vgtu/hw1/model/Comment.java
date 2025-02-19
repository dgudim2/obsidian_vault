package lt.vgtu.hw1.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlIDREF;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * The type Comment.
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Comment {
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Setter
    private String commentTitle;
    @Setter
    private String commentBody;
    @ManyToOne()
    private Product whichProductCommented;
    @ManyToOne()
    private User commentOwner;
    @Setter
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> replies;
    @ManyToOne()
    private Comment parentComment;
    @Setter
    private float rating;

    /**
     * Instantiates a new Comment (No linking)
     */
    public Comment(String commentTitle, String commentBody, float rating) {
        this.commentTitle = commentTitle;
        this.commentBody = commentBody;
        this.rating = rating;
    }

    /**
     * Instantiates a new Comment. (Product linking)
     */
    public Comment(String commentTitle, String commentBody, Product whichProductCommented, User commentOwner, float rating) {
        this.commentTitle = commentTitle;
        this.commentBody = commentBody;
        this.whichProductCommented = whichProductCommented;
        this.commentOwner = commentOwner;
        this.rating = rating;
    }

    /**
     * Instantiates a new Comment. (User linking)
     */
    public Comment(String commentTitle, String commentBody, User commentOwner, Comment parentComment, float rating) {
        this.commentTitle = commentTitle;
        this.commentBody = commentBody;
        this.commentOwner = commentOwner;
        this.parentComment = parentComment;
        this.rating = rating;
    }

    @XmlElement
    public int getId() {
        return id;
    }

    @XmlElement
    public String getCommentTitle() {
        return commentTitle;
    }

    @XmlElement
    public String getCommentBody() {
        return commentBody;
    }

    @XmlElement
    public Product getWhichProductCommented() {
        return whichProductCommented;
    }

    @XmlIDREF
    public void setWhichProductCommented(Product whichProductCommented) {
        this.whichProductCommented = whichProductCommented;
    }

    @XmlElement
    public User getCommentOwner() {
        return commentOwner;
    }

    @XmlIDREF
    public void setCommentOwner(User commentOwner) {
        this.commentOwner = commentOwner;
    }

    @XmlElement
    public List<Comment> getReplies() {
        return replies;
    }

    @XmlElement
    public Comment getParentComment() {
        return parentComment;
    }

    @XmlIDREF
    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    @XmlElement
    public float getRating() {
        return rating;
    }

    @XmlID
    public String getXId() {
        return id + "";
    }

    public String toStrShort() {
        return "Rating: " + rating + "\nReview: " + commentBody;
    }
}
