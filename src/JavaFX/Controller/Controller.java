package JavaFX.Controller;

import Connection.ConnectionClass;
import JavaFX.Model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


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

    public Controller() {
    }

    /**
     * Функция -initialize()- предназначена для привязки столбцов к соответствующим переменным в классе -Person-
     * Запускается при создании таблицы, т.е. при запуске проекта.
     * Последняя строка -initializeTableValues();- вызывает метод для заполнения таблицы данными из Базы данных.
     */
    @FXML
    private void initialize(){
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        surnameColumn.setCellValueFactory(cellData -> cellData.getValue().surnameProperty());
        numberColumn.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());

        initializeTableValues();
    }

    /**
     * Следующие строки предназначены для создания объекта класса -ConnectionClass-
     * и вызова его метода -getConnection()-
     * Переменная -connection- далее используется для отправки запросов на базу данных
     */
    ConnectionClass connectionClass = new ConnectionClass();
    Connection connection = connectionClass.getConnection();


    /**
     * Метод -insertPerson(ActionEvent actionEvent)- привязан к кнопке -Создать- на главной странице
     * при нажатии которой из текста полей -nameField, surnameField, phoneNumberField- формируется
     * запрос добавления(INSERT) в базу данных.
     *
     * Далее значения полей -nameField, surnameField, phoneNumberField- опустошаются и вызывается метод
     * -initializeTableValues();- для заполнения таблицы новыми данными из Базы данных.
     *
     * @param actionEvent
     */
    public void insertPerson(ActionEvent actionEvent) {
        try {
            Statement statement=connection.createStatement();
            String sql="INSERT INTO person VALUES ('"+nameField.getText() + "', '" + surnameField.getText() + "', '" + phoneNumberField.getText() + "');";
            statement.executeUpdate(sql);

            System.out.println("Success!");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        nameField.setText("");
        surnameField.setText("");
        phoneNumberField.setText("");

        initializeTableValues();

    }

    /**
     * Данный метод делает запрос -SELECT- в базу данных и из полученных данных формирует список
     * типа -ObservableList<Person>-, с помощью которого заполняет таблицу -personTable-
     */
    public void initializeTableValues(){
        try {
            Statement statement=connection.createStatement();

            String sql="SELECT * FROM person;";

            ResultSet resultSet=statement.executeQuery(sql);

            ObservableList<Person> personList = FXCollections.observableArrayList();

            if (resultSet.next()){
                while (resultSet.next()) {
                    Person person = new Person(resultSet.getString("name"), resultSet.getString("surname"), resultSet.getString("phonenumber"));
                    personList.add(person);
                }
                personTable.setItems(personList);
            }else {
                System.out.println("no data");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
