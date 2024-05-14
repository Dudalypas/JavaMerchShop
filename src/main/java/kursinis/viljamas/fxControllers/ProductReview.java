package kursinis.viljamas.fxControllers;

import kursinis.viljamas.HelloApplication;
import kursinis.viljamas.hibernate.ShopHibernate;
import kursinis.viljamas.model.*;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

import java.io.IOException;

public class ProductReview {
    @FXML
    public ListView<Product> productListField;
    @FXML
    public TreeView<Comment> commentTreeField;
    private EntityManagerFactory entityManagerFactory;
    private ShopHibernate shopHibernate;
    private User user;

    public void setData(EntityManagerFactory entityManagerFactory, User user) {
        this.entityManagerFactory = entityManagerFactory;
        this.shopHibernate = new ShopHibernate(entityManagerFactory);
        this.user = user;
        fillLists();
    }

    private void fillLists() {
        productListField.getItems().clear();
        productListField.getItems().addAll(shopHibernate.getAllRecords(Product.class));
    }

    public void previewProduct() {
        Product selectedProduct = productListField.getSelectionModel().getSelectedItem();

        if (selectedProduct != null) {
            if (selectedProduct instanceof Hoodie hoodie) {
                FxUtils.generateAlert(Alert.AlertType.INFORMATION, hoodie.getTitle(), hoodie.toString());
            } else if (selectedProduct instanceof TShirt tShirt) {
                FxUtils.generateAlert(Alert.AlertType.INFORMATION, tShirt.getTitle(), tShirt.toString());
            } else if (selectedProduct instanceof Cap cap) {
                FxUtils.generateAlert(Alert.AlertType.INFORMATION, cap.getTitle(), cap.toString());
            }
            else {
                FxUtils.generateAlert(Alert.AlertType.INFORMATION, selectedProduct.getTitle(), selectedProduct.toString());
            }
        } else {
            System.out.println("Please select a product.");
        }
    }


    public void addReview() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("comment-form.fxml"));
        Parent parent = fxmlLoader.load();
        CommentForm commentForm = fxmlLoader.getController();
        commentForm.setData(entityManagerFactory, user, productListField.getSelectionModel().getSelectedItem());
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        FxUtils.setStageParameters(stage, scene, true);
    }

    public void loadReviews() {
        //Get info about product from database
        Product product = shopHibernate.getEntityById(Product.class, productListField.getSelectionModel().getSelectedItem().getId());
        commentTreeField.setRoot(new TreeItem<>());
        commentTreeField.setShowRoot(false);
        commentTreeField.getRoot().setExpanded(true);
        product.getComments().forEach(comment -> addTreeItem(comment, commentTreeField.getRoot()));
    }

    private void addTreeItem(Comment comment, TreeItem<Comment> parentComment) {
        TreeItem<Comment> treeItem = new TreeItem<>(comment);
        parentComment.getChildren().add(treeItem);
        comment.getReplies().forEach(sub -> addTreeItem(sub, treeItem));
    }

    public void updateComment() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("comment-form.fxml"));
        Parent parent = fxmlLoader.load();
        CommentForm commentForm = fxmlLoader.getController();
        commentForm.setData(entityManagerFactory, commentTreeField.getSelectionModel().getSelectedItem().getValue());
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        FxUtils.setStageParameters(stage, scene, true);
    }

    public void reply() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("comment-form.fxml"));
        Parent parent = fxmlLoader.load();
        CommentForm commentForm = fxmlLoader.getController();
        commentForm.setData(entityManagerFactory, commentTreeField.getSelectionModel().getSelectedItem().getValue(), user);
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        FxUtils.setStageParameters(stage, scene, true);
    }

    public void delete() {
        shopHibernate.deleteComment(commentTreeField.getSelectionModel().getSelectedItem().getValue().getId());
    }
}
