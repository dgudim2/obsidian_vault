package lt.vgtu.hw1.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlElementRefs;
import jakarta.xml.bind.annotation.XmlTransient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String commentTitle;
    private String commentBody;
    @ManyToOne()
    private Product whichProductCommented;
    @ManyToOne()
    private User commentOwner;
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapsId
    private List<Comment> replies;
    @ManyToOne()
    private Comment parentComment;
    private float rating;

    public Comment(String commentTitle, String commentBody, float rating) {
        this.commentTitle = commentTitle;
        this.commentBody = commentBody;
        this.rating = rating;
    }

    public Comment(String commentTitle, String commentBody, Product whichProductCommented, User commentOwner, float rating) {
        this.commentTitle = commentTitle;
        this.commentBody = commentBody;
        this.whichProductCommented = whichProductCommented;
        this.commentOwner = commentOwner;
        this.rating = rating;
    }

    public Comment(String commentTitle, String commentBody, User commentOwner, Comment parentComment, float rating) {
        this.commentTitle = commentTitle;
        this.commentBody = commentBody;
        this.commentOwner = commentOwner;
        this.parentComment = parentComment;
        this.rating = rating;
    }

    public String genText() {
        return "Rating: "  + rating + "\nReview: " + commentBody;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id == comment.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
