package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ORequestClick {
    private int countRequestClick=0;
    private ListView<OproductDetail > Olist;

    ORequestClick(int count,ListView<OproductDetail > list1){
        Olist=list1;
        countRequestClick=count;

        listFilling();
    }


    static class Cell extends ListCell<OproductDetail> {

        OproductDetail tempProduct ;






        Label status = new Label();
        HBox hBox = new HBox();

        VBox vLabel  = new VBox();

        Label payment = new Label();
        Label productName = new Label();
        Label numberOfProduct = new Label();

        ImageView productImage = new ImageView();

        public Cell()
        {

            super();
            numberOfProduct.setFont(Font.font(16));
            productName.setFont(Font.font(16));
            payment.setFont(Font.font(16));
            status.setFont(Font.font(16));

            productImage.setFitHeight(200);
            productImage.setFitWidth(265);
            productImage.setPreserveRatio(true);
            productImage.setSmooth(true);
            productImage.setCache(true);


            vLabel.getChildren().addAll(productName,numberOfProduct,payment,status);
            hBox.getChildren().addAll(productImage,vLabel);

        }

        public void updateItem(OproductDetail request,boolean empty){

            super.updateItem(request,empty);
            setText(null);
            setGraphic(null);
            if ((request!=null)&& !empty){
                productName.setText(request.getMrequestedPname());
                numberOfProduct.setText("Total Number Of Product Requested:\n\t"+request.getMrequestedPcount());
                productImage.setImage(request.getmRequestedImage());
                payment.setText("Payment    Rs. "+ request.getmPayments());
                status.setText(request.getMstatus());
                setGraphic(hBox);
            }
        }

    }

    public void listFilling() {
        if (countRequestClick ==0) {

            Olist.getItems().clear();
            try {
                Connection con = getConnection();
                PreparedStatement getData = con.prepareStatement("SELECT * FROM request_table,product_table,payments_table WHERE request_table.dealerId='"+Main.dealerId+"'");
                ResultSet result = getData.executeQuery();

                while(result.next()) {

                    if ((result.getInt("request_table.dealerId")==Main.dealerId)&&(result.getInt("product_table.product_number")==result.getInt("request_table.productNumber"))) {

                        InputStream is = result.getBinaryStream("productImage");
                        OutputStream os = new FileOutputStream(new File("photo1.jpg"));
                        byte[] content = new byte[1024];
                        int size = 0;
                        while ((size = is.read(content)) != -1) {
                            os.write(content, 0, size);
                        }
                        os.close();
                        is.close();

                        Olist.getItems().add(new OproductDetail(new Image("file:photo1.jpg"), String.valueOf(result.getString("productName")), result.getString("price"), result.getString("status"),result.getInt("request_table.count")));
                        break;
                    }

                }

            }catch (Exception e){System.out.println(e);}

            Olist.setCellFactory(param -> new ORequestClick.Cell());
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
            PreparedStatement getData = conn.prepareStatement("SELECT * FROM dealer_table");
            ResultSet result = getData.executeQuery();
            if(result.next()){

            }
            return conn;
        }catch (Exception e){System.out.println(e); }

        return null;
    }
}
