package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ControllerOwner {



    private int dealerId=1;
    private int countDealersClick=0;
    private int countProductsClick=0;
    private int countRequestClick=0;
    private int countPaymentsClick=0;
    productDetail temp;

    @FXML
    private ListView<OproductDetail > Olist;

    @FXML
    private Label main_label;


    public  void CreateScreen() throws Exception {
        Stage primaryStage = new Stage();
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setTitle("Vimal Shah (OWNER)");
        Parent root = FXMLLoader.load(getClass().getResource("owner.fxml"));
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();

    }

    public Connection getConnection() throws Exception{

        try{
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://127.0.0.1:3306/login";
            String username = "root";
            String password = "10@ugusT1965";
            Class.forName(driver);

            Connection conn = DriverManager.getConnection(url,username,password);
            PreparedStatement getData = conn.prepareStatement("SELECT * FROM dealer_table WHERE id ='"+dealerId+"'");
            ResultSet result = getData.executeQuery();
            if(result.next()){

            }
            return conn;
        }catch (Exception e){System.out.println(e); }

        return null;
    }

    public void ODealersClicked(){
        if(countDealersClick%2==0) {

            countRequestClick=0;

            countProductsClick=0;

            countPaymentsClick=0;

            main_label.setText("Dealers");
            Olist.setVisible(true);
            System.out.println("Dealers clicked");
            new ODealerClick(countDealersClick,Olist);
            countDealersClick++;
        }
        else
        {
            main_label.setText("Select From List");
            countDealersClick++;
            Olist.setVisible(false);
        }
    }
    public void OPaymentsClicked(){
        System.out.println("Payments clicked");
        if(countPaymentsClick%2==0) {

            countRequestClick=0;

            countProductsClick=0;

            countDealersClick=0;
            main_label.setText("Payments");
            Olist.setVisible(true);
           // new PaymentsClick(countPaymentsClick,Olist);
            countPaymentsClick++;
        }
        else
        {
            main_label.setText("Select From List");
            countPaymentsClick++;
            Olist.setVisible(false);
        }
    }
    public void ORequestClicked(){
        System.out.println("Request Clicked");

        if(countRequestClick%2==0) {
            countDealersClick=0;
            countProductsClick=0;
            countPaymentsClick=0;
            main_label.setText("Request");
           Olist.setVisible(true);
            new ORequestClick(countRequestClick,Olist);
            countRequestClick++;
        }
        else
        {
            main_label.setText("Select From List");
            countRequestClick++;
           Olist.setVisible(false);
        }
    }
    public void OProductsClicked(){
        System.out.println("Products clicked");
        if(countProductsClick%2==0) {

            countRequestClick=0;

            countDealersClick=0;

            countPaymentsClick=0;
            main_label.setText("Products");
            Olist.setVisible(true);
            System.out.println("Dealers clicked");
            new OProductClick(countProductsClick,Olist);
            countProductsClick++;
        }
        else
        {
            main_label.setText("Select From List");
            countProductsClick++;
            Olist.setVisible(false);
        }
    }
}