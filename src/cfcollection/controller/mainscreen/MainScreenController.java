package com.cfcollection.controller.mainscreen;

import com.cfcollection.model.Submission;
import com.cfcollection.utils.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MainScreenController extends Component {
    private final long SLEEP_DURATION = 250;
    private final int MAX_CONTEST_ID = 10000;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab settingsTab;
    @FXML
    private TextField handle;
    @FXML
    private TextField directory;
    @FXML
    private Button getButton;
    @FXML
    private ChoiceBox option;
    @FXML
    private Label progressStatus;
    @FXML
    private ProgressBar progress;

    @FXML
    private void initialize() {
        option.setItems(FXCollections.observableArrayList("All", "Time", "Memory"));
        option.getSelectionModel().selectFirst();
    }

    @FXML
    private void setDirectory(ActionEvent actionEvent) {
        JFileChooser file = new JFileChooser();
        file.setCurrentDirectory(new java.io.File("."));
        file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        file.setAcceptAllFileFilterUsed(false);
        file.showOpenDialog(this);
        if (file.getSelectedFile() != null) {
            directory.setText(file.getSelectedFile().toString());
            directory.setDisable(false);
        }
    }

    private JsonObject checkInfo(String url) throws InterruptedException, IOException {
        Thread.sleep(SLEEP_DURATION);
        return JsonUtils.stringToJsonObject(WebUtils.documentFromUrl(url));
    }

    private TreeMap<String, ArrayList<Submission>> collectData(JsonObject data) {
        JsonArray result = data.getAsJsonArray("result");
        TreeMap<String, ArrayList<Submission>> okSubmission = new TreeMap<>();
        for (JsonElement submission: result) {
            if (submission.getAsJsonObject().getAsJsonPrimitive("verdict").getAsString().equals("OK")) {
                String contestId = submission.getAsJsonObject()
                        .getAsJsonPrimitive("contestId").getAsString();
                if (Integer.parseInt(contestId) > MAX_CONTEST_ID) continue;
                Submission tmp = new Submission();
                tmp.setId(submission.getAsJsonObject().getAsJsonPrimitive("id").getAsInt());
                tmp.setContestId(contestId);
                tmp.setProblemIndex(submission.getAsJsonObject().getAsJsonObject("problem").
                        getAsJsonPrimitive("index").getAsCharacter());
                tmp.setTime(submission.getAsJsonObject()
                        .getAsJsonPrimitive("timeConsumedMillis").getAsInt());
                tmp.setMemory(submission.getAsJsonObject()
                        .getAsJsonPrimitive("memoryConsumedBytes").getAsInt());
                tmp.setLanguage(submission.getAsJsonObject()
                        .getAsJsonPrimitive("programmingLanguage").getAsString());
                String problem = tmp.getContestId() + tmp.getProblemIndex();
                okSubmission.computeIfAbsent(problem, k -> new ArrayList<>());
                okSubmission.get(problem).add(tmp);
            }
        }
        if (option.getValue() == "Time") {
            okSubmission.forEach((problem, submissions) -> submissions.sort(new TimeComparator()));
        } else if (option.getValue() == "Memory") {
            okSubmission.forEach((problem, submissions) -> submissions.sort(new MemoryComparator()));
        }

        return okSubmission;
    }

    private void getSource(Submission submission, int order, AtomicInteger index, AtomicInteger total, boolean multi) {
        index.getAndIncrement();
        String submissionUrl = "https://codeforces.com/contest/" + submission.getContestId() + "/submission/"
                + submission.getId();
        String folderName = submission.getContestId();
        String currentStatus = "Contest: " + folderName + " (" + index.get() + " / " + total.get() + ")";
        Platform.runLater(() -> {
            progressStatus.setText(currentStatus);
            progress.setProgress(index.get() * 1.0 / total.get());
        });
        try {
            String subDirectory = directory.getText() + "/" + folderName;
            String fileName = subDirectory + "/" +
                    submission.getContestId() +
                    Character.toLowerCase(submission.getProblemIndex());
            if (multi) {
                fileName += "_" + order;
            }
            fileName += FileUtils.sourceExtension(submission.getLanguage());
            try {
                Thread.sleep(SLEEP_DURATION);
            } catch (InterruptedException ignored) {

            }
            String html = Jsoup.connect(submissionUrl).get().html();
            Document doc = Jsoup.parse(html);
            String tmp = doc.body().getElementById("program-source-text").text();
            new File(subDirectory).mkdir();
            PrintWriter writer = new PrintWriter(fileName);
            writer.println(tmp);
            writer.close();

        } catch (IOException e) {
            Platform.runLater(() -> progressStatus.setText(currentStatus + " (Error)"));
        }
    }

    private void setDisable(boolean disable) {
        handle.setDisable(disable);
        getButton.setDisable(disable);
        settingsTab.setDisable(disable);
    }

    private void resetProgress() {
        progressStatus.setText("Progress");
        progress.setProgress(0);
    }

    private int programError() {
        final int HANDLE = (handle.getText() == null || handle.getText().length() == 0) ? 1 : 0;
        final int DIRECTORY = (directory.getText() == null || directory.getText().length() == 0) ? 1 : 0;
        final int MODE = (option.getValue() == null) ? 1 : 0;
        return HANDLE + (DIRECTORY << 1) + (MODE << 2);
    }

    @FXML
    private void getSources(){
        Task<Void> backgroundTask = new Task<Void>() {
            @Override
            protected Void call() {
                setDisable(true);
                int error = programError();
                if (error > 0) {
                    Platform.runLater(() -> {
                        String errorMessage;
                        if (error == 1) {
                            errorMessage = "Handle is empty!";
                        } else {
                            if (error == 2 || error == 3) {
                                errorMessage = "Directory is not set!";
                            } else if (error == 4 || error == 5) {
                                errorMessage = "Mode is empty!";
                            } else {
                                errorMessage = "Settings is empty!";
                            }
                        }
                        Alert alert = AlertUtils.getNonHeaderAlert(null, errorMessage);
                        alert.showAndWait();
                        if (error > 1) {
                            tabPane.getSelectionModel().select(1);
                        }
                    });
                } else {
                    Platform.runLater(() -> progressStatus.setText("Collecting data..."));
                    String api = "https://codeforces.com/api/user.status?handle=" + handle.getText();
                    JsonObject user = null;
                    try {
                        user = checkInfo(api);
                    } catch (InterruptedException | IOException e) {
                        e.printStackTrace();
                    }
                    if (user == null || user.getAsJsonPrimitive("status").getAsString().equals("FAILED")) {
                        Platform.runLater(() -> {
                            Alert alert = AlertUtils.getNonHeaderAlert(null, "Handle not found!");
                            alert.showAndWait();
                            resetProgress();
                        });
                    } else {
                        TreeMap<String, ArrayList<Submission> > data = collectData(user);
                        boolean multi = option.getValue() == "All";
                        AtomicInteger total = new AtomicInteger();
                        data.forEach((problem, submissions) -> {
                            if (multi) total.addAndGet(submissions.size());
                            else total.incrementAndGet();
                        });
                        AtomicInteger index = new AtomicInteger();
                        data.forEach((problem, submissions) -> {
                            for (int i = 0; i < submissions.size(); i++) {
                                getSource(submissions.get(i), i + 1, index, total, multi & (submissions.size() > 1));
                                if (!multi) break;
                            }
                        });
                        Platform.runLater(() -> {
                            Alert alert = AlertUtils.getNonHeaderAlert(null, "Done!");
                            alert.showAndWait();
                            resetProgress();
                        });
                    }
                }
                Platform.runLater(() -> setDisable(false));

                return null;
            }
        };
        Thread backgroundThread = new Thread(backgroundTask);
        backgroundThread.setDaemon(true);
        backgroundThread.start();
    }

}
