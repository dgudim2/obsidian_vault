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
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> replies;
    @ManyToOne()
    private Comment parentComment;
    @Setter
    private float rating;

    /**
     * Instantiates a new Comment.
     *
     * @param commentTitle the comment title
     * @param commentBody  the comment body
     * @param rating       the rating
     */
    public Comment(String commentTitle, String commentBody, float rating) {
        this.commentTitle = commentTitle;
        this.commentBody = commentBody;
        this.rating = rating;
    }

    /**
     * Instantiates a new Comment.
     *
     * @param commentTitle          the comment title
     * @param commentBody           the comment body
     * @param whichProductCommented the which product commented
     * @param commentOwner          the comment owner
     * @param rating                the rating
     */
    public Comment(String commentTitle, String commentBody, Product whichProductCommented, User commentOwner, float rating) {
        this.commentTitle = commentTitle;
        this.commentBody = commentBody;
        this.whichProductCommented = whichProductCommented;
        this.commentOwner = commentOwner;
        this.rating = rating;
    }

    /**
     * Instantiates a new Comment.
     *
     * @param commentTitle  the comment title
     * @param commentBody   the comment body
     * @param commentOwner  the comment owner
     * @param parentComment the parent comment
     * @param rating        the rating
     */
    public Comment(String commentTitle, String commentBody, User commentOwner, Comment parentComment, float rating) {
        this.commentTitle = commentTitle;
        this.commentBody = commentBody;
        this.commentOwner = commentOwner;
        this.parentComment = parentComment;
        this.rating = rating;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    @XmlElement
    public int getId() {
        return id;
    }

    /**
     * Gets comment title.
     *
     * @return the comment title
     */
    @XmlElement
    public String getCommentTitle() {
        return commentTitle;
    }

    /**
     * Gets comment body.
     *
     * @return the comment body
     */
    @XmlElement
    public String getCommentBody() {
        return commentBody;
    }

    /**
     * Gets which product commented.
     *
     * @return the which product commented
     */
    @XmlElement
    public Product getWhichProductCommented() {
        return whichProductCommented;
    }

    /**
     * Sets which product commented.
     *
     * @param whichProductCommented the which product commented
     */
    @XmlIDREF
    public void setWhichProductCommented(Product whichProductCommented) {
        this.whichProductCommented = whichProductCommented;
    }

    /**
     * Gets comment owner.
     *
     * @return the comment owner
     */
    @XmlElement
    public User getCommentOwner() {
        return commentOwner;
    }

    /**
     * Sets comment owner.
     *
     * @param commentOwner the comment owner
     */
    @XmlIDREF
    public void setCommentOwner(User commentOwner) {
        this.commentOwner = commentOwner;
    }

    /**
     * Gets replies.
     *
     * @return the replies
     */
    @XmlElement
    public List<Comment> getReplies() {
        return replies;
    }

    /**
     * Sets replies.
     *
     * @param replies the replies
     */
    @XmlIDREF
    public void setReplies(List<Comment> replies) {
        this.replies = replies;
    }

    /**
     * Gets parent comment.
     *
     * @return the parent comment
     */
    @XmlElement
    public Comment getParentComment() {
        return parentComment;
    }

    /**
     * Sets parent comment.
     *
     * @param parentComment the parent comment
     */
    @XmlIDREF
    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    /**
     * Gets rating.
     *
     * @return the rating
     */
    @XmlElement
    public float getRating() {
        return rating;
    }

    /**
     * Gets x id.
     *
     * @return the x id
     */
    @XmlID
    public String getXId() {
        return id + "";
    }

    /**
     * Gen text string.
     *
     * @return the string
     */
    public String genText() {
        return "Rating: " + rating + "\nReview: " + commentBody;
    }
}
