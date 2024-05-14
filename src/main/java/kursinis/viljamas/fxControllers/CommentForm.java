package kursinis.viljamas.fxControllers;

import javafx.stage.Stage;
import kursinis.viljamas.hibernate.GenericHibernate;
import kursinis.viljamas.model.Cart;
import kursinis.viljamas.model.Comment;
import kursinis.viljamas.model.Product;
import kursinis.viljamas.model.User;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.Setter;

public class CommentForm {
    @FXML
    public TextField commentTitleField;
    @FXML
    public TextArea commentBodyField;
    @FXML
    public Slider ratingField;

    private EntityManagerFactory entityManagerFactory;
    private User currentUser;
    private Product product;
    private Cart cart;
    private GenericHibernate genericHibernate;
    private Comment comment;
    private boolean isUpdate;

    public void setData(EntityManagerFactory entityManagerFactory, User user, Product product) {
        this.entityManagerFactory = entityManagerFactory;
        this.currentUser = user;
        this.product = product;
        this.genericHibernate = new GenericHibernate(entityManagerFactory);
    }

    public void setData(EntityManagerFactory entityManagerFactory, User user, Cart cart) {
        this.entityManagerFactory = entityManagerFactory;
        this.currentUser = user;
        this.cart = cart;
        this.genericHibernate = new GenericHibernate(entityManagerFactory);

        commentTitleField.setVisible(false);
        ratingField.setVisible(false);
    }

    public void setData(EntityManagerFactory entityManagerFactory, Comment comment, User user) {
        this.entityManagerFactory = entityManagerFactory;
        this.comment = comment;
        this.currentUser = user;
        this.genericHibernate = new GenericHibernate(entityManagerFactory);
        commentTitleField.setVisible(false);
        ratingField.setVisible(false);
    }

    public void setData(EntityManagerFactory entityManagerFactory, Comment comment) {
        this.entityManagerFactory = entityManagerFactory;
        this.genericHibernate = new GenericHibernate(entityManagerFactory);
        this.comment = comment;
        this.isUpdate = true;

        Comment commentToUpdate = genericHibernate.getEntityById(Comment.class, comment.getId());
        ratingField.setVisible(false);
        commentTitleField.setText(commentToUpdate.getCommentTitle());
        commentBodyField.setText(commentToUpdate.getCommentBody());
    }
    public void saveComment() {
        if (!isUpdate && comment == null && product != null) {
            Product currentProduct = genericHibernate.getEntityById(Product.class, product.getId());
            Comment reviewForProduct = new Comment(commentTitleField.getText(), commentBodyField.getText(), ratingField.getValue(), currentUser, currentProduct);
            currentProduct.getComments().add(reviewForProduct);
            genericHibernate.update(currentProduct);
        }
        else if (!isUpdate && comment == null && cart != null) {
            Cart currentCart = genericHibernate.getEntityById(Cart.class, cart.getId());
            Comment reviewForProduct = new Comment(commentTitleField.getText(), commentBodyField.getText(), currentUser, currentCart);
            currentCart.getChat().add(reviewForProduct);
            genericHibernate.update(currentCart);
        }
        else if (!isUpdate) {
            Comment parentComment = genericHibernate.getEntityById(Comment.class, comment.getId());
            Comment replyToComment = new Comment(commentTitleField.getText(), commentBodyField.getText(), currentUser, parentComment);
            parentComment.getReplies().add(replyToComment);
            genericHibernate.update(parentComment);
        } else {
            Comment commentToUpdate = genericHibernate.getEntityById(Comment.class, comment.getId());
            commentToUpdate.setCommentBody(commentBodyField.getText());
            commentToUpdate.setCommentTitle(commentTitleField.getText());
            genericHibernate.update(commentToUpdate);
        }
    }
}
