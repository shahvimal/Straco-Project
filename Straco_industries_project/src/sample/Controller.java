package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.DepthTest;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.text.Element;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Controller {



    private int dealerId=1;
    private int countDealersClick=0;
    private int countProductsClick=0;
    private int countRequestClick=0;
    private int countPaymentsClick=0;
    productDetail temp;

   @FXML
   private ListView<productDetail > list;

   @FXML
   private Label main_label;


    public  void CreateScreen() throws Exception {
        Stage primaryStage = new Stage();
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setTitle("Dealer");
        Parent root = FXMLLoader.load(getClass().getResource("dealer.fxml"));
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();

    }

    public  Connection getConnection() throws Exception{

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

    public void DealersClicked(){
        if(countDealersClick%2==0) {

                countRequestClick=0;

                countProductsClick=0;

                countPaymentsClick=0;

                main_label.setText("Dealers");
            list.setVisible(true);
            System.out.println("Dealers clicked");
            new DealerClick(countDealersClick,list);
            countDealersClick++;
        }
        else
        {
            main_label.setText("Select From List");
            countDealersClick++;
            list.setVisible(false);
        }
    }
    public void PaymentsClicked(){
        System.out.println("Payments clicked");
        if(countPaymentsClick%2==0) {

                countRequestClick=0;

                countProductsClick=0;

                countDealersClick=0;
            main_label.setText("Payments");
            list.setVisible(true);
            new PaymentsClick(countPaymentsClick,list);
            countPaymentsClick++;
        }
        else
        {
            main_label.setText("Select From List");
            countPaymentsClick++;
            list.setVisible(false);
        }
    }
    public void RequestClicked(){
        System.out.println("Request Clicked");

        if(countRequestClick%2==0) {
                countDealersClick=0;
                countProductsClick=0;
                countPaymentsClick=0;
            main_label.setText("Request");
            list.setVisible(true);
            new RequestClick(countRequestClick,list);
            countRequestClick++;
        }
        else
        {
            main_label.setText("Select From List");
            countRequestClick++;
            list.setVisible(false);
        }
    }
    public void ProductsClicked(){
        System.out.println("Products clicked");
        if(countProductsClick%2==0) {

                countRequestClick=0;

                countDealersClick=0;

                countPaymentsClick=0;
            main_label.setText("Products");
            list.setVisible(true);
            System.out.println("Dealers clicked");
             new ProductClick(countProductsClick,list);
            countProductsClick++;
        }
        else
        {
            main_label.setText("Select From List");
            countProductsClick++;
            list.setVisible(false);
        }
    }
}
