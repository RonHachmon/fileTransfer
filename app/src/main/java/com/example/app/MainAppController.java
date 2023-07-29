package com.example.app;

import engine.DirectoryManager;
import javafx.beans.binding.Bindings;
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
    private ScrollPane listOfJpegFiles;

    @FXML
    private ListView<String> jpegListFile;

    @FXML
    private Label amountOfJpegFile;

    @FXML
    private Label amountOfNefFile;

    @FXML
    private Label filesCopied;

    @FXML
    private ScrollPane listOfNefFiles;

    @FXML
    private ScrollPane listOfDetinationFiles;

    @FXML
    private ListView<String> destinationListFile;

    @FXML
    private ListView<String> nefListFile;

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
        File destFolder = new File(destinationPath.getText());
        //directoryManager.copyFiles(destFolder);
        List<String> listOfFilesInDirectory = directoryManager.getListOfFilesInDirectory(destFolder);
        destinationListFile.getItems().addAll(listOfFilesInDirectory);
        filesCopied.setText(String.valueOf(listOfFilesInDirectory.size()));

    }

    @FXML
    void selectJpegFolder(MouseEvent event) {
        File selectedFile = chooseDirectory();
        if(selectedFile!=null)
        {
            jpegPath.setText(selectedFile.getAbsolutePath());
            directoryManager.setJpegFolder(selectedFile);
            List<String> listOfFilesInDirectory = directoryManager.getListOfFilesInDirectory(selectedFile);
            jpegListFile.getItems().addAll(listOfFilesInDirectory);
            amountOfJpegFile.setText(String.valueOf(listOfFilesInDirectory.size()));
            enableCopy();
        }


    }

    @FXML
    void selectNefFolder(MouseEvent event) {
        File selectedFile = chooseDirectory();

        if(selectedFile!=null)
        {
            nefPath.setText(selectedFile.getAbsolutePath());
            directoryManager.setNefFolder(selectedFile);
            List<String> listOfFilesInDirectory = directoryManager.getListOfFilesInDirectory(selectedFile);
            nefListFile.getItems().addAll(listOfFilesInDirectory);

            amountOfNefFile.setText(String.valueOf(listOfFilesInDirectory.size()));
            enableCopy();

        }

    }

    @FXML
    void selectDefFolder(MouseEvent event) {

        File selectedFile = chooseDirectory();
        if(selectedFile!=null)
        {
            destinationPath.setText(selectedFile.getAbsolutePath());
            directoryManager.setDesFolder(selectedFile);
            enableCopy();
        }


    }

    private void enableCopy() {
        if(!destinationPath.getText().isEmpty() && !nefPath.getText().isEmpty()
            && !jpegPath.getText().isEmpty())
        {
            copyButton.setDisable(false);
        }

    }




    private File chooseDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);


        return selectedDirectory;
    }
    public void bindTaskToUIComponents(CopyFileTask aTask) {


        // task progress bar
        copyTaskBar.progressProperty().bind(aTask.progressProperty());



    }




}