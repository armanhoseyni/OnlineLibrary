package org.example.onlinelibrary;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class bookController implements Initializable {

    Connection con =null;
    PreparedStatement st=null;

    @FXML
    private  int id;
    @FXML
    private Button btn_Exit;
    ResultSet rs=null;

    @FXML
    private TextField txt_SrchBookTitle;


    @FXML
    private TextField txt_AddBookTitle;
    @FXML
    private TextField txt_AddBookSubject;
    @FXML
    private TextField txt_AddBookAthor;
@FXML
    private TableColumn<books, Integer> colID;

    @FXML
    private TableView tbl_books;
    @FXML

    private TableColumn<books,String> colSubject;
    @FXML

    private TableColumn<books,String> colTitle;
    @FXML

    private TableColumn<books,String> colIs;

    @FXML

    private TableColumn<books,String> colAuthor;
    @FXML
    private boolean chektable=false;
@FXML
    private TableColumn<books,String> IsAvailable;
    @FXML
    private TextField txt_showMessage;
    @FXML
    private TableColumn<members,String> colUsername;
    @FXML

    private TableColumn<members,String> colPassword;

    @FXML
    public ObservableList<books> getBooks(){
        ObservableList<books> books= FXCollections.observableArrayList();
        String query="select * from books";

        con= DBcon.getCon();
        try {
            st=con.prepareStatement(query);
            rs=st.executeQuery();
            while (rs.next()){

                books b=new books();
                b.setId(rs.getInt("Id"));
                b.setSubject(rs.getString(("subject")));
                b.setTitle(rs.getString(("title")));
                b.setAuthor(rs.getString(("author")));
                b.setIsReservd(rs.getString(("IsReservd")));

                books.add(b);
            }
        }catch (SQLException e){

            throw new RuntimeException(e);
        }
        return books;
    }


    @Override
    public  void initialize(URL url, ResourceBundle resourceBundle){
        showBooks();

    }

    @FXML
    public void showBooks(){
        if (chektable){ObservableList<books>list=getBooks();
            tbl_books.setItems(list);
            colID.setCellValueFactory(new PropertyValueFactory<books,Integer>("id"));
            colSubject.setCellValueFactory((new  PropertyValueFactory<books,String>("subject")));
            colTitle.setCellValueFactory((new  PropertyValueFactory<books,String>("title")));
            colAuthor.setCellValueFactory((new  PropertyValueFactory<books,String>("author")));
            colIs.setCellValueFactory((new  PropertyValueFactory<books,String>("IsReservd")));
    }


}
    public void getData(javafx.scene.input.MouseEvent mouseEvent) {
        books book= (books) tbl_books.getSelectionModel().getSelectedItem();
        id=book.getId();


    }
    @FXML
    void deleteBook(ActionEvent event){
        String delete="delete from books where id=?";
        con=DBcon.getCon();
        try {
            st=con.prepareStatement(delete);
            st.setInt(1,id);
            st.executeUpdate();

        }
        catch (SQLException e){

            throw new RuntimeException(e);
        }

        showBooks();


    }

    @FXML
    public void searchBook() {
        ObservableList<books> allBooks = getBooks();

        FilteredList<books> filteredData = new FilteredList<>(allBooks, b -> true);

        txt_SrchBookTitle.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(book -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Show all books when the search field is empty
                }

                // Convert both the book title and search text to lowercase for case-insensitive search
                String lowerCaseFilter = newValue.toLowerCase();

                return book.getTitle().toLowerCase().contains(lowerCaseFilter);
            });
        });

        // Wrap the filtered data in a SortedList
        SortedList<books> sortedData = new SortedList<>(filteredData);

        // Bind the SortedList to the TableView
        sortedData.comparatorProperty().bind(tbl_books.comparatorProperty());
        tbl_books.setItems(sortedData);
    }

    @FXML
    private void GoToHomePage(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login page.fxml"));
            Parent root = fxmlLoader.load();

            Stage newStage = new Stage();
            newStage.setTitle("Login page");
            newStage.setScene(new Scene(root, 750, 700));

            // Get the current stage from the event source
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            currentStage.close(); // Close the current stage
            newStage.show(); // Show the new stage
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
