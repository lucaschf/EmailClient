package com.emailclient;

import com.emailclient.controller.services.FetchFoldersService;
import com.emailclient.controller.services.FolderUpdaterService;
import com.emailclient.model.EmailAccount;
import com.emailclient.model.EmailMessage;
import com.emailclient.model.EmailTreeItem;

import javax.mail.Flags;
import javax.mail.Folder;
import java.util.ArrayList;
import java.util.List;

public class EmailManager {

    private final FolderUpdaterService folderUpdaterService;
    //Folder handling:
    private final EmailTreeItem<String> foldersRoot = new EmailTreeItem<String>("");
    private final List<Folder> folderList = new ArrayList<Folder>();
    private EmailMessage selectedMessage;
    private EmailTreeItem<String> selectedFolder;

    public EmailManager() {
        folderUpdaterService = new FolderUpdaterService(folderList);
        folderUpdaterService.start();
    }

    public EmailMessage getSelectedMessage() {
        return selectedMessage;
    }

    public void setSelectedMessage(EmailMessage selectedMessage) {
        this.selectedMessage = selectedMessage;
    }

    public EmailTreeItem<String> getSelectedFolder() {
        return selectedFolder;
    }

    public void setSelectedFolder(EmailTreeItem<String> selectedFolder) {
        this.selectedFolder = selectedFolder;
    }

    public EmailTreeItem<String> getFoldersRoot() {
        return foldersRoot;
    }

    public List<Folder> getFolderList() {
        return this.folderList;
    }

    public void addEmailAccount(EmailAccount emailAccount) {
        EmailTreeItem<String> treeItem = new EmailTreeItem<String>(emailAccount.getAddress());
        FetchFoldersService fetchFoldersService = new FetchFoldersService(emailAccount.getStore(), treeItem, folderList);
        fetchFoldersService.start();
        foldersRoot.getChildren().add(treeItem);
    }

    public void setRead() {
        try {
            selectedMessage.setRead(true);
            selectedMessage.getMessage().setFlag(Flags.Flag.SEEN, true);
            selectedFolder.decrementMessagesCount();
        } catch (Exception e) {

        }
    }
}