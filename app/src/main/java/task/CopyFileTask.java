package task;

import com.example.app.MainAppController;
import engine.DirectoryManager;
import javafx.concurrent.Task;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class CopyFileTask extends Task<Boolean> {

    private long totalWorkSize = 0;
    private DirectoryManager directoryManager;
    private ScheduledExecutorService timedExecute;
    private MainAppController mainAppController;

    public CopyFileTask(DirectoryManager directoryManager, MainAppController mainAppController) {
        this.directoryManager = directoryManager;
        this.mainAppController=mainAppController;
        this.mainAppController.bindTaskToUIComponents(this);

        DaemonThread daemonThreadFactory = new DaemonThread();
        timedExecute = Executors.newSingleThreadScheduledExecutor(daemonThreadFactory);

        /*        this.controller = bruteForceController;
        controller.bindTaskToUIComponents(this);*/


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
        updateProgress(directoryManager.getTotalFilesInDestination(), totalWorkSize);
        return;
    }
}
