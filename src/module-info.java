module JavaEfxEmailClient {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.web;
    requires java.mail;
    requires activation;

    opens com.emailclient;
    opens com.emailclient.view;
    opens com.emailclient.controller;
}