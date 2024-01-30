package org.example.onlinelibrary;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.lang.reflect.Member;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class memberController  implements Initializable{
Connection con =null;
PreparedStatement st=null;
ResultSet rs=null;



@FXML
public Button btn_Exit;
@FXML
private TableView<members>tbl_Member;


@FXML
private TableColumn<members, Integer> colID;
    @FXML

    private TableColumn<members,String> colName;
    @FXML

    private TableColumn<members,String> colFamily;

    @FXML

    private TableColumn<members,String> colTell;
    @FXML

    private TableColumn<members,String> colEmail;
    @FXML
    private TextField txt_showMessage;
    @FXML
    private TableColumn<members,String> colUsername;
    @FXML

    private TableColumn<members,String> colPassword;
    @FXML

    private TextField txt_AddMemberName;
    @FXML
    private boolean chektable=false;
    @FXML

    private TextField txt_AddMemberPassword;
    @FXML

    private TextField txt_AddMemberFamily;
    @FXML

    private TextField txt_AddMemberEmail;

    @FXML

    private TextField txt_AddMemberTell;
    @FXML

    private TextField txt_AddMemberUsername;

    @FXML
    private  int id;




    @Override
    public  void initialize(URL url, ResourceBundle resourceBundle){
        showMembers();

    }
  @FXML

    public ObservableList<members> getMembers(){
        ObservableList<members> members= FXCollections.observableArrayList();
        String query="select * from members";

        con= DBcon.getCon();
        try {
            st=con.prepareStatement(query);
            rs=st.executeQuery();
            while (rs.next()){

                members mb=new members();
                mb.setId(rs.getInt("Id"));
                mb.setName(rs.getString(("name")));
                mb.setFamily(rs.getString(("family")));
                mb.setTell(rs.getString(("tell")));
                mb.setEmail(rs.getString(("email")));
                mb.setUsername(rs.getString(("username")));
                mb.setPassword(rs.getString(("password")));
                members.add(mb);
            }
        }catch (SQLException e){

            throw new RuntimeException(e);
        }
        return members;
    }


@FXML
void createMember(ActionEvent event){

        String insert="insert into members(name,family,tell,email,username,password) values(?,?,?,?,?,?)";
        con=DBcon.getCon();
        try{

            st=con.prepareStatement(insert);
            st.setString(1,txt_AddMemberName.getText());
            st.setString(2,txt_AddMemberFamily.getText());
            st.setString(3,txt_AddMemberTell.getText());
            st.setString(4,txt_AddMemberEmail.getText());
            st.setString(5,txt_AddMemberUsername.getText());
            st.setString(6,txt_AddMemberPassword.getText());
            st.executeUpdate();
            txt_showMessage.setText("ثبت نام با موفقیت انجام شد");
        }
            catch (SQLException e){
            throw new RuntimeException(e);
            }


}

@FXML
void deleteMember(ActionEvent event){
        String delete="delete from members where id=?";
        con=DBcon.getCon();
        try {
            st=con.prepareStatement(delete);
            st.setInt(1,id);
            st.executeUpdate();

        }
        catch (SQLException e){

            throw new RuntimeException(e);
        }

        showMembers();


}

@FXML
    public void showMembers(){
    if (chektable){ObservableList<members>list=getMembers();
        tbl_Member.setItems(list);
        colID.setCellValueFactory(new PropertyValueFactory<members,Integer>("id"));
        colName.setCellValueFactory((new  PropertyValueFactory<members,String>("name")));
        colFamily.setCellValueFactory((new  PropertyValueFactory<members,String>("family")));
        colTell.setCellValueFactory((new  PropertyValueFactory<members,String>("tell")));
        colEmail.setCellValueFactory((new  PropertyValueFactory<members,String>("email")));
        colUsername.setCellValueFactory((new  PropertyValueFactory<members,String>("username")));
        colPassword.setCellValueFactory((new  PropertyValueFactory<members,String>("password")));}


}


    public void getData(javafx.scene.input.MouseEvent mouseEvent) {
        members member=tbl_Member.getSelectionModel().getSelectedItem();
        id=member.getId();


    }


    @FXML
    private Stage currentStage;

}
