/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bluejnr.customer;

import bluejnr.beans.CustomerTO;
import bluejnr.beans.daos.DaoFactory;
import bluejnr.customer.model.Customer;
import bluejnr.customer.view.CustomerEditDialogController;
import bluejnr.customer.view.CustomerOverviewController;
import bluejnr.customer.view.OperationDialogController;
import bluejnr.service.CustomerService;
import bluejnr.util.Util;
import java.io.IOException;
import java.util.List;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author kcuadror
 */
public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    private ObservableList<Customer> customerData = FXCollections.observableArrayList();
    
    DaoFactory factory = DaoFactory.getInstance();
    CustomerService service = factory.getCustomerDao(Util.PST);

    public MainApp() {
        // Add some sample data
        List<CustomerTO> lista= service.getCustomers();
        lista.stream()
            .map(customerTO -> parse(customerTO))
            .forEach(customer -> customerData.add(customer));
        
    }
    
    private Customer parse(CustomerTO customerTO){
        return new Customer(customerTO.getIdCustomer(), 
                customerTO.getNames(), 
                customerTO.getSurnames(), 
                customerTO.getEmail(), 
                customerTO.getTelephone());
    }

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("MaintenanceCustomersApp");

        initRootLayout();

        showCustomerOverview();
    }

    private void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showCustomerOverview() {
        try {
            // Load customer overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/CustomerOverview.fxml"));
            AnchorPane customerOverview = (AnchorPane) loader.load();

            // Set customer overview into the center of root layout.
            rootLayout.setCenter(customerOverview);

            // Give the controller access to the main app.
            CustomerOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens a dialog to edit details for the specified customer. If the user
     * clicks OK, the changes are saved into the provided customer object and true
     * is returned.
     *
     * @param customer the customer object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showCustomerEditDialog(Customer customer) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/CustomerEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            if (customer.getNames() == null) {
                dialogStage.setTitle("Agregar Cliente");
            } else {
                dialogStage.setTitle("Editar Cliente");
            }
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the customer into the controller.
            CustomerEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCustomer(customer);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean showDialogOperation() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/OperationDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the customer into the controller.
            OperationDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns the main stage.
     *
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Returns the data as an observable list of Customers.
     *
     * @return
     */
    public ObservableList<Customer> getCustomerData() {
        return customerData;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
