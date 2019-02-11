package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ControllerAddChild {


    public Button okButton;
    public Button cancelButton;

    public TextField nameField;
    public TextField surenameField;
    public TextField jmbgField;
    public DatePicker dateField;
    public ComboBox placeCombo;
    public ComboBox institutionCombo;
    public TextField nameParentField;
    public TextField surenameParrentField;
    public TextField jmbgParentField;
    public DatePicker dateParrentField;
    public TextField phoneNumberField;
    public Button addPlaceButton;
    public RadioButton specialNeedsNo;
    public RadioButton specialNeedsYes;

    private KindergartenDAO base = new KindergartenDAO();
    public Child chilD;
    ResourceBundle bundle = ResourceBundle.getBundle("Trn");

    public ControllerAddChild(Child child){
        this.chilD = child;
    }

    public void initialize(){

        institutionCombo.setItems(base.getInstitutions());

        placeCombo.setItems(base.getPlaces());

        addPlaceButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FXMLLoader loader1 = new FXMLLoader(getClass().getClassLoader().getResource("fxml/addPlace.fxml"), bundle);
                Parent root = null;
                try {
                    root = loader1.load();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                Stage stage = new Stage();
                stage.setTitle("Dodaj mjesto");
                stage.setResizable(false);
                stage.setScene(new Scene(root, 304, 159));
                stage.showAndWait();
                placeCombo.setItems(base.getPlaces());
                placeCombo.getSelectionModel().selectLast();
            }
        });

        specialNeedsYes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(specialNeedsYes.isSelected()) specialNeedsNo.setSelected(false);
            }
        });

        specialNeedsNo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(specialNeedsNo.isSelected()) specialNeedsYes.setSelected(false);
            }
        });

        if(chilD != null){
            nameField.setText(chilD.getName());
            surenameField.setText(chilD.getSurename());
            jmbgField.setText(chilD.getJmbg());
            dateField.setValue(chilD.getDateOfBirth());
            placeCombo.getSelectionModel().select(chilD.getDwelling());
            institutionCombo.getSelectionModel().select(chilD.getInstitution());
            nameParentField.setText(chilD.getParentsName());
            surenameParrentField.setText(chilD.getParentsSurename());
            jmbgParentField.setText(chilD.getJmbg());
            dateParrentField.setValue(chilD.getParentsDateOfBirth());
            phoneNumberField.setText(chilD.getPhoneNumber());
            if(chilD.isChildWithSpecialNeeds()) specialNeedsYes.setSelected(true);
            else if(!chilD.isChildWithSpecialNeeds()) specialNeedsNo.setSelected(true);

        }

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
                if(chilD == null){
                    boolean sveOk = validirajPrazno(nameField);
                    sveOk &= validirajPrazno(surenameField);
                    sveOk &= validirajPrazno(nameParentField);
                    sveOk &= validirajPrazno(surenameParrentField);
                    sveOk &= validirajPrazno(phoneNumberField);

                    if (placeCombo.getSelectionModel().isEmpty()) {
                        placeCombo.getStyleClass().removeAll("poljeIspravno");
                        placeCombo.getStyleClass().add("poljeNijeIspravno");
                        sveOk = false;
                    } else {
                        placeCombo.getStyleClass().removeAll("poljeNijeIspravno");
                        placeCombo.getStyleClass().add("poljeIspravno");
                    }


                    if (institutionCombo.getSelectionModel().isEmpty()) {
                        institutionCombo.getStyleClass().removeAll("poljeIspravno");
                        institutionCombo.getStyleClass().add("poljeNijeIspravno");
                        sveOk = false;
                    } else {
                        institutionCombo.getStyleClass().removeAll("poljeNijeIspravno");
                        institutionCombo.getStyleClass().add("poljeIspravno");
                    }

                    if(!specialNeedsYes.isSelected() && !specialNeedsNo.isSelected()){
                        sveOk = false;
                    }

                    if (dateField.getValue() == null || dateField.getValue().isAfter(LocalDate.now())) {
                        dateField.getStyleClass().removeAll("poljeIspravno");
                        dateField.getStyleClass().add("poljeNijeIspravno");
                        sveOk = false;
                    } else {
                        dateField.getStyleClass().removeAll("poljeNijeIspravno");
                        dateField.getStyleClass().add("poljeIspravno");
                    }

                    if (dateParrentField.getValue() == null || dateParrentField.getValue().isAfter(LocalDate.now())) {
                        dateParrentField.getStyleClass().removeAll("poljeIspravno");
                        dateParrentField.getStyleClass().add("poljeNijeIspravno");
                        sveOk = false;
                    } else {
                        dateParrentField.getStyleClass().removeAll("poljeNijeIspravno");
                        dateParrentField.getStyleClass().add("poljeIspravno");
                    }

                    if (!validateJmbgC(jmbgField.getText())) {
                        jmbgField.getStyleClass().removeAll("poljeIspravno");
                        jmbgField.getStyleClass().add("poljeNijeIspravno");
                        sveOk = false;
                    } else {
                        jmbgField.getStyleClass().removeAll("poljeNijeIspravno");
                        jmbgField.getStyleClass().add("poljeIspravno");
                    }

                    if (!validateJmbgP(jmbgParentField.getText())) {
                        jmbgParentField.getStyleClass().removeAll("poljeIspravno");
                        jmbgParentField.getStyleClass().add("poljeNijeIspravno");
                        sveOk = false;
                    } else {
                        jmbgParentField.getStyleClass().removeAll("poljeNijeIspravno");
                        jmbgParentField.getStyleClass().add("poljeIspravno");
                    }




                if(sveOk) {

                    Child child = new Child(nameField.getText(), surenameField.getText(), base.getMaxIdFromChildren());
                    child.setDateOfBirth(dateField.getValue());
                    child.setJmbg(jmbgField.getText());
                    child.setDwelling((Place) placeCombo.getSelectionModel().getSelectedItem());
                    child.setInstitution((Institution) institutionCombo.getSelectionModel().getSelectedItem());
                    child.setChildWithSpecialNeeds(specialNeedsYes.isSelected());
                    child.setParentsName(nameParentField.getText());
                    child.setParentsSurename(surenameParrentField.getText());
                    child.setParentsJmbg(jmbgParentField.getText());
                    child.setParentsDateOfBirth(dateParrentField.getValue());
                    child.setPhoneNumber(phoneNumberField.getText());

                    base.addChild(child);

                    Stage stage = (Stage) okButton.getScene().getWindow();
                    stage.close();
                }

                } else {
                    base.removeChild(chilD);

                    boolean sveOk = validirajPrazno(nameField);
                    sveOk &= validirajPrazno(surenameField);
                    sveOk &= validirajPrazno(nameParentField);
                    sveOk &= validirajPrazno(surenameParrentField);
                    sveOk &= validirajPrazno(phoneNumberField);

                    if (placeCombo.getSelectionModel().isEmpty()) {
                        placeCombo.getStyleClass().removeAll("poljeIspravno");
                        placeCombo.getStyleClass().add("poljeNijeIspravno");
                        sveOk = false;
                    } else {
                        placeCombo.getStyleClass().removeAll("poljeNijeIspravno");
                        placeCombo.getStyleClass().add("poljeIspravno");
                    }


                    if (institutionCombo.getSelectionModel().isEmpty()) {
                        institutionCombo.getStyleClass().removeAll("poljeIspravno");
                        institutionCombo.getStyleClass().add("poljeNijeIspravno");
                        sveOk = false;
                    } else {
                        institutionCombo.getStyleClass().removeAll("poljeNijeIspravno");
                        institutionCombo.getStyleClass().add("poljeIspravno");
                    }

                    if(!specialNeedsYes.isSelected() && !specialNeedsNo.isSelected()){
                        sveOk = false;
                    }

                    if (dateField.getValue() == null || dateField.getValue().isAfter(LocalDate.now())) {
                        dateField.getStyleClass().removeAll("poljeIspravno");
                        dateField.getStyleClass().add("poljeNijeIspravno");
                        sveOk = false;
                    } else {
                        dateField.getStyleClass().removeAll("poljeNijeIspravno");
                        dateField.getStyleClass().add("poljeIspravno");
                    }

                    if (dateParrentField.getValue() == null || dateParrentField.getValue().isAfter(LocalDate.now())) {
                        dateParrentField.getStyleClass().removeAll("poljeIspravno");
                        dateParrentField.getStyleClass().add("poljeNijeIspravno");
                        sveOk = false;
                    } else {
                        dateParrentField.getStyleClass().removeAll("poljeNijeIspravno");
                        dateParrentField.getStyleClass().add("poljeIspravno");
                    }

                    if (!validateJmbgC(jmbgField.getText())) {
                        jmbgField.getStyleClass().removeAll("poljeIspravno");
                        jmbgField.getStyleClass().add("poljeNijeIspravno");
                        sveOk = false;
                    } else {
                        jmbgField.getStyleClass().removeAll("poljeNijeIspravno");
                        jmbgField.getStyleClass().add("poljeIspravno");
                    }

                    if (!validateJmbgP(jmbgParentField.getText())) {
                        jmbgParentField.getStyleClass().removeAll("poljeIspravno");
                        jmbgParentField.getStyleClass().add("poljeNijeIspravno");
                        sveOk = false;
                    } else {
                        jmbgParentField.getStyleClass().removeAll("poljeNijeIspravno");
                        jmbgParentField.getStyleClass().add("poljeIspravno");
                    }

                    if(sveOk) {
                        Child child = new Child(nameField.getText(), surenameField.getText(), base.getMaxIdFromChildren());

                        child.setDateOfBirth(dateField.getValue());
                        child.setJmbg(jmbgField.getText());
                        child.setDwelling((Place) placeCombo.getSelectionModel().getSelectedItem());
                        child.setInstitution((Institution) institutionCombo.getSelectionModel().getSelectedItem());
                        child.setChildWithSpecialNeeds(specialNeedsYes.isSelected());
                        child.setParentsName(nameParentField.getText());
                        child.setParentsSurename(surenameParrentField.getText());
                        child.setParentsJmbg(jmbgParentField.getText());
                        child.setParentsDateOfBirth(dateParrentField.getValue());
                        child.setPhoneNumber(phoneNumberField.getText());

                        base.addChild(child);

                        Stage stage = (Stage) okButton.getScene().getWindow();
                        stage.close();
                    }
                }
            }
        });

    }

    private boolean validateJmbgC(String jmbg) {
        if (jmbg.length() != 13)
            return false;

        LocalDate datum = dateField.getValue();
        if (datum.getDayOfMonth() != Integer.parseInt(jmbg.substring(0, 2)))
            return false;
        else if (datum.getMonthValue() != Integer.parseInt(jmbg.substring(2, 4)))
            return false;

        int godina = Integer.parseInt(jmbg.substring(4, 7));
        if (godina > 500) godina += 1000; else godina += 2000;
        if (datum.getYear() != godina)
            return false;

        int[] br = new int[13];
        for (int i=0; i<13; i++) {
            br[i] = jmbg.charAt(i);
            br[i] -= 48;
        }
        int sum = 7*(br[0]+br[6]) + 6*(br[1]+br[7]) + 5*(br[2]+br[8]) + 4*(br[3]+br[9]) + 3*(br[4]+br[10]) + 2*(br[5]+br[11]);
        sum = 11 - (sum % 11);
        if (sum > 9) sum = 0;
        if (br[12] != sum) return false;
        return true;
    }

    private boolean validateJmbgP(String jmbg) {
        if (jmbg.length() != 13)
            return false;

        LocalDate datum = dateParrentField.getValue();
        if (datum.getDayOfMonth() != Integer.parseInt(jmbg.substring(0, 2)))
            return false;
        else if (datum.getMonthValue() != Integer.parseInt(jmbg.substring(2, 4)))
            return false;

        int godina = Integer.parseInt(jmbg.substring(4, 7));
        if (godina > 500) godina += 1000; else godina += 2000;
        if (datum.getYear() != godina)
            return false;

        int[] br = new int[13];
        for (int i=0; i<13; i++) {
            br[i] = jmbg.charAt(i);
            br[i] -= 48;
        }
        int sum = 7*(br[0]+br[6]) + 6*(br[1]+br[7]) + 5*(br[2]+br[8]) + 4*(br[3]+br[9]) + 3*(br[4]+br[10]) + 2*(br[5]+br[11]);
        sum = 11 - (sum % 11);
        if (sum > 9) sum = 0;
        if (br[12] != sum) return false;
        return true;
    }

    private boolean validirajPrazno(TextField polje) {
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