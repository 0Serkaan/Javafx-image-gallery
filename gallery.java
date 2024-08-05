import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class HelloApplication extends Application {
    private List<Image> images = new ArrayList<>();
    private int currentImageIndex = 0;
    @Override
    public void start(Stage primaryStage) {
        FlowPane imageGallery = new FlowPane();
        File folder = new File("folder_path");
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile() && (file.getName().endsWith(".jpg") || file.getName().endsWith(".png"))) {
                    Image image = new Image(file.toURI().toString());
                    images.add(image);

                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(100);
                    imageView.setFitHeight(100);
                    imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> openImageModal(image));
                    imageGallery.getChildren().add(imageView);
                }
            }
        }

        Scene scene = new Scene(imageGallery, 800, 600);
        primaryStage.setTitle("Image Gallery");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openImageModal(Image image) {
        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);

        BorderPane borderPane = new BorderPane();
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(500);
        borderPane.setCenter(imageView);

        Button nextButton = new Button("Next");
        nextButton.setOnAction(e -> showNextImage(imageView));
        Button prevButton = new Button("Previous");
        prevButton.setOnAction(e -> showPreviousImage(imageView));

        HBox buttonBox = new HBox(prevButton, nextButton);
        buttonBox.setSpacing(10);
        borderPane.setBottom(buttonBox);

        Scene modalScene = new Scene(borderPane, 600, 400);
        modalStage.setScene(modalScene);
        modalStage.show();
    }

    private void showNextImage(ImageView imageView) {
        currentImageIndex = (currentImageIndex + 1) % images.size();
        imageView.setImage(images.get(currentImageIndex));
    }

    private void showPreviousImage(ImageView imageView) {
        currentImageIndex = (currentImageIndex - 1 + images.size()) % images.size();
        imageView.setImage(images.get(currentImageIndex));
    }

    public static void main(String[] args) {
        launch(args);
    }}

