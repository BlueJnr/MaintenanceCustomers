/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bluejnr.customer.view;

import bluejnr.customer.MainApp;
import bluejnr.customer.model.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;

/**
 * FXML Controller class
 *
 * @author kcuadror
 */
public class CustomerEditDialogController {

    @FXML
    private Label idCustomerLabel;
    @FXML
    private Label idCustomerValue;
    @FXML
    private TextField namesField;
    @FXML
    private TextField surnamesField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField telephoneField;

    private Stage dialogStage;
    private Customer customer;
    private boolean okClicked = false;
    // Reference to the main application.
    private MainApp mainApp;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the customer to be edited in the dialog.
     *
     * @param customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;

        if (customer.getNames() == null) {
            idCustomerValue.setVisible(false);
            idCustomerLabel.setVisible(false);
        } else {
            idCustomerValue.setText(Integer.toString(customer.getIdCustomer()));
            idCustomerValue.setVisible(true);
            idCustomerLabel.setVisible(true);
        }
        namesField.setText(customer.getNames());
        surnamesField.setText(customer.getSurnames());
        emailField.setText(customer.getEmail());
        telephoneField.setText(customer.getTelephone());
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            customer.setIdCustomer(Integer.parseInt(idCustomerValue.getText()));
            customer.setNames(namesField.getText());
            customer.setSurnames(surnamesField.getText());
            customer.setEmail(emailField.getText());
            customer.setTelephone(telephoneField.getText());

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (namesField.getText() == null || namesField.getText().length() == 0) {
            errorMessage += "¡No hay un nombre válido!\n";
        }
        if (surnamesField.getText() == null || surnamesField.getText().length() == 0) {
            errorMessage += "¡No hay un apellido válido!\n";
        }
        if (emailField.getText() == null || emailField.getText().length() == 0) {
            errorMessage += "¡No hay un email válido!\n";
        }

        if (telephoneField.getText() == null || telephoneField.getText().length() == 0) {
            errorMessage += "¡No hay un telefono válido!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Dialogs.create()
                    .title("Campos inválidos")
                    .masthead("Por favor corrija los campos inválidos")
                    .message(errorMessage)
                    .showError();
            return false;
        }
    }
}
