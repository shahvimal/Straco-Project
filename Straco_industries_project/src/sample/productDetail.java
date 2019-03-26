package sample;

import javafx.scene.image.Image;

public class productDetail  {



    private  int mPaymentsPnumber;
    private Image mImage;
    private String mProductName;
    private String mPrice;
    private String mproductNumber;



    private Image mdealerImage;
    private String mDealerName;
    private String mDealerAddress;



    private Image mpaymentImage;
    private String mPaymentsLeft;
    private String mPaymentsDone;
    private String mPhoneNumber;



    private Image mRequestedImage;
    private String mrequestedPname;
    private String mPayments;
    private String mstatus;
    private int mrequestedPcount;

    private int mProductNumber;

    productDetail(Image RequestedImage, String requestedPname,String price ,String status,int Pcount){
        mRequestedImage=RequestedImage;
        mrequestedPname=requestedPname;
        mrequestedPcount=Pcount;
        if(status.equals(0))
            mstatus="Pending";
        else
                mstatus="Accepted";
        mPayments=String.valueOf(mrequestedPcount*Integer.parseInt(price));
    }

    productDetail(Image pay, String PaymentsLeft,String PaymentsDone ,int ProductNumber ){
        mpaymentImage=pay;
        mPaymentsDone=PaymentsDone;
        mPaymentsLeft=PaymentsLeft;
        mPaymentsPnumber = ProductNumber;
    }

    productDetail(Image Dealerimage, String DealerName , String phoneNumber,String address){
        mDealerName=DealerName;
        mdealerImage=Dealerimage;
        mPhoneNumber=phoneNumber;
        mDealerAddress=address;

    }
    productDetail(Image image, String productName , String price,String mProductNumber,int i,int j){
        mImage=image;
        mPrice=price;
        mProductName=productName;
        mproductNumber=mProductNumber;
    }

    public Image getmImage() {
        return mImage;
    }
    public String getmProductName() {
        return mProductName;
    }
    public String getmPrice() {
        return mPrice;
    }

    public Image getMdealerImage() {
        return mdealerImage;
    }
    public String getmDealerName() {
        return mDealerName;
    }
    public String getmDealerAddress() {
        return mDealerAddress;
    }
    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public Image getMpaymentImage() {
        return mpaymentImage;
    }
    public String getmPaymentsLeft() {
        return mPaymentsLeft;
    }
    public String getmPaymentsDone() {
        return mPaymentsDone;
    }
    public int  getmProductNumber() {
        return mProductNumber;
    }

    public Image getmRequestedImage() {
        return mRequestedImage;
    }
    public String getMrequestedPname() {
        return mrequestedPname;
    }
    public String getmPayments() {
        return mPayments;
    }
    public String getMstatus() {
        return mstatus;
    }
    public int getMrequestedPcount() {
        return mrequestedPcount;
    }
    public int getmPaymentsPnumber() {
        return mPaymentsPnumber;
    }

    public String getMproductNumber() {
        return mproductNumber;
    }
}
