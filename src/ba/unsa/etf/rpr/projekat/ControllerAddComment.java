package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ControllerAddComment {
    public Button okButton;
    public Button cancelButton;
    public TextArea comentArea;
    private Child child;

    public ControllerAddComment(Child child){
        this.child = child;
    }



    public void initialize(){
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) cancelButton.getScene().getWindow();
                stage.close();
            }
        });

        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean sveOk = validirajPrazno(comentArea);

                if(sveOk) {
                    ArrayList<Day> days = child.desrialize();
                    Day day = days.get(days.size() - 1);
                    day.getComments().add("\n" + comentArea.getText());
                    days.remove(days.size() - 1);
                    days.add(day);
                    child.serialize(days);
                    Stage stage = (Stage) okButton.getScene().getWindow();
                    stage.close();
                }
            }
        });

    }

    private boolean validirajPrazno(TextArea polje) {
        if (polje.getText().trim().isEmpty()) {
            polje.getStyleClass().removeAll("poljeIspravno");
            polje.getStyleClass().add("poljeNijeIspravno");
            return false;
        } else {
            polje.getStyleClass().removeAll("poljeNijeIspravno");
            polje.getStyleClass().add("poljeIspravno");
        }
        return true;
    }
}
