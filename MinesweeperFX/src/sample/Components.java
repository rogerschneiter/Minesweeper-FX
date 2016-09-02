package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.text.html.Option;

public class Components {
    static Stage tempStage;

    private static Stage buildWindow (String title) {
        tempStage = new Stage();
        tempStage.initStyle(StageStyle.UTILITY);
        tempStage.setResizable(false);
        tempStage.initModality(Modality.APPLICATION_MODAL);
        tempStage.setTitle(title);
        return tempStage;
    }

    static boolean okay = false;
    static boolean tempOkay = false;

    public static boolean ok (String title, String message) {
        Stage window = buildWindow(title);
        Label l = new Label(message);

        Button bo = new Button("_OK");
        Button ba = new Button("_Abbrechen");

        bo.setOnAction(e ->
        {
            tempOkay = true;
            window.close();
        });

        ba.setOnAction(e ->
        {
            tempOkay = false;
            window.close();
        });

        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(20));
        buttons.getChildren().addAll(bo, ba);

        VBox layout = new VBox(20);
        layout.getChildren().addAll(l, buttons);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40, 20, 20, 20));

        int width;
        int x = 6;
        int y = 150;

        width = message.length() * x + y;

        layout.setOnKeyReleased(e ->
        {
            if (e.getCode() == KeyCode.ESCAPE)
                window.close();
        });

        window.setScene(new Scene(layout, width, 150));
        window.showAndWait();

        okay = tempOkay;
        tempOkay = false;

        return okay;
    }

    public static Options options () {
        Options o = new Options();

        Stage window = buildWindow("Options");

        Button bo = new Button("Save");
        bo.setPrefWidth(70);
        Button ba = new Button("Cancel");
        ba.setPrefWidth(70);

        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setPrefWidth(200);

        ComboBox cb = new ComboBox();
        cb.setPrefWidth(200);

        ObservableList<String> values = FXCollections.observableArrayList();
        values.addAll("Very Easy", "Easy", "Normal", "Hard", "Very Hard");
        cb.setItems(values);
        cb.setPromptText("Select Difficulty");

        Label l0 = new Label("Options");

        TextField tf1 = new TextField();
        TextField tf2 = new TextField();

        tf1.setPromptText("Rows (Max. 20)");
        tf1.setMaxWidth(200);

        tf2.setPromptText("Columns (Max. 20)");
        tf2.setMaxWidth(200);

        bo.setOnAction(e ->
        {
            if (cb.getValue() == null) {
                simpleInfoBox("Warning", "Please select a difficulty!", "Ok");
                return;
            }

            int diff;

            switch (cb.getValue().toString()) {
                case "Very Easy":
                    diff = 1;
                    break;
                case "Easy":
                    diff = 2;
                    break;
                case "Normal":
                    diff = 3;
                    break;
                case "Hard":
                    diff = 4;
                    break;
                case "Very Hard":
                    diff = 5;
                    break;
                default:
                    diff = 0;
                    break;
            }

            int i1 = 0;
            int i2 = 0;

            try {
                i1 = Integer.parseInt(tf1.getText());
                i2 = Integer.parseInt(tf2.getText());
            } catch (Exception ex) {
                simpleInfoBox("Warning", "Only Numbers allowed as rows/cols!", "Ok");
                return;
            }

            if (i1 > 20 || i1 < 1 || i2 > 20 || i2 < 1) {
                simpleInfoBox("Warning", "Only Numbers between 1-50 as rows/cols!", "Ok");
                return;
            }

            o.setDifficulty(diff);
            o.setRowCount(i1);
            o.setColumnCount(i2);

            window.close();
        });

        ba.setOnAction(e ->
        {
            window.close();
        });

        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(20));
        buttons.getChildren().addAll(bo, ba);

        VBox layout = new VBox(20);
        layout.getChildren().addAll(l0, cb, colorPicker, tf1, tf2, buttons);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20, 20, 20, 20));

        window.setScene(new Scene(layout, 250, 355));
        window.showAndWait();

        return o;
    }

    public static void simpleInfoBox (String title, String message, String button) {
        Stage window = buildWindow(title);
        Label l = new Label(message);
        l.setWrapText(true);

        Button b = new Button(button);

        b.setOnAction(e -> window.close());

        VBox layout = new VBox(20);
        Insets s = new Insets(15,15,15,15);
        layout.setPadding(s);
        layout.getChildren().addAll(l, b);
        layout.setAlignment(Pos.CENTER);

        int width;
        int x = 6;
        int y = 150;
        width = 250;

        layout.setOnKeyReleased(e ->
        {
            if (e.getCode() == KeyCode.ESCAPE)
                window.close();
        });

        window.setScene(new Scene(layout, width, 150));
        window.show();
    }
}
