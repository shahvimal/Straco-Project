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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProductClick {
    private int countProductsClick=0;
    private ListView<productDetail > list;

    ProductClick(int count,ListView<productDetail > list1){
        list=list1;
        countProductsClick=count;

        listFilling();
    }

    static class Cell extends ListCell<productDetail> {
        AnchorPane anchorPane= new AnchorPane();

        productDetail tempProduct ;





        JFXButton addToRequest = new JFXButton("Add");
        HBox hBox = new HBox();

        VBox vLabel  = new VBox();

        Label price = new Label();
        Label productName = new Label();
        Label comment = new Label();

        ImageView productImage = new ImageView();


        public Cell()
        {
            super();
            productName.setFont(Font.font(16));
            price.setFont(Font.font(16));
            comment.setFont(Font.font(16));
            comment.setVisible(false);

            productImage.setFitHeight(200);
            productImage.setFitWidth(265);
            productImage.setPreserveRatio(true);
            productImage.setSmooth(true);
            productImage.setCache(true);


            addToRequest.setFont(Font.font(14));
            addToRequest.setBackground(Background.EMPTY);
            String style = "-fx-background-color: rgba(33, 150, 243, 1);";
            addToRequest.setStyle(style);

            addToRequest.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    tempProduct=getItem();
                    int i=add(tempProduct);
                    if(i==0){
                        comment.setText("Not added");
                        comment.setVisible(true);}
                    else if(i==1)
                    {
                        comment.setText("Added");
                        comment.setVisible(true);
                    }
                }
            });
            vLabel.getChildren().addAll(productName,price,addToRequest,comment);
            hBox.getChildren().addAll(productImage,vLabel);


        }

        public void updateItem(productDetail product,boolean empty){

            super.updateItem(product,empty);
            setText(null);
            setGraphic(null);
            if ((product!=null)&& !empty){
                productName.setText(product.getmProductName());
                productImage.setImage(product.getmImage());
                price.setText("PRICE    Rs. "+ product.getmPrice());

                setGraphic(hBox);
            }
        }

        int add(productDetail tempProduct){
            Connection connection;
            try {
                connection =getConnection();
                PreparedStatement ps = connection.prepareStatement("INSERT INTO request_table (productNumber,count,dealerId,status) VALUES ('" + tempProduct.getMproductNumber()+ "','" + 100 + "','" + Main.dealerId + "','" + 0 + "')");
                ps.executeUpdate();
                return 1;
            } catch (Exception e) {
                e.printStackTrace(); }
                return 0;
        }

    }

    public void listFilling() {

        if (countProductsClick ==0) {
            list.getItems().clear();
            try {
                Connection con = getConnection();
                PreparedStatement getData = con.prepareStatement("SELECT * FROM product_table");
                ResultSet result = getData.executeQuery();

                while(result.next()){

                    InputStream is=result.getBinaryStream("productImage");
                    OutputStream os = new FileOutputStream(new File("photo.jpg"));
                    byte [] content = new byte[1024];
                    int size=0;
                    while((size=is.read(content))!=-1){
                        os.write(content,0,size);
                    }
                    os.close();
                    is.close();


                    list.getItems().add(new productDetail(new Image("file:photo.jpg")  , String.valueOf(result.getString("productName")), result.getString("price"),result.getString("product_number"),0,0));
                }

            }catch (Exception e){System.out.println(e);}
            list.setCellFactory(param -> new ProductClick.Cell());
        }
    }
    static Connection getConnection() throws Exception{

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
