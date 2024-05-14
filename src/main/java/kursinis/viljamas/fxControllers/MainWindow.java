package kursinis.viljamas.fxControllers;

import javafx.event.ActionEvent;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.util.Callback;
import kursinis.viljamas.HelloApplication;
import kursinis.viljamas.fxControllers.tableParameters.CustomerTableParameters;
import kursinis.viljamas.fxControllers.tableParameters.ManagerTableParameters;
import kursinis.viljamas.fxControllers.tableParameters.OrderTableParameters;
import kursinis.viljamas.hibernate.ShopHibernate;
import kursinis.viljamas.model.*;
import jakarta.persistence.EntityManagerFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindow implements Initializable {
    private final ObservableList<ManagerTableParameters> data = FXCollections.observableArrayList();
    private final ObservableList<CustomerTableParameters> dataS = FXCollections.observableArrayList();
    private final ObservableList<OrderTableParameters> OrderData = FXCollections.observableArrayList();
    private final ObservableList<Warehouse> warehouseObservableList = FXCollections.observableArrayList();
    @FXML
    public ListView<Product> shopProducts;
    @FXML
    public Tab shopTab;
    @FXML
    public ListView<Product> myCartItems;
    @FXML
    public Tab productsTab;
    @FXML
    public ListView<Product> productAdminList;
    public TextField brimType;
    public TextField closureType;
    public TextField hoodStyle;
    public TextField PocketStyle;
    public TextField sleeveStyle;
    public TextField neckline;
    public TextField productTitleField;
    public TextArea productDescriptionField;
    public TextField productQuantityField;
    public TextField productBrandField;
    public TextField productMaterialField;
    public TextField productSizeField;
    public ToggleGroup productType;
    public RadioButton productHoodieRadio;
    public RadioButton productCapRadio;
    public RadioButton productTShirtRadio;
    public TextField size;
    public ComboBox<TshirtType> TshirtTypeField;
    @FXML
    public TableColumn<ManagerTableParameters, Integer> managerColId;
    public TableColumn<ManagerTableParameters, String> managerColLogin;
    public TableColumn<ManagerTableParameters, String> managerColName;
    public TableView<ManagerTableParameters> managerTable;
    public TableView<CustomerTableParameters> customerTable;
    public TableColumn<ManagerTableParameters, Void> managerDel;
    public TableColumn<CustomerTableParameters, Void> customerDummy;
    public TableColumn<OrderTableParameters, Void> ordersDummy;
    public Tab usersTab;
    public TableColumn<CustomerTableParameters, Integer> customerColId;
    public TableColumn<CustomerTableParameters, String> customerColLogin;
    public TableColumn<CustomerTableParameters, String> customerColName;
    public TableColumn<CustomerTableParameters, String> customerColSurname;
    public ListView<Warehouse> warehouseListView;
    public TextField addressField;
    public ComboBox<City> cityComboBox;
    public DatePicker dateCreatedPicker;
    public DatePicker dateModifiedPicker;
    public ListView<Cart> myOrderListView;
    public TableView<OrderTableParameters> ordersTableView;
    public Tab myOrdersTab;
    public Tab OrdersTab;
    public Tab warehousesTab;
    @FXML
    public TabPane tabPane;
    public ComboBox<Manager> managerComboBox;
    public TableColumn<OrderTableParameters, String> idColOrders;
    public TableColumn<OrderTableParameters, String> customerColOrders;
    public TableColumn<OrderTableParameters, String> managerColOrders;
    public TableColumn<OrderTableParameters, String> dateColOrders;
    public Button orderDeleteButton;
    public TableColumn<ManagerTableParameters, String> managerColSurname;
    public Button AddManagerButton;
    public ComboBox<Manager> filterManagerBox;
    public ComboBox<Customer> filterCustomerBox;
    public DatePicker filterDateFrom;
    public DatePicker filterDateTo;
    public ComboBox<Warehouse> warehouseComboBox;
    public ListView<Comment> chatView;
    public ListView<Comment> orderChatView;
    private EntityManagerFactory entityManagerFactory;
    //This class has methods for entity manipulation
    private ShopHibernate shopHibernate;
    //I need to know which user is selected
    private User user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadTabData();

        //Populating ComboBox with SeedType values
        TshirtTypeField.getItems().addAll(TshirtType.values());

        // Populate the cityComboBox
        cityComboBox.getItems().addAll(City.values());

        managerTable.setEditable(true);
        managerColId.setCellValueFactory(new PropertyValueFactory<>("id"));
        managerColLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        managerColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        managerColSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        managerColName.setCellFactory(TextFieldTableCell.forTableColumn());
        managerColName.setOnEditCommit(event -> {
            ManagerTableParameters selectedManager = event.getRowValue();

            // Check if the edited manager is the currently logged-in manager
            if (selectedManager.getId() == getCurrentManager().getId()) {
                selectedManager.setName(event.getNewValue());
                // Before updating, get the latest version from the database
                Manager manager = shopHibernate.getEntityById(Manager.class, selectedManager.getId());
                manager.setName(event.getNewValue());
                shopHibernate.update(manager);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Unauthorized Edit");
                alert.setHeaderText(null);
                alert.setContentText("You are not authorized to edit this manager's data.");
                alert.showAndWait();
                managerTable.refresh();
            }
        });
        managerColSurname.setCellFactory(TextFieldTableCell.forTableColumn());
        managerColSurname.setOnEditCommit(event -> {
            ManagerTableParameters selectedManager = event.getRowValue();

            // Check if the edited manager is the currently logged-in manager
            if (selectedManager.getId() == getCurrentManager().getId()) {
                selectedManager.setName(event.getNewValue());
                // Before updating, get the latest version from the database
                Manager manager = shopHibernate.getEntityById(Manager.class, selectedManager.getId());
                manager.setSurname(event.getNewValue());
                shopHibernate.update(manager);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Unauthorized Edit");
                alert.setHeaderText(null);
                alert.setContentText("You are not authorized to edit this manager's data.");
                alert.showAndWait();
                managerTable.refresh();
            }
        });
        managerColLogin.setCellFactory(TextFieldTableCell.forTableColumn());
        managerColLogin.setOnEditCommit(event -> {
            ManagerTableParameters selectedManager = event.getRowValue();

            // Check if the edited manager is the currently logged-in manager
            if (selectedManager.getId() == getCurrentManager().getId()) {
                selectedManager.setName(event.getNewValue());
                // Before updating, get the latest version from the database
                Manager manager = shopHibernate.getEntityById(Manager.class, selectedManager.getId());
                manager.setLogin(event.getNewValue());
                shopHibernate.update(manager);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Unauthorized Edit");
                alert.setHeaderText(null);
                alert.setContentText("You are not authorized to edit this manager's data.");
                alert.showAndWait();
                managerTable.refresh();
            }
        });

        //This portion of the code is responsible for generating a graphic element (button) in a cell
        Callback<TableColumn<ManagerTableParameters, Void>, TableCell<ManagerTableParameters, Void>> callback = param -> {
            final TableCell<ManagerTableParameters, Void> cell = new TableCell<>() {
                private final Button deleteButton = new Button("Delete");

                {
                    if (user instanceof Manager && !((Manager) user).isAdmin()) {
                        deleteButton.setVisible(false);
                    }
                    deleteButton.setOnAction(event -> {
                        ManagerTableParameters row = getTableView().getItems().get(getIndex());
                        shopHibernate.delete(Manager.class, row.getId());
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(deleteButton);
                    }
                }
            };
            return cell;
        };

        managerDel.setCellFactory(callback);

        customerTable.setEditable(true);
        customerColId.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerColLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        customerColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerColSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));

        ordersTableView.setEditable(true);
        idColOrders.setCellValueFactory(new PropertyValueFactory<>("id"));
        managerColOrders.setCellValueFactory(new PropertyValueFactory<>("manager"));
        customerColOrders.setCellValueFactory(new PropertyValueFactory<>("customer"));
        dateColOrders.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));
    }

    public Manager getCurrentManager() {
        User currentUser = this.user;

        if (currentUser instanceof Manager) {
            return (Manager) currentUser;
        } else {
            return null;
        }
    }


    public void createRecord() {
        // Check if the required fields are filled
        if (productTitleField.getText().isEmpty() || productDescriptionField.getText().isEmpty() || productQuantityField.getText().isEmpty()) {
            // If any required field is empty, display an alert
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all the required fields.");
            alert.showAndWait();
            return;
        }
        if (productHoodieRadio.isSelected()) {
            Hoodie hoodie = new Hoodie(
                    productTitleField.getText(),
                    productDescriptionField.getText(),
                    Integer.parseInt(productQuantityField.getText()),
                    productSizeField.getText(),
                    productBrandField.getText(),
                    productMaterialField.getText(),
                    hoodStyle.getText(),
                    PocketStyle.getText()
            );
            shopHibernate.create(hoodie);
        } else if (productTShirtRadio.isSelected()) {
            TShirt tshirt = new TShirt(
                    productTitleField.getText(),
                    productDescriptionField.getText(),
                    Integer.parseInt(productQuantityField.getText()),
                    productSizeField.getText(),
                    productBrandField.getText(),
                    productMaterialField.getText(),
                    sleeveStyle.getText(),
                    neckline.getText()
            );
            shopHibernate.create(tshirt);
        } else {
            Cap cap = new Cap(
                    productTitleField.getText(),
                    productDescriptionField.getText(),
                    Integer.parseInt(productQuantityField.getText()),
                    productSizeField.getText(),
                    productBrandField.getText(),
                    productMaterialField.getText(),
                    brimType.getText(),
                    closureType.getText()
            );
            shopHibernate.create(cap);
        }
        productAdminList.getItems().clear();
        productAdminList.getItems().addAll(shopHibernate.getAllRecords(Product.class));
    }

    public void disableFields() {
        if (productCapRadio.isSelected()) {
            hoodStyle.setDisable(true);
            PocketStyle.setDisable(true);
            brimType.setDisable(false);
            closureType.setDisable(false);
            sleeveStyle.setDisable(true);
            neckline.setDisable(true);
            TshirtTypeField.setDisable(true);
        } else if (productTShirtRadio.isSelected()) {
            hoodStyle.setDisable(true);
            PocketStyle.setDisable(true);
            brimType.setDisable(true);
            closureType.setDisable(true);
            sleeveStyle.setDisable(false);
            neckline.setDisable(false);
            TshirtTypeField.setDisable(false);
        } else {
            hoodStyle.setDisable(false);
            PocketStyle.setDisable(false);
            brimType.setDisable(true);
            closureType.setDisable(true);
            sleeveStyle.setDisable(true);
            neckline.setDisable(true);
            TshirtTypeField.setDisable(true);
        }
    }

    public void setData(EntityManagerFactory entityManagerFactory, User user) {
        this.entityManagerFactory = entityManagerFactory;
        this.shopHibernate = new ShopHibernate(entityManagerFactory);
        this.user = user;
        loadTabData();
        setCustomerView();
        managerComboBox.getItems().addAll(shopHibernate.getAllRecords(Manager.class));
        filterManagerBox.getItems().addAll(shopHibernate.getAllRecords(Manager.class));
        filterCustomerBox.getItems().addAll(shopHibernate.getAllRecords(Customer.class));
        warehouseComboBox.getItems().addAll(shopHibernate.getAllRecords(Warehouse.class));
        shopProducts.getItems().setAll(shopHibernate.loadAvailableProducts());
    }

    private void setCustomerView() {
        // Customer should not have any access or knowledge about tabs that are intended for Managers/Admins
        if (user instanceof Customer) {
            // You could simply disable tabs, but it is better to not render them
            tabPane.getTabs().remove(usersTab);
            tabPane.getTabs().remove(productsTab);
            tabPane.getTabs().remove(warehousesTab);
            tabPane.getTabs().remove(OrdersTab);
        } else if (user instanceof Manager && !((Manager) user).isAdmin()) {
            // Disable fields or functions that simple managers should not be able to access
            tabPane.getTabs().remove(shopTab);
            tabPane.getTabs().remove(myOrdersTab);
        }
    }

    private void fillCustomerTable() {
        List<Customer> customers = shopHibernate.getAllRecords(Customer.class);
        for (Customer customer : customers) {
            CustomerTableParameters customerTableParameters = new CustomerTableParameters(customer);
            customerTableParameters.setName(customer.getName());
            customerTableParameters.setSurname(customer.getSurname());
            customerTableParameters.setLogin(customer.getLogin());
            customerTableParameters.setPassword(customer.getPassword());
            customerTableParameters.setId(customer.getId());
            customerTableParameters.setBillingAddress(customer.getBillingAddress());
            customerTableParameters.setBirthDate(String.valueOf(customer.getBirthDate()));
            customerTableParameters.setCardNo(String.valueOf(customer.getCardNo()));
            customerTableParameters.setDeliveryAddress(customer.getDeliveryAddress());
            dataS.add(customerTableParameters);
        }
        customerTable.setItems(dataS);
    }

    public ObservableList<OrderTableParameters> getCarts(List<Cart> cartList) {
        ObservableList<OrderTableParameters> orderData = FXCollections.observableArrayList();

        for (Cart cart : cartList) {
            OrderTableParameters orderTableParameter = new OrderTableParameters();
            orderTableParameter.setId(cart.getId());
            orderTableParameter.setCustomer(String.valueOf(cart.getCustomer()));
            orderTableParameter.setManager(String.valueOf(cart.getManager()));
            orderTableParameter.setDateCreated(String.valueOf(cart.getDateCreated()));

            orderData.add(orderTableParameter);
        }

        return orderData;
    }

    public void moveOldOrdersToTop() {
        List<OrderTableParameters> orders = ordersTableView.getItems();
        ObservableList<OrderTableParameters> oldOrders = FXCollections.observableArrayList();

        LocalDate currentDate = LocalDate.now();
        for (OrderTableParameters order : orders) {
            LocalDate orderDate = LocalDate.parse(order.getDateCreated());
            if ((ChronoUnit.DAYS.between(orderDate, currentDate) >= 1) && order.getManager().equals("null")) {
                oldOrders.add(order);
            }
        }

        ordersTableView.getItems().removeAll(oldOrders);
        ordersTableView.getItems().addAll(0, oldOrders);

        // Set row factory
        ordersTableView.setRowFactory(tv -> new TableRow<>() {
            @Override
            public void updateItem(OrderTableParameters item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setStyle("");
                } else {
                    LocalDate currentDate = LocalDate.now();
                    LocalDate orderDate = LocalDate.parse(item.getDateCreated());
                    if ((ChronoUnit.DAYS.between(orderDate, currentDate) >= 1) && item.getManager().equals("null")) {
                        setStyle("-fx-background-color: #FFFF6E;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });
    }

    //<editor-fold desc="Logic for User Tab">
    private void fillManagerTable() {
        List<Manager> managers = shopHibernate.getAllRecords(Manager.class);
        for (Manager m : managers) {
            ManagerTableParameters managerTableParameters = new ManagerTableParameters(m);
            managerTableParameters.setName(m.getName());
            managerTableParameters.setSurname(m.getSurname());
            managerTableParameters.setLogin(m.getLogin());
            managerTableParameters.setPassword(m.getPassword());
            managerTableParameters.setId(m.getId());
            managerTableParameters.setAdmin(m.isAdmin());
            managerTableParameters.setWarehouse(m.getWarehouse());
            data.add(managerTableParameters);
        }
        managerTable.setItems(data);
    }

    public void loadProductData() {
        Product product = productAdminList.getSelectionModel().getSelectedItem();

        if (product instanceof Hoodie hoodie) {
            productTitleField.setText(hoodie.getTitle());
            productDescriptionField.setText(hoodie.getDescription());
            productQuantityField.setText(String.valueOf(hoodie.getQuantity()));
            productBrandField.setText(hoodie.getBrand());
            productSizeField.setText(hoodie.getSize());
            productMaterialField.setText(hoodie.getMaterial());
            hoodStyle.setText(hoodie.getHoodStyle());
            PocketStyle.setText(hoodie.getPocketStyle());
        } else if (product instanceof TShirt tShirt) {
            productTitleField.setText(tShirt.getTitle());
            productDescriptionField.setText(tShirt.getDescription());
            productQuantityField.setText(String.valueOf(tShirt.getQuantity()));
            productBrandField.setText(tShirt.getBrand());
            productSizeField.setText(tShirt.getSize());
            productMaterialField.setText(tShirt.getMaterial());
            neckline.setText(tShirt.getNeckline());
            sleeveStyle.setText(tShirt.getSleeveStyle());
        } else if (product instanceof Cap cap) {
            productTitleField.setText(cap.getTitle());
            productDescriptionField.setText(cap.getDescription());
            productQuantityField.setText(String.valueOf(cap.getQuantity()));
            productBrandField.setText(cap.getBrand());
            productSizeField.setText(cap.getSize());
            productMaterialField.setText(cap.getMaterial());
            brimType.setText(cap.getBrimType());
            closureType.setText(cap.getClosureType());
        }
    }

    public void updateRecord() {
        Product selectedProduct = productAdminList.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a product to edit.");
            alert.showAndWait();
            return;
        }

        // Check if any required field is empty
        if (productTitleField.getText().isEmpty() || productDescriptionField.getText().isEmpty() || productQuantityField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all the required fields.");
            alert.showAndWait();
            return;
        }

        Product product = shopHibernate.getEntityById(Product.class, selectedProduct.getId());

        if (product instanceof Hoodie hoodie) {
            hoodie.setTitle(productTitleField.getText());
            hoodie.setDescription(productDescriptionField.getText());
            hoodie.setQuantity(Integer.parseInt(productQuantityField.getText()));
            hoodie.setMaterial(productMaterialField.getText());
            hoodie.setBrand(productBrandField.getText());
            hoodie.setSize(productSizeField.getText());
            hoodie.setHoodStyle(hoodStyle.getText());
            hoodie.setPocketStyle(PocketStyle.getText());
            shopHibernate.update(hoodie);
        } else if (product instanceof Cap cap) {
            cap.setTitle(productTitleField.getText());
            cap.setDescription(productDescriptionField.getText());
            cap.setQuantity(Integer.parseInt(productQuantityField.getText()));
            cap.setMaterial(productMaterialField.getText());
            cap.setBrand(productBrandField.getText());
            cap.setSize(productSizeField.getText());
            cap.setBrimType(brimType.getText());
            cap.setClosureType(closureType.getText());
            shopHibernate.update(cap);
        } else if (product instanceof TShirt tShirt) {
            tShirt.setTitle(productTitleField.getText());
            tShirt.setDescription(productDescriptionField.getText());
            tShirt.setQuantity(Integer.parseInt(productQuantityField.getText()));
            tShirt.setMaterial(productMaterialField.getText());
            tShirt.setBrand(productBrandField.getText());
            tShirt.setSize(productSizeField.getText());
            tShirt.setSleeveStyle(sleeveStyle.getText());
            tShirt.setNeckline(neckline.getText());
            shopHibernate.update(tShirt);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid product type.");
            alert.showAndWait();
            return;
        }

        refreshProductList();
    }

    public void deleteRecord() {
        Product product = productAdminList.getSelectionModel().getSelectedItem();
        if (product == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a product to delete.");
            alert.showAndWait();
            return;
        }

        shopHibernate.delete(product.getClass(), product.getId());
        refreshProductList();
    }

    public void refreshProductList() {
        List<Product> productList = shopHibernate.getAllRecords(Product.class);
        productAdminList.getItems().setAll(productList);
    }

    public void removeFromCart() {
        Product product = myCartItems.getSelectionModel().getSelectedItem();
        if (product != null) {
            myCartItems.getItems().remove(product);
            shopProducts.getItems().add(product);
        } else {
            // Display an alert if no product is selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a product to remove from the cart.");
            alert.showAndWait();
        }
    }

    public void addToCart() {
        Product product = shopProducts.getSelectionModel().getSelectedItem();
        if (product != null) {
            shopProducts.getItems().remove(product);
            myCartItems.getItems().add(product);
        } else {
            // Display an alert if no product is selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a product to add to the cart.");
            alert.showAndWait();
        }
    }

    public void buyItems() {
        User user = this.user;
        if (user == null) {
            System.out.println("ERROR: User is null.");
            return;
        }
        if (!myCartItems.getItems().isEmpty()) {

            // Save the new cart to the database
            shopHibernate.createCart(myCartItems.getItems(), user);

            // Clear the cart after purchase
            myCartItems.getItems().clear();

            refreshMyOrdersData();

        } else {
            // Display an alert if the cart is empty
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Your cart is empty. Please add items before buying.");
            alert.showAndWait();
        }
    }

    public void filterOrders() {
        Customer customer = filterCustomerBox.getValue();
        Manager manager = filterManagerBox.getValue();
        LocalDate dateFrom = filterDateFrom.getValue();
        LocalDate dateTo = filterDateTo.getValue();

        List<Cart> filteredCarts = shopHibernate.filterOrders(customer, manager, dateFrom, dateTo);
        ObservableList<OrderTableParameters> listToDisplay = getCarts(filteredCarts);
        ordersTableView.setItems(listToDisplay);
    }

    public void refreshMyOrdersData() {
        Customer currentCustomer = null;
        if (user instanceof Customer) {
            currentCustomer = (Customer) user;
            List<Cart> customerCarts = currentCustomer.getMyCarts();
            myOrderListView.getItems().clear();
            if(customerCarts != null) {
                myOrderListView.getItems().addAll(customerCarts);
            }
        }
    }

    //</editor-fold>

    public void loadTabData() {

        //Disables button for adding managers for managers that are not admins
        if (user instanceof Manager && !((Manager) user).isAdmin()) {
            AddManagerButton.setVisible(false);
        }

        shopTab.setOnSelectionChanged(event -> {
            if (shopTab.isSelected()) {
                shopProducts.getItems().setAll(shopHibernate.loadAvailableProducts());
            } else if (usersTab.isSelected()) {
                fillManagerTable();
                fillCustomerTable();
            }
        });
        myOrdersTab.setOnSelectionChanged(event -> {
            if (myOrdersTab.isSelected()) {
            refreshMyOrdersData();
            }
        });
        productsTab.setOnSelectionChanged(event -> {
            if (productsTab.isSelected()) {
                productAdminList.getItems().clear();
                productAdminList.getItems().addAll(shopHibernate.getAllRecords(Product.class));
            }
        });
        OrdersTab.setOnSelectionChanged(event -> {
            if (OrdersTab.isSelected()) {
                ordersTableView.getItems().clear();
                List<Cart> CartList = shopHibernate.getAllRecords(Cart.class);
                ObservableList<OrderTableParameters> displayCart = getCarts(CartList);
                ordersTableView.setItems(displayCart);

                moveOldOrdersToTop();
            }
        });
        warehousesTab.setOnSelectionChanged(event -> {
            if (warehousesTab.isSelected()) {
                warehouseListView.getItems().clear();
                warehouseListView.getItems().addAll(shopHibernate.getAllRecords(Warehouse.class));
            }
        });
        usersTab.setOnSelectionChanged(event -> {
            if (usersTab.isSelected()) {
                clearTables();
                fillManagerTable();
                fillCustomerTable();
            }
        });
    }

    private void clearTables() {
        managerTable.getItems().clear();
        customerTable.getItems().clear();
    }

    public void loadProductReviewForm() throws IOException {
        //Get resources: fxml, controller, grapics, styles...
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("product-review.fxml"));
        //Load resources, without this step I cannot access controllers
        Parent parent = fxmlLoader.load();

        ProductReview productReview = fxmlLoader.getController();
        //Forms do not know about each other, therefore I must pass info between them
        productReview.setData(entityManagerFactory, user);
        //Create a completely new window
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        FxUtils.setStageParameters(stage, scene, true);
    }

    @FXML
    public void addWarehouse() {
        if (addressField.getText().isEmpty() || cityComboBox.getValue() == null || dateCreatedPicker.getValue() == null || dateModifiedPicker.getValue() == null) {
            // Handle empty fields
            System.out.println("Please fill in all fields.");
            return;
        }

        Warehouse warehouse = new Warehouse(
                addressField.getText(),
                cityComboBox.getValue(),
                dateCreatedPicker.getValue(),
                dateModifiedPicker.getValue()
        );

        shopHibernate.create(warehouse);
        warehouseObservableList.add(warehouse);
        warehouseListView.getItems().clear();
        warehouseListView.getItems().addAll(shopHibernate.getAllRecords(Warehouse.class));

        clearFields();
    }

    @FXML
    public void deleteWarehouse() {
        Warehouse warehouse = warehouseListView.getSelectionModel().getSelectedItem();
        if (warehouse == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a product to delete.");
            alert.showAndWait();
            return;
        }

        shopHibernate.delete(warehouse.getClass(), warehouse.getId());
        refreshWarehouseList();
    }

    public void updateWarehouse() {
        Warehouse selectedWarehouse = warehouseListView.getSelectionModel().getSelectedItem();
        if (selectedWarehouse == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a warehouse to edit.");
            alert.showAndWait();
            return;
        }

        // Check if any required field is empty
        if (addressField.getText().isBlank() || cityComboBox.getValue() == null || dateCreatedPicker.getValue() == null || dateModifiedPicker.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all the required fields.");
            alert.showAndWait();
            return;
        }

        Warehouse warehouse = shopHibernate.getEntityById(Warehouse.class, selectedWarehouse.getId());

        warehouse.setAddress(addressField.getText());
        warehouse.setDateCreated(dateCreatedPicker.getValue());
        warehouse.setDateModified(dateModifiedPicker.getValue());
        warehouse.setCity(cityComboBox.getValue());

        // Update the warehouse in the database using shopHibernate
        shopHibernate.update(warehouse);

        // Refresh the list view
        refreshWarehouseList();

        // Clear input fields
        clearFields();
    }

    public void clearFields() {
        // Clear input fields after adding or updating a warehouse
        addressField.clear();
        cityComboBox.getSelectionModel().clearSelection();
        dateCreatedPicker.setValue(null);
        dateModifiedPicker.setValue(null);
    }

    public void refreshWarehouseList() {
        List<Warehouse> warehouseList = shopHibernate.getAllRecords(Warehouse.class);
        warehouseListView.getItems().setAll(warehouseList);
    }

    public void loadWarehouseData() {
        // Get the selected warehouse from the ListView
        Warehouse selectedWarehouse = warehouseListView.getSelectionModel().getSelectedItem();

        if (selectedWarehouse != null) {
            // Retrieve the warehouse details from the database
            Warehouse warehouse = shopHibernate.getEntityById(Warehouse.class, selectedWarehouse.getId());

            // Populate UI elements with warehouse data
            addressField.setText(warehouse.getAddress());
            cityComboBox.setValue(warehouse.getCity());
            dateCreatedPicker.setValue(warehouse.getDateCreated());
            dateModifiedPicker.setValue(warehouse.getDateModified());
        }
    }

    @FXML
    public void deleteMyOrder() {
        Cart selectedCart = myOrderListView.getSelectionModel().getSelectedItem();
        if (selectedCart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a product to delete.");
            alert.showAndWait();
            return;
        }

        shopHibernate.deleteCart(selectedCart.getId());
        refreshMyOrdersData();
    }

    public void deleteOrder() {
        try {
            // Get the selected cart and manager from the UI components
            OrderTableParameters selectedCart = ordersTableView.getSelectionModel().getSelectedItem();
            Cart selectedCartEntity = null;
            List<Cart> cartList = shopHibernate.getAllRecords(Cart.class);
            for (Cart cart : cartList) {
                if (selectedCart.getId() == cart.getId()) {
                    selectedCartEntity = cart;
                    break;
                }
            }
            if (selectedCartEntity != null) {
                // Update the cart in the database using the GenericHibernate class
                shopHibernate.delete(selectedCartEntity.getClass(), selectedCartEntity.getId());

                ordersTableView.getItems().clear();
                List<Cart> updatedCartList = shopHibernate.getAllRecords(Cart.class);
                ObservableList<OrderTableParameters> displayCart = getCarts(updatedCartList);
                ordersTableView.setItems(displayCart);
            } else {
                System.out.println("Selected cart not found in the database.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void addManager() {
        try {
            // Get the selected cart and manager from the UI components
            OrderTableParameters selectedCart = ordersTableView.getSelectionModel().getSelectedItem();
            Manager selectedManager = managerComboBox.getValue();
            Cart selectedCartEntity = null;

            if (selectedCart != null && selectedManager != null) {
                List<Cart> cartList = shopHibernate.getAllRecords(Cart.class);
                for (Cart cart : cartList) {
                    if (selectedCart.getId() == cart.getId()) {
                        selectedCartEntity = cart;
                        break;
                    }
                }

                if (selectedCartEntity != null) {
                    // Update the manager of the selected cart
                    selectedCartEntity.setManager(selectedManager);
                    // Update the cart in the database using the GenericHibernate class
                    shopHibernate.update(selectedCartEntity);

                    ordersTableView.getItems().clear();
                    List<Cart> updatedCartList = shopHibernate.getAllRecords(Cart.class);
                    ObservableList<OrderTableParameters> displayCart = getCarts(updatedCartList);
                    ordersTableView.setItems(displayCart);

                    managerComboBox.getSelectionModel().clearSelection();
                } else {
                    System.out.println("Selected cart not found in the database.");
                }
            } else {
                System.out.println("Please select both a cart and a manager.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void dropdownProduct() {
            // Get the selected cart and manager from the UI components
            Product selectedProduct = productAdminList.getSelectionModel().getSelectedItem();
            Warehouse warehouse = warehouseComboBox.getValue();


                if (selectedProduct != null) {
                    // Update the manager of the selected cart
                    selectedProduct.setWarehouse(warehouse);
                    // Update the cart in the database using the GenericHibernate class
                    shopHibernate.update(selectedProduct);

                    productAdminList.getItems().clear();
                    productAdminList.getItems().addAll(shopHibernate.getAllRecords(Product.class));

                    warehouseComboBox.getSelectionModel().clearSelection();
                } else {
                    System.out.println("Selected product not found in the database.");
                }
    }

    public void openRegistration() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("registration.fxml"));
        Parent parent = fxmlLoader.load();
        RegistrationController registrationController = fxmlLoader.getController();
        registrationController.setDataManager(entityManagerFactory, getCurrentManager()); // Pass necessary data to the controller

        Stage stage = new Stage();
        stage.setTitle("Registration");
        stage.setScene(new Scene(parent));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void createChat() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("comment-form.fxml"));
        Parent parent = fxmlLoader.load();
        CommentForm commentForm = fxmlLoader.getController();
        Cart selectedCart = myOrderListView.getSelectionModel().getSelectedItem();
        commentForm.setData(entityManagerFactory, user, selectedCart);
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        FxUtils.setStageParameters(stage, scene, true);
    }

    public void reply() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("comment-form.fxml"));
        Parent parent = fxmlLoader.load();
        CommentForm commentForm = fxmlLoader.getController();
        Cart selectedCart = myOrderListView.getSelectionModel().getSelectedItem();
        commentForm.setData(entityManagerFactory, user, selectedCart);
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        FxUtils.setStageParameters(stage, scene, true);
    }

    public void loadReviews() {
        Cart selectedCart = myOrderListView.getSelectionModel().getSelectedItem();
        Cart cart = shopHibernate.getEntityById(Cart.class, selectedCart.getId());
        chatView.getItems().clear();
        chatView.getItems().addAll(cart.getChat());
    }

    public void createChatOrder() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("comment-form.fxml"));
        Parent parent = fxmlLoader.load();
        CommentForm commentForm = fxmlLoader.getController();
        OrderTableParameters selectedCart = ordersTableView.getSelectionModel().getSelectedItem();
        Cart selectedCartEntity = shopHibernate.getEntityById(Cart.class, selectedCart.getId());
        commentForm.setData(entityManagerFactory, user, selectedCartEntity);
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        FxUtils.setStageParameters(stage, scene, true);
        loadReviewsOrder();
    }

    public void replyOrder() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("comment-form.fxml"));
        Parent parent = fxmlLoader.load();
        CommentForm commentForm = fxmlLoader.getController();
        OrderTableParameters selectedCart = ordersTableView.getSelectionModel().getSelectedItem();
        Cart selectedCartEntity = shopHibernate.getEntityById(Cart.class, selectedCart.getId());
        commentForm.setData(entityManagerFactory, user, selectedCartEntity);
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        FxUtils.setStageParameters(stage, scene, true);
        loadReviewsOrder();
    }


    public void loadReviewsOrder() {
        OrderTableParameters selectedCart = ordersTableView.getSelectionModel().getSelectedItem();
        Cart selectedCartEntity = shopHibernate.getEntityById(Cart.class, selectedCart.getId());
        assert selectedCartEntity != null;
        Cart cart = shopHibernate.getEntityById(Cart.class, selectedCartEntity.getId());
        orderChatView.getItems().clear();
        orderChatView.getItems().addAll(cart.getChat());
    }
}

