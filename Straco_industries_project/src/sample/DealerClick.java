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

import java.io.*;
import java.sql.*;

public class DealerClick {
    private int countDealersClick=0;
    private ListView<productDetail > list;


    DealerClick(){}
    DealerClick(int count,ListView<productDetail > list1){
        list=list1;
        countDealersClick=count;

        listFilling();
    }

    static class Cell extends ListCell<productDetail> {

        productDetail tempProduct ;




        HBox hBox = new HBox();

        VBox vLabel  = new VBox();

        Label phoneNumber = new Label();
        Label dealerName = new Label();
        Label address = new Label();

        ImageView dealerImage = new ImageView("img/ic_account_circle_black_48dp.png");

        public Cell()
        {
            super();
            dealerName.setFont(Font.font(16));
            phoneNumber.setFont(Font.font(16));

            address.setFont(Font.font(16));


            vLabel.getChildren().addAll(dealerName,phoneNumber,address);
            hBox.getChildren().addAll(dealerImage,vLabel);


        }

        public void updateItem(productDetail dealer,boolean empty){

            super.updateItem(dealer,empty);
            setText(null);
            setGraphic(null);
            if ((dealer!=null)&& !empty){
                dealerName.setText(dealer.getmDealerName());
                dealerImage.setImage(dealer.getMdealerImage());
                phoneNumber.setText("Phone Number: "+ dealer.getmPhoneNumber());
                address.setText("Address :\n\t"+dealer.getmDealerAddress());
                setGraphic(hBox);
            }
        }
    }

    public void listFilling() {

        if (countDealersClick ==0) {

            list.getItems().clear();
            try {
                Connection con = getConnection();
                PreparedStatement getData = con.prepareStatement("SELECT * FROM dealer_table");
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

                    list.getItems().add(new productDetail(new Image("file:photo.jpg")  , String.valueOf(result.getString("dealerName")), result.getString("phoneNumber"),result.getString("address")));
                }

            }catch (Exception e){System.out.println(e);}

            list.setCellFactory(param -> new DealerClick.Cell());
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
            PreparedStatement getData = conn.prepareStatement("SELECT * FROM dealer_table WHERE id ='"+Main.dealerId+"'");
            ResultSet result = getData.executeQuery();
            if(result.next()){

            }
            return conn;
        }catch (Exception e){System.out.println(e); }

        return null;
    }

}
