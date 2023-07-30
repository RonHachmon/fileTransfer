package task;

import com.example.app.MainAppController;
import engine.DirectoryManager;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class CopyFileTask extends Task<Boolean> {

    private long totalWorkSize = 0;
    private SimpleIntegerProperty copiedFiles = new SimpleIntegerProperty();

    public ReadOnlyIntegerProperty getCopiedFilesProperty() {
        return copiedFiles;
    }


    private DirectoryManager directoryManager;
    private ScheduledExecutorService timedExecute;
    private MainAppController mainAppController;

    public CopyFileTask(DirectoryManager directoryManager, MainAppController mainAppController) {
        this.directoryManager = directoryManager;
        this.mainAppController=mainAppController;
        this.mainAppController.bindTaskToUIComponents(this);

        DaemonThread daemonThreadFactory = new DaemonThread();
        timedExecute = Executors.newSingleThreadScheduledExecutor(daemonThreadFactory);
    }

    ScheduledFuture<?> scheduledFuture;


    @Override
    protected Boolean call() throws Exception {
        updateMessage("starting to work");
        totalWorkSize = directoryManager.getTotalFilesInJpegFolder();
        updateProgress(0, totalWorkSize);
        //uiAdapter.updateProgress("permutation: "+0+"/"+Utils.formatToIntWithCommas(totalWorkSize));
        startTimedTask();
        directoryManager.copyFiles();
        return false;
    }
    private void startTimedTask() {
        scheduledFuture = timedExecute.scheduleAtFixedRate(() -> update(), 100, 100, TimeUnit.MILLISECONDS);
    }

    private void update() {
        System.out.println("Hey i am running :)");
        copiedFiles.setValue( directoryManager.getTotalFilesInDestination());
        updateProgress(copiedFiles.getValue(), totalWorkSize);
        handleIfDone();
        return;
    }

    private void handleIfDone() {
        if(copiedFiles.getValue()>=totalWorkSize)
        {
            timedExecute.shutdownNow();
            super.cancelled();
            this.cancel();
        }
    }
}
