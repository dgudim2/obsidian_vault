package org.kloud.module.gui.components;

import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kloud.model.enums.UserCapability;
import org.kloud.common.fields.ForeignKeyListField;
import org.kloud.daos.BasicDAO;
import org.kloud.model.BaseModel;
import org.kloud.model.Comment;
import org.kloud.utils.Conf;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class CommentsWrapper<T extends BaseModel> {

    private final Label commentsLabel;
    private final TreeView<String> commentsTree;
    private final Separator commentsSeparator;
    private final Button addCommentButton;
    private final BasicDAO<T> dao;
    private final Function<T, ForeignKeyListField<Comment>> commentGetter;
    private final Supplier<T> selectedObjectGetter;

    public CommentsWrapper(@NotNull Label commentsLabel,
                           @NotNull TreeView<String> commentsTree,
                           @NotNull Separator commentsSeparator,
                           @NotNull Button addCommentButton,
                           @NotNull BasicDAO<T> dao,
                           @NotNull Function<T, @NotNull ForeignKeyListField<Comment>> commentGetter,
                           @NotNull Supplier<@NotNull T> selectedObjectGetter) {

        this.commentsLabel = commentsLabel;
        this.commentsTree = commentsTree;
        this.commentsSeparator = commentsSeparator;
        this.addCommentButton = addCommentButton;
        this.dao = dao;
        this.commentGetter = commentGetter;
        this.selectedObjectGetter = selectedObjectGetter;

        addCommentButton.setOnAction(event ->
                leaveOrEditComment(
                        commentsTree.getRoot(), null,
                        null, null,
                        selectedObjectGetter.get()));

        loadCommentsForObject(null);
    }

    @NotNull
    private TreeItem<String> addCommentNode(@NotNull TreeItem<String> parentNode,
                                            @Nullable Comment parentComment,
                                            @NotNull Comment thisComment,
                                            @NotNull T TObject) {

        var thisNode = new TreeItem<>("", new Label(thisComment.toString()));
        var replyAction = new MenuItem("Reply");
        var deleteAction = new MenuItem("Delete");
        var editAction = new MenuItem("Edit");
        ContextMenu contextMenu;
        if (Conf.getLoginController().canActOnOtherUser(thisComment.author.getLinkedValue(), UserCapability.WRITE_OTHER_COMMENTS) ||
                Conf.getLoginController().canActOnSelf(thisComment.author.getLinkedValue(), UserCapability.RW_SELF_COMMENTS)) {
            contextMenu = new ContextMenu(replyAction, editAction, deleteAction);
        } else {
            contextMenu = new ContextMenu(replyAction);
        }
        replyAction.setOnAction(event -> leaveOrEditComment(thisNode, thisComment, null, null, TObject));
        deleteAction.setOnAction(event -> deleteComment(parentNode, parentComment, thisNode, thisComment, TObject));
        editAction.setOnAction(event -> leaveOrEditComment(thisNode, thisComment, thisNode, thisComment, TObject));

        // TODO: This is semi-ideal, right click will only work on the label
        // maybe set preferred width for the label??
        thisNode.getGraphic().setOnContextMenuRequested(e -> contextMenu.show(thisNode.getGraphic(), e.getScreenX(), e.getScreenY()));
        thisNode.getGraphic().setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                Alert viewCommentDialog = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
                viewCommentDialog.setTitle("Comment on " + TObject);
                viewCommentDialog.setHeaderText("View comment");
                BootstrapPane pane = new BootstrapPane();
                pane.setPrefWidth(350);
                pane.setPrefHeight(150);
                pane.addRow(thisComment.loadReadonlyGui());

                viewCommentDialog.getDialogPane().setContent(pane);
                viewCommentDialog.showAndWait();
            }
        });
        parentNode.getChildren().add(thisNode);
        return thisNode;
    }

    private void loadCommentLayer(@NotNull TreeItem<String> parentNode,
                                  @Nullable Comment parentComment,
                                  @NotNull List<Comment> childComments,
                                  @NotNull T TObject) {
        for (var childComment : childComments) {

            if (!Conf.getLoginController().canActOnOtherUser(childComment.author.getLinkedValue(), UserCapability.READ_OTHER_COMMENTS) &&
                    !Conf.getLoginController().canActOnSelf(childComment.author.getLinkedValue(), UserCapability.RW_SELF_COMMENTS)) {
                // Skip comments our user doesn't have read access to
                continue;
            }
            var childNode = addCommentNode(parentNode, parentComment, childComment, TObject);
            // TODO: This is not efficient, we should load maybe up to 2 layers deep
            // but for now I am too lazy to ensure that there are no duplicates on layers
            // just don't leave 30000 comments
            loadCommentLayer(childNode, childComment, childComment.children.getLinkedValues(), TObject);
        }
    }

    public void loadCommentsForObject(@Nullable T TObject) {
        addCommentButton.setVisible(TObject != null);
        commentsLabel.setVisible(TObject != null);
        commentsTree.setVisible(TObject != null);
        commentsSeparator.setVisible(TObject != null);

        if (TObject != null) {
            TreeItem<String> rootItem = new TreeItem<>();
            rootItem.setExpanded(true);
            commentsTree.setRoot(rootItem);
            commentsTree.setShowRoot(false);
            loadCommentLayer(rootItem, null, commentGetter.apply(TObject).getLinkedValues(), TObject);
        }
    }

    public void leaveOrEditComment(@NotNull TreeItem<String> parentNode, @Nullable Comment parentComment,
                                   @Nullable TreeItem<String> thisNode, @Nullable Comment thisComment,
                                   @NotNull T TObject) {
        Alert addCommentDialog = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.CANCEL, ButtonType.YES);
        addCommentDialog.setTitle("Comment on " + TObject);
        var addButton = (Button) addCommentDialog.getDialogPane().lookupButton(ButtonType.YES);

        boolean isEditing;
        if (thisComment == null) {
            isEditing = false;
            addButton.setText("Add");
            // We are leaving a new comment
            addCommentDialog.setHeaderText(parentComment == null ? "Add comment" : "Reply to \"" + parentComment + "\"");
            thisComment = new Comment();
            thisComment.author.set(Conf.getLoginController().loggedInUser.get().id);
        } else {
            isEditing = true;
            addButton.setText("Save");
            // We are editing a comment
            addCommentDialog.setHeaderText(parentComment == null ? "Edit comment" : "Edit reply to \"" + parentComment + "\"");
        }

        BootstrapPane pane = new BootstrapPane();
        pane.setPrefWidth(350);
        pane.setPrefHeight(150);

        @NotNull Comment finalThisComment = thisComment;
        pane.addRow(thisComment.loadEditableGui(addButton, Conf.getStorage().getCommentStorage(), () -> {
            if (parentComment == null) {
                // Commenting on the object itself
                if (!isEditing) {
                    // We are adding a comment
                    commentGetter.apply(selectedObjectGetter.get()).addLinkedValue(finalThisComment);
                }
                // NOTE: This is wrong, we can save an invalid object by leaving a comment on it (validate in addOrUpdateObject or here maybe)
                dao.addOrUpdateObject(selectedObjectGetter.get());
            } else {
                if (!isEditing) {
                    parentComment.children.addLinkedValue(finalThisComment);
                }
                // NOTE: We are saving the child first, then updating the parent,
                // can't do it in one go because getByIds won't return our newly created child, need to save it first
                // Maybe add a flag 'save' to addOrUpdateObject, but I don't know how to handle it gracefully in DBDAO because we do SQL UPDATE
                // when updating the object. Maybe a separate queue??
                // TLDR: Skip this for now
                Conf.getStorage().getCommentStorage().addOrUpdateObject(parentComment);
            }
            if (!isEditing) {
                addCommentNode(parentNode, parentComment, finalThisComment, TObject);
            } else {
                // Update text in UI
                ((Label) Objects.requireNonNull(thisNode).getGraphic()).setText(finalThisComment.toString());
            }
            addCommentDialog.close();
        }, true));

        addCommentDialog.getDialogPane().setContent(pane);
        addCommentDialog.showAndWait();
    }

    private void deleteComment(@NotNull TreeItem<String> parentNode, @Nullable Comment parentComment,
                               @NotNull TreeItem<String> thisNode, @NotNull Comment thisComment, @NotNull T TObject) {
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.CANCEL, ButtonType.YES);
        confirmDialog.setTitle("Delete a comment");
        confirmDialog.setHeaderText("Delete " + thisComment + "?");
        var result = confirmDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            parentNode.getChildren().remove(thisNode);
            if (parentComment != null) {
                parentComment.children.removeLinkedValue(thisComment);
                Conf.getStorage().getCommentStorage().addOrUpdateObject(parentComment);
            } else {
                commentGetter.apply(TObject).removeLinkedValue(thisComment);
                dao.addOrUpdateObject(TObject);
            }
            deleteCommentChildrenRecursive(thisComment);
            Conf.getStorage().getCommentStorage().removeById(thisComment.id);
        }
    }

    private void deleteCommentChildrenRecursive(@NotNull Comment thisComment) {
        for (var child : thisComment.children.getLinkedValues()) {
            deleteCommentChildrenRecursive(child);
        }
        // NOTE: This is inefficient, collect all ids and delete in one batch maybe
        Conf.getStorage().getCommentStorage().removeByIds(thisComment.children.get().backingList);
    }
}
