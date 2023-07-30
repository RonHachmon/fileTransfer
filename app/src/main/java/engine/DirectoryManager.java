package engine;

import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;


public class DirectoryManager {
    private File jpegFolder=null;
    private int totalFilesInJpegFolder;
    private File nefFolder=null;
    private int totalFilesInNefFolder;

    private File desFolder=null;
    private int totalFilesInDestination;



    public List<String> getListOfFilesInDirectory(File chosenDirectory)
    {
        List<String> fileNames = new ArrayList<>();

        if (chosenDirectory.isDirectory()) {
            File[] files = chosenDirectory.listFiles(file -> file.isFile());
            if (files != null) {
                for (File file : files) {
                    fileNames.add(file.getName());
                }
            }
        }

        return fileNames;
    }


    public void copyFiles()
    {
        if(isValidInputs()) {

            File[] jpegFiles  = jpegFolder.listFiles(file -> file.isFile());
            for (File jpegFile : jpegFiles) {
                String fileName = jpegFile.getName().split("\\.")[0];
                File nefFileToCopy = new File(nefFolder, fileName + ".NEF");
                File outputFile = new File(this.desFolder, nefFileToCopy.getName());

                copyFile(nefFileToCopy, outputFile);
            }
        }
    }


    private boolean isValidInputs() {
        return jpegFolder != null && nefFolder != null && desFolder != null;
    }

    private void copyFile(File fileToCopy, File outputFile) {
        try {
            Files.copy(fileToCopy.toPath(), outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public File getJpegFolder() {
        return jpegFolder;
    }

    public void setJpegFolder(File jpegFolder) {
        if(jpegFolder.isDirectory())
        {
            this.jpegFolder = jpegFolder;
            this.setTotalFilesInJpegFolder();
        }

    }

    public File getNefFolder() {
        return nefFolder;
    }

    public void setNefFolder(File nefFolder) {
        if(nefFolder.isDirectory()) {
            this.nefFolder = nefFolder;
            this.setTotalFilesInNefFolder();
        }
    }
    public File getDesFolder() {

        return desFolder;
    }

    public void setDesFolder(File desFolder) {
        if(desFolder.isDirectory()) {
            this.desFolder = desFolder;
        }
    }



    private void setTotalFilesInJpegFolder() {

        List<String> listOfFilesInDirectory = this.getListOfFilesInDirectory(this.jpegFolder);
        this.totalFilesInJpegFolder = listOfFilesInDirectory.size();
    }
    public int getTotalFilesInJpegFolder() {
        return totalFilesInJpegFolder;
    }



    private void setTotalFilesInNefFolder() {
        List<String> listOfFilesInDirectory = this.getListOfFilesInDirectory(this.nefFolder);
        this.totalFilesInNefFolder = listOfFilesInDirectory.size();
    }
    public int getTotalFilesInNefFolder() {
        return this.totalFilesInNefFolder;

    }



    public int getTotalFilesInDestination() {
        List<String> listOfFilesInDirectory = this.getListOfFilesInDirectory(this.desFolder);
        this.totalFilesInDestination = listOfFilesInDirectory.size();
        return totalFilesInDestination;
    }


}
