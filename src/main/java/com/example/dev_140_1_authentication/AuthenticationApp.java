package com.example.dev_140_1_authentication;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;


public class AuthenticationApp extends Application {
    private Stage stage;
    private Repository repository = new Repository();
    private TextField userNameField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private Label result = new Label();

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setResizable(false);
        stage.setTitle("JavaFX Welcome");
        stage.setScene(getRootScene());
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    private Scene getRootScene(){
        AnchorPane root = new AnchorPane();
        GridPane gridPane = new GridPane();

        Text text = new Text("Welcome");
        text.setFill(Color.INDIGO);
        text.setStyle("-fx-font: 20 arial;");

        Font font = new Font("Calibri Bold Italic", 13);

        Label userName = new Label("User Name:");
        Label password = new Label("Password:");

        result.setFont(font);


        Button button = new Button("Sign in");

        button.setOnAction(e -> authentication());

        passwordField.setOnKeyPressed(e -> {
            if (e.getCode().toString().equals("ENTER")) {
                authentication();
            }
        });

        userNameField.setOnKeyPressed(e -> {
            if (e.getCode().toString().equals("ENTER")) {
                authentication();
            }
        });


        passwordField.setPrefSize(172, 20);
        passwordField.setMaxSize(172, 20);
        passwordField.setMinSize(172, 20);

        userNameField.setPrefSize(172, 20);
        userNameField.setMaxSize(172, 20);
        userNameField.setMinSize(172, 20);

        AnchorPane.setLeftAnchor(gridPane, 40.0);
        AnchorPane.setRightAnchor(gridPane, 20.0);
        AnchorPane.setTopAnchor(gridPane, 60.0);
        AnchorPane.setBottomAnchor(gridPane, 20.0);


        gridPane.add(text, 0, 0);
        gridPane.add(userName, 0, 1);
        gridPane.add(password, 0, 2);
        gridPane.add(passwordField, 1, 2);
        gridPane.add(userNameField, 1, 1);
        gridPane.add(button, 1, 4);
        gridPane.add(result, 1, 5);

        GridPane.setHalignment(button, HPos.RIGHT);


        ColumnConstraints columnConstraints1 = new ColumnConstraints();
        columnConstraints1.setPercentWidth(40);
        ColumnConstraints columnConstraints2 = new ColumnConstraints();
        columnConstraints2.setPercentWidth(80);


        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(15);


        gridPane.getColumnConstraints().addAll(columnConstraints1, columnConstraints2);
        gridPane.getRowConstraints().addAll(rowConstraints, rowConstraints, rowConstraints, rowConstraints, rowConstraints, rowConstraints);

        root.getChildren().add(gridPane);


        Scene scene = new Scene(root, 320, 240);
        return scene;
    }

    private void authentication() {
        try {
            for (Users i :
                    repository.findAllUsers()) {
                if (i.equalsNamePassword(new Users(userNameField.getText(), passwordField.getText()))) {
                    result.setText("Успешная аутентификация");
                } else result.setText("Неверный логин или пароль");
            }
        } catch (SQLException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}