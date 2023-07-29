package engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class DirectoryManager {
    private File jpegFolder;
    private int totalFilesInJpegFolder;
    private File nefFolder;
    private int totalFilesInNefFolder;

    private File desFolder;
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

        File[] files = jpegFolder.listFiles(file-> file.isFile());
        for (int i = 0; i <files.length ; i++) {
            String name = files[i].getName();
            String fileWithNoExtension = name.split("\\.")[0];
            File fileToCopy = getNefFilePath(fileWithNoExtension);
            File outputFile = parseFileDestination(this.desFolder,fileToCopy);

            copyFile(fileToCopy, outputFile);
        }
    }

    private void copyFile(File fileToCopy, File outputFile) {
        try {
            Files.copy(fileToCopy.toPath(), outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File getNefFilePath(String fileName) {
        String nefFilePath=nefFolder.getPath() +File.separator+ fileName+".NEF";
        File fileToCopy = new File(nefFilePath);
        return fileToCopy;
    }
    private File parseFileDestination(File destinationPath,File fileName) {
        String nefFilePath=destinationPath.getPath() +File.separator+ fileName.getName();
        File fileToCopy = new File(nefFilePath);
        return fileToCopy;
    }

    public File getJpegFolder() {
        return jpegFolder;
    }

    public void setJpegFolder(File jpegFolder) {

        this.jpegFolder = jpegFolder;
        this.setTotalFilesInJpegFolder();
    }

    public File getNefFolder() {
        return nefFolder;
    }

    public void setNefFolder(File nefFolder) {

        this.nefFolder = nefFolder;
        this.setTotalFilesInNefFolder();
    }
    public File getDesFolder() {
        return desFolder;
    }

    public void setDesFolder(File desFolder) {
        this.desFolder = desFolder;
    }

    public int getTotalFilesInJpegFolder() {
        return totalFilesInJpegFolder;
    }

    private void setTotalFilesInJpegFolder() {

        List<String> listOfFilesInDirectory = this.getListOfFilesInDirectory(this.jpegFolder);
        this.totalFilesInJpegFolder = listOfFilesInDirectory.size();
    }

    public int getTotalFilesInNefFolder() {
        return this.totalFilesInNefFolder;

    }

    private void setTotalFilesInNefFolder() {
        List<String> listOfFilesInDirectory = this.getListOfFilesInDirectory(this.nefFolder);
        this.totalFilesInNefFolder = listOfFilesInDirectory.size();
    }



    public int getTotalFilesInDestination() {
        List<String> listOfFilesInDirectory = this.getListOfFilesInDirectory(this.desFolder);
        this.totalFilesInDestination = listOfFilesInDirectory.size();
        return totalFilesInDestination;
    }


}
