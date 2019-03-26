package sample;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main extends Application {

     static int dealerId;

    @FXML
    private JFXTextField userName;
    @FXML
    private JFXPasswordField passWord;
    static int   OwnerId;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("login_page.fxml"));
        primaryStage.setTitle("Straco Leaders");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }


    public void login_button_click() throws Exception   {
        System.out.print("hi im a loged in");

        String username=userName.getText();
        String password=passWord.getText();
        try {
            Connection connection = getConnection();
            PreparedStatement getLoginDetails = connection.prepareStatement("SELECT id,username,password FROM login_table");
            ResultSet resultSet = getLoginDetails.executeQuery();

            while (resultSet.next()) {
                if (((username.equals(resultSet.getString("username")))) && (password.equals( resultSet.getString("password")))) {
                    System.out.println("username matched");
                    dealerId = resultSet.getInt("id");
                   Controller controller=new  Controller();
                           controller.CreateScreen();
                }
                else if (((username.equals("VimalShah"))) && (password.equals( "VimalShah"))){
                    System.out.println("username matched");
                    OwnerId = resultSet.getInt("id");
                    ControllerOwner controllerOwner=new ControllerOwner();
                    controllerOwner.CreateScreen();
                    break;
                }
            }
        }catch (Exception e){System.out.println();}
    }

    public static void get() throws Exception{
        try {
            Connection con = getConnection();
            PreparedStatement getData = con.prepareStatement("SELECT username,password FROM login_table");
            ResultSet result = getData.executeQuery();

            while(result.next()){
                System.out.print(result.getString("username"));
                System.out.print(" ");
                System.out.println(result.getString("password")+"");
            }

        }catch (Exception e){System.out.println(e);}
    }
    public static void post()throws Exception{
        final String userName ="vimal";
        final String passWord = "vimal";

        try{
            Connection con =getConnection();
            PreparedStatement posted = con.prepareStatement("INSERT INTO login_table (username,password) VALUES ('"+userName+"','"+passWord+"')");
            posted.executeUpdate();
        }catch (Exception e){System.out.println("cannot update"+e);}
    }
    public static void createTable()throws Exception {
        try {
            Connection con = getConnection();
            PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS  login(id int NOT NULL AUTO_INCREMENT PRIMARY KEY,username varchar(225),password varchar(225))");
            create.execute();
            System.out.println("create successull");
        }catch (Exception e){System.out.println(e);}
        finally {
            System.out.println("Function complete");
        }
    }
    public static Connection getConnection() throws Exception{

        try{
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://127.0.0.1:3306/login";
            String username = "root";
            String password = "10@ugusT1965";
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url,username,password);
            System.out.println("connected");
            return conn;
        }catch (Exception e){System.out.println(e);}

        return null;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
