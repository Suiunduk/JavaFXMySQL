package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.ArrayList;


public class Controller {
    @FXML
    public TextField nameField;
    @FXML
    public TextField surnameField;
    @FXML
    public TextField phoneNumberField;

    @FXML
    public TableView<Person> personTable;
    @FXML
    public TableColumn<Person, String> nameColumn;
    @FXML
    public TableColumn<Person, String> surnameColumn;
    @FXML
    public TableColumn<Person, String> numberColumn;

    @FXML
    private void initialize(){
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        surnameColumn.setCellValueFactory(cellData -> cellData.getValue().surnameProperty());
        numberColumn.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
    }

    public ObservableList<Person> personList = FXCollections.observableArrayList();

    public void insertPerson(ActionEvent actionEvent){
        Person person = new Person(nameField.getText(), surnameField.getText(), phoneNumberField.getText());
        personList.add(person);

        nameField.setText("");
        surnameField.setText("");
        phoneNumberField.setText("");

        personTable.setItems(personList);

    }
}
