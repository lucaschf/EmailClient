package com.emailclient.controller;

import com.emailclient.EmailManager;
import com.emailclient.controller.services.LoginService;
import com.emailclient.model.EmailAccount;
import com.emailclient.view.ViewFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginWindowController extends BaseController {

    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    public LoginWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    void loginButtonAction(ActionEvent event) {
        if (fieldsAreValid()) {
            EmailAccount emailAccount = new EmailAccount(emailTextField.getText(), passwordField.getText());
            LoginService loginService = new LoginService(emailAccount, emailManager);
            loginService.start();
            loginService.setOnSucceeded(event1 -> {
                EmailLoginResult loginResult = loginService.getValue();
                switch (loginResult) {
                    case SUCCESS:
                        System.out.println("login success" + emailAccount);
                        if (!viewFactory.isMainViewInitialized()) {
                            viewFactory.showMainWindow();
                        }

                        Stage stage = (Stage) errorLabel.getScene().getWindow();
                        viewFactory.closeStage(stage);
                        return;
                    case FAILED_BY_CREDENTIALS:
                        errorLabel.setText("Invalid credentials");
                        break;
                    case FAILED_BY_UNEXPECTED_ERROR:
                        errorLabel.setText("Unexpected error");
                        break;
                    default:
                        break;
                }
            });
        }
    }

    private boolean fieldsAreValid() {
        if (emailTextField.getText().isEmpty()) {
            errorLabel.setText("Please fill email address");
            return false;
        }

        if (passwordField.getText().isEmpty()) {
            errorLabel.setText("Please fill password");
            return false;
        }

        return true;
    }
}