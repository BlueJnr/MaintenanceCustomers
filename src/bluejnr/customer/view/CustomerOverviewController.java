/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bluejnr.customer.view;

import bluejnr.beans.CustomerTO;
import bluejnr.beans.daos.DaoFactory;
import bluejnr.customer.MainApp;
import bluejnr.customer.model.Customer;
import bluejnr.service.CustomerService;
import bluejnr.util.Util;
import javafx.fxml.FXML;
import org.controlsfx.dialog.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author kcuadror
 */
public class CustomerOverviewController {

    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, String> namesColumn;
    @FXML
    private TableColumn<Customer, String> surnamesColumn;

    @FXML
    private Label idCustomerLabel;
    @FXML
    private Label namesLabel;
    @FXML
    private Label surnamesLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label telephoneLabel;

    // Reference to the main application.
    private MainApp mainApp;

    DaoFactory factory = DaoFactory.getInstance();
    CustomerService service = factory.getCustomerDao(Util.CST);

    /**
     * The constructor. The constructor is called before the initialize()
     * method.
     */
    public CustomerOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the customer table with the two columns.
        namesColumn.setCellValueFactory(
                cellData -> cellData.getValue().namesProperty());
        surnamesColumn.setCellValueFactory(
                cellData -> cellData.getValue().surnamesProperty());

        // Clear customer details.
        showCustomerDetails(null);

        // Listen for selection changes and show the customer details when changed.
        customerTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showCustomerDetails(newValue));
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        customerTable.setItems(mainApp.getCustomerData());
    }

    /**
     * Fills all text fields to show details about the customer. If the
     * specified customer is null, all text fields are cleared.
     *
     * @param customer the customer or null
     */
    private void showCustomerDetails(Customer customer) {
        if (customer != null) {
            // Fill the labels with info from the customer object.
            CustomerTO customerTO = service.findCustomer(customer.getSurnames(), customer.getNames());
            System.out.println(customerTO);
            idCustomerLabel.setText(Integer.toString(customerTO.getIdCustomer()));
            namesLabel.setText(customerTO.getNames());
            surnamesLabel.setText(customerTO.getSurnames());
            emailLabel.setText(customerTO.getEmail());
            telephoneLabel.setText(customerTO.getTelephone());
        } else {
            // Customer is null, remove all the text.
            idCustomerLabel.setText("   _ _ _ _ _ _ _");
            namesLabel.setText("   _ _ _ _ _ _ _");
            surnamesLabel.setText("   _ _ _ _ _ _ _");
            emailLabel.setText("   _ _ _ _ _ _ _");
            telephoneLabel.setText("   _ _ _ _ _ _ _");
        }
    }

    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDeleteCustomer() {
        int selectedIndex = customerTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            boolean okClicked1 = mainApp.showDialogOperation();
            Customer customer = customerTable.getItems().remove(selectedIndex);
            service.deleteCustomer(customer.getIdCustomer());
        } else {
            // Nothing selected.
            Dialogs.create()
                    .title("No Selection")
                    .masthead("No Customer Selected")
                    .message("Please select a customer in the table.")
                    .showWarning();
        }
    }

    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new customer.
     */
    @FXML
    private void handleNewCustomer() {
        Customer tempCustomer = new Customer();
        boolean okClicked = mainApp.showCustomerEditDialog(tempCustomer);
        if (okClicked) {
            service.createCustomer(parse(tempCustomer));
            
            if (mainApp.showDialogOperation()) {
                CustomerTO customerTO = service.createCustomer(parse(tempCustomer));
                System.out.println(customerTO);
                mainApp.getCustomerData().add(parse(customerTO));
            }
        }
    }
    
     private Customer parse(CustomerTO customerTO){
        return new Customer(customerTO.getIdCustomer(), 
                customerTO.getNames(), 
                customerTO.getSurnames(), 
                customerTO.getEmail(), 
                customerTO.getTelephone());
    }

    private CustomerTO parse(Customer customer) {
        return new CustomerTO(customer.getIdCustomer(),
                customer.getNames(),
                customer.getSurnames(),
                customer.getEmail(),
                customer.getTelephone());
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected customer.
     */
    @FXML
    private void handleEditCustomer() {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            boolean okClicked = mainApp.showCustomerEditDialog(selectedCustomer);
            if (okClicked) {
                if (mainApp.showDialogOperation()) {
                    CustomerTO customerTO = service.updateCustomer(parse(selectedCustomer));
                    System.out.println(customerTO);
                    showCustomerDetails(parse(customerTO));
                }
            }

        } else {
            // Nothing selected.
            Dialogs.create()
                    .title("No Selection")
                    .masthead("No Customer Selected")
                    .message("Please select a customer in the table.")
                    .showWarning();
        }
    }
}
