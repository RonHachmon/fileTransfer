package com.example.app;

import engine.DirectoryManager;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import task.CopyFileTask;


import java.io.File;
import java.util.List;

public class MainAppController {


    @FXML
    private TextField jpegPath;

    @FXML
    private TextField nefPath;

    @FXML
    private TextField destinationPath;




    @FXML
    private ListView<String> jpegListFile;

    @FXML
    private ListView<String> nefListFile;
    @FXML
    private ListView<String> destinationListFile;


    @FXML
    private Label amountOfJpegFile;

    @FXML
    private Label amountOfNefFile;

    @FXML
    private Label filesCopied;



    @FXML
    private Button copyButton;
    @FXML
    private ProgressBar copyTaskBar;


    private CopyFileTask currentRunningTask;

   private DirectoryManager directoryManager=new DirectoryManager();

    @FXML
    void copyFiles(ActionEvent event) {
        currentRunningTask = new CopyFileTask(directoryManager,this);
        new Thread(currentRunningTask).start();

        addFileNameFromFolderToList(destinationListFile,directoryManager.getDesFolder());
    }


    @FXML
    void selectJpegFolder(MouseEvent event) {
        File selectedDirectory = chooseDirectory();
        if(selectedDirectory!=null)
        {
            jpegPath.setText(selectedDirectory.getAbsolutePath());
            directoryManager.setJpegFolder(selectedDirectory);
            addFileNameFromFolderToList(jpegListFile,directoryManager.getJpegFolder());
            amountOfJpegFile.setText(String.valueOf(directoryManager.getTotalFilesInJpegFolder()));
            enableCopy();
        }


    }

    @FXML
    void selectNefFolder(MouseEvent event) {
        File selectedDirectory = chooseDirectory();

        if(selectedDirectory!=null)
        {
            nefPath.setText(selectedDirectory.getAbsolutePath());
            directoryManager.setNefFolder(selectedDirectory);
            addFileNameFromFolderToList(nefListFile,directoryManager.getNefFolder());
            amountOfNefFile.setText(String.valueOf(directoryManager.getTotalFilesInNefFolder()));
            enableCopy();

        }

    }

    @FXML
    void selectDefFolder(MouseEvent event) {

        File selectedDirectory = chooseDirectory();
        if(selectedDirectory!=null)
        {
            destinationPath.setText(selectedDirectory.getAbsolutePath());
            directoryManager.setDesFolder(selectedDirectory);
            enableCopy();
        }


    }

    private void addFileNameFromFolderToList(ListView<String> listOfFiles, File directory) {
        listOfFiles.getItems().clear();
        List<String> listOfFilesInDirectory = directoryManager.getListOfFilesInDirectory(directory);
        listOfFiles.getItems().addAll(listOfFilesInDirectory);
    }


    private void enableCopy() {
        if (!destinationPath.getText().isEmpty() && !nefPath.getText().isEmpty()
                && !jpegPath.getText().isEmpty()) {
            copyButton.setDisable(false);
        }
    }




    private File chooseDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        return directoryChooser.showDialog(null);
    }
    public void bindTaskToUIComponents(Task aTask) {
        // task progress bar
        copyTaskBar.progressProperty().bind(aTask.progressProperty());


        filesCopied.textProperty().bind(Bindings.createStringBinding(() -> {
            int filesCopiedValue = (int) aTask.getWorkDone();
            return String.format("%,d", filesCopiedValue);
        }, aTask.workDoneProperty()));
    }


}