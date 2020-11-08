package com.emailclient;

import com.emailclient.controller.services.FetchFoldersService;
import com.emailclient.model.EmailAccount;
import com.emailclient.model.EmailTreeItem;
import javafx.scene.control.TreeItem;

public class EmailManager {
    private final EmailTreeItem<String> foldersRoot = new EmailTreeItem<>("");

    public TreeItem<String> getFoldersRoot() {
        return foldersRoot;
    }

    public void addEmailAccount(EmailAccount emailAccount) {
        EmailTreeItem<String> treeItem = new EmailTreeItem<>(emailAccount.getAddress());
        FetchFoldersService fetchFoldersService = new FetchFoldersService(emailAccount.getStore(), treeItem);
        fetchFoldersService.start();
        foldersRoot.getChildren().add(treeItem);
    }
}
