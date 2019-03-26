
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
        import javafx.stage.StageStyle;

        import java.io.*;
        import java.sql.*;

public class PaymentsClick {
    private int countPaymentsClick=0;
    private ListView<productDetail > list;

    PaymentsClick(int count,ListView<productDetail > list1){
        list=list1;
        countPaymentsClick=count;

        listFilling();
    }




    static class Cell extends ListCell<productDetail> {


        productDetail tempProduct ;

        @FXML
        JFXButton productRequest;
        @FXML
        JFXTextField number;
        @FXML
        Label payments_name;
        @FXML
        Label payments_price;

        JFXButton request = new JFXButton("Request");
        HBox hBox = new HBox();
        VBox vLabel  = new VBox();

        Label paymentsLeft = new Label();
        Label paymentsDone = new Label();
        Label paymentDetail = new Label();

        ImageView productImage = new ImageView("img/ic_account_circle_black_48dp.png");

        public Cell()
        {
            super();
            paymentDetail.setFont(Font.font(16));
            paymentsDone.setFont(Font.font(16));
            paymentsLeft.setFont(Font.font(16));


            request.setFont(Font.font(14));
            request.setBackground(Background.EMPTY);
            String style = "-fx-background-color: rgba(33, 150, 243, 1);";
            request.setStyle(style);

            request.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    tempProduct=getItem();
                    productDetail(tempProduct);
                }
            });

            vLabel.getChildren().addAll(paymentDetail,paymentsDone,paymentsLeft,request);
            hBox.getChildren().addAll(productImage,vLabel);

        }
        public void productDetail(productDetail tempProduct){
            JFXButton  Pay= new JFXButton("Pay");
            ImageView productImage = new ImageView();
            Label ProductName = new Label();
            ProductName.setAlignment(Pos.TOP_CENTER);
            ProductName.setFont(Font.font(16));

            productImage.setFitHeight(200);
            productImage.setFitWidth(265);
            productImage.setPreserveRatio(true);
            productImage.setSmooth(true);
            productImage.setCache(true);

            Label payment = new Label();

            payment.setFont(Font.font(16));
            payment.setAlignment(Pos.CENTER);

            Pay.setFont(Font.font(14));
            Pay.setButtonType(JFXButton.ButtonType.RAISED);
            Pay.setBackground(Background.EMPTY);
            String style = "-fx-background-color: rgba(33, 150, 243, 1);";
            Pay.setStyle(style);
            Pay.setLayoutX(132.5);

            VBox vBox = new VBox(ProductName,payment,Pay);
            vBox.setPadding(new Insets(50,50,50,50));
            vBox.setSpacing(10);
            VBox vpayment  = new VBox(productImage,vBox);


            AnchorPane pane = new AnchorPane(vpayment);
            pane.setMaxSize(265,365);
            pane.setBackground(Background.EMPTY);
            Pay.setBackground(Background.EMPTY);
            String style1 = "-fx-background-color: rgba(255, 255, 255, 1);";
            String style2 = "-fx-background-color: rgba(33, 150, 243, 1);";
            Pay.setStyle(style2);
            pane.setStyle(style1);

            Scene scene = new Scene(pane,265,365);


            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            try {
                Connection con = getConnection();
                PreparedStatement getData = con.prepareStatement("SELECT productName,productImage,paymentLeft,dealerId,productNumber FROM payments_table,product_table WHERE payments_table.dealerId ='"+Main.dealerId+"'");
                ResultSet result = getData.executeQuery();

                while (result.next()) {

                    if (result.getInt("productNumber") == tempProduct.getmPaymentsPnumber()) {
                        String newString = "payment"+(result.getInt("productNumber"))+".jpg";
                        InputStream is = result.getBinaryStream("productImage");
                        OutputStream os = new FileOutputStream(new File(newString));

                        byte[] content = new byte[1024];
                        int size = 0;
                        while ((size = is.read(content)) != -1) {
                            os.write(content, 0, size);
                        }
                        os.close();
                        is.close();


                        Image image = new Image(("file:"+newString),275,300,true,true);
                        productImage.setImage(image);
                        ProductName.setText(result.getString("productName"));
                        payment.setText("Amount Payable  Rs."+result.getString("paymentLeft"));
                        stage.setScene(scene);
                        stage.show();
                    }
                }

            }catch (Exception e){System.out.println(e);}

            Pay.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Connection con = null;
                    try {
                        con = getConnection();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    PreparedStatement getData = null;
                    try {
                        getData = con.prepareStatement("UPDATE payments_table SET  paymentLeft='"+null+"' WHERE dealerId ='"+ Main.dealerId+"'" );
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        getData.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Pay click" );
                }
            });
        }
        public void updateItem(productDetail product,boolean empty){

            super.updateItem(product,empty);
            setText(null);
            setGraphic(null);
            if ((product!=null)&& !empty){
                if ((product.getmPaymentsLeft()!=null)&&(product.getmPaymentsDone()!=null))
                    paymentDetail.setText("Payment Details");

                else if((product.getmPaymentsLeft()!=null)&&(product.getmPaymentsDone()==null))
                    paymentDetail.setText("Payment Left");
                else
                    paymentDetail.setText("Payment Done");

                productImage.setImage(product.getMpaymentImage());
                if(product.getmPaymentsDone()!=null)
                    paymentsDone.setText("Payment Done   Rs. "+ product.getmPaymentsDone());
                else
                    paymentsDone.setVisible(false);
                if(product.getmPaymentsLeft()!=null)
                    paymentsLeft.setText("Payment Left  Rs."+product.getmPaymentsLeft());
                else
                    paymentsLeft.setVisible(false);
                setGraphic(hBox);
            }
        }

    }

    public void listFilling() {

        if (countPaymentsClick ==0) {
            list.getItems().clear();

            try {
                Connection con = getConnection();
                PreparedStatement getData = con.prepareStatement("SELECT dealerId,paymentLeft,paymentDone,productNumber FROM payments_table WHERE dealerId ='"+ Main.dealerId+"'");
                ResultSet result = getData.executeQuery();

                while(result.next()){

                    list.getItems().add(new productDetail(new Image("img/payment-method.png")  , result.getString("paymentLeft"),result.getString("paymentDone"),result.getInt("productNumber")));
                }

            }catch (Exception e){System.out.println(e);}

            list.setCellFactory(param -> new PaymentsClick.Cell());
        }

    }
    public  static Connection getConnection() throws Exception{

        try{
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://127.0.0.1:3306/login";
            String username = "root";
            String password = "10@ugusT1965";
            Class.forName(driver);

            Connection conn = DriverManager.getConnection(url,username,password);
            return conn;
        }catch (Exception e){System.out.println(e); }

        return null;
    }
}
