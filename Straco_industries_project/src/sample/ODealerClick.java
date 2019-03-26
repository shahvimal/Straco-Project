package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.xml.transform.Result;
import java.io.*;
import java.sql.*;

public class ODealerClick {
    private int countDealersClick=0;
    private ListView<OproductDetail> Olist;


    ODealerClick(){}
    ODealerClick(int count,ListView<OproductDetail > list1){
        Olist=list1;
        countDealersClick=count;

        listFilling();
    }

    static class Cell extends ListCell<OproductDetail> {

        productDetail tempProduct ;




        HBox hBox = new HBox();

        VBox vLabel  = new VBox();

        Label phoneNumber = new Label();
        Label dealerName = new Label();
        Label address = new Label();
        Label paymentsLeft = new Label();


        ImageView dealerImage = new ImageView("img/ic_account_circle_black_48dp.png");

        public Cell()
        {
            super();
            dealerName.setFont(Font.font(16));
            phoneNumber.setFont(Font.font(16));
            paymentsLeft.setFont(Font.font(16));
            address.setFont(Font.font(16));


            vLabel.getChildren().addAll(dealerName,phoneNumber,address,paymentsLeft);
            hBox.getChildren().addAll(dealerImage,vLabel);


        }

        public void updateItem(OproductDetail Odealer,boolean empty){

            super.updateItem(Odealer,empty);
            setText(null);
            setGraphic(null);
            if ((Odealer!=null)&& !empty){
                dealerName.setText(Odealer.getmDealerName());
                dealerImage.setImage(Odealer.getMdealerImage());
                phoneNumber.setText("Phone Number: "+ Odealer.getmPhoneNumber());
                address.setText("Address :\n\t"+Odealer.getmDealerAddress());
                paymentsLeft.setText("Payments Left:: Rs."+Odealer.getmDealerPayments());
                setGraphic(hBox);
            }
        }
    }

    public void listFilling() {

        if (countDealersClick ==0) {

            Olist.getItems().clear();
            try {
                Connection con = getConnection();
                PreparedStatement getData = con.prepareStatement("SELECT *,paymentLeft FROM dealer_table,payments_table");
                ResultSet result = getData.executeQuery();


                while(result.next()){

                    InputStream is=result.getBinaryStream("dealerImage");
                    OutputStream os = new FileOutputStream(new File("photo.jpg"));
                    byte [] content = new byte[1024];
                    int size=0;
                    while((size=is.read(content))!=-1){
                        os.write(content,0,size);
                    }
                    os.close();
                    is.close();

                    Olist.getItems().add(new OproductDetail(new Image("file:photo.jpg")  , String.valueOf(result.getString("dealerName")), result.getString("phoneNumber"),result.getString("address"),result.getString("payments_table.paymentLeft")));
                }

            }catch (Exception e){System.out.println(e);}

            Olist.setCellFactory(param -> new ODealerClick.Cell());
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
            PreparedStatement getData = conn.prepareStatement("SELECT * FROM dealer_table ");
            ResultSet result = getData.executeQuery();
            if(result.next()){

            }
            return conn;
        }catch (Exception e){System.out.println(e); }

        return null;
    }

}
