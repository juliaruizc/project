package isen.project;

import java.time.LocalDate;
import java.util.regex.Pattern;


import db.daos.PersonDao;
import db.entities.Person;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class addController {
	
    private PersonDao personDao = new PersonDao();

    @FXML
    private Button add_Add;

    @FXML
    private TextField add_Address;

    @FXML
    private Button add_Back;

    @FXML
    private TextField add_Birth;

    @FXML
    private TextField add_Email;

    @FXML
    private TextField add_FirstName;

    @FXML
    private TextField add_ID;

    @FXML
    private TextField add_LastName;

    @FXML
    private TextField add_Nickname;

    @FXML
    private TextField add_Phone;

    @FXML
    private TableView<Person> add_Table;

    @FXML
    private TableColumn<Person,String> add_colAddress;

    @FXML
    private TableColumn<Person,LocalDate> add_colBirthDate;

    @FXML
    private TableColumn<Person,String> add_colEmail;

    @FXML
    private TableColumn<Person,String> add_colFirstName;

    @FXML
    private TableColumn<Person,Integer> add_colID;

    @FXML
    private TableColumn<Person,String> add_colLastName;

    @FXML
    private TableColumn<Person,String> add_colNickName;

    @FXML
    private TableColumn<Person,String> add_colPhone;
    
    
    //To validate de email and the phone number
    private Pattern p_email = Pattern.compile("\\b[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b");
    private Pattern p_phone = Pattern.compile("^\\d{10}$");

    

}
