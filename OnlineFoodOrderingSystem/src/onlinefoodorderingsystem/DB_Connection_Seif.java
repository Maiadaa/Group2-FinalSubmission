/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinefoodorderingsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author mostafa gado
 */
public class DB_Connection_Seif {

    private final String userName = "root";
    private final String password = "";
    private final String dbName = "food_ordering_system";

    private Connection con;

    public DB_Connection_Seif() {
        try {
            //Loading the jdbc driver
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            //Get a connection to database
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, userName, password);
        } catch (Exception e) {
            System.err.println("DATABASE CONNECTION ERROR: " + e.toString());
        }
    }

    public void Add_To_Cart(Order_Item item) {
            try {
                Statement stmt = con.createStatement();
                stmt.executeUpdate("insert into order_item values('" + item.getItem().getItem_Id() + "', " + item.getItem().getItem_Id() + "', " + item.getItem().getItem_Id() +"'," + item.getItem_Quantity() +"'," + item.getItem_Total_Price()+ ")");
            } catch (Exception e) {
                System.err.println("DATABASE INSERTION ERROR: " + e.toString());
            }
        }


    public void Modify_Cart_Item(int id, int amount,int sum) {
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate("UPDATE `order_item` SET `Quantity`='" + amount + "',`Total_ItemPrice`='" + sum + "' where MenuItem_ID = " + id + "");
        } catch (Exception e) {
            System.err.println("DATABASE INSERTION ERROR: " + e.toString());
        }
    }

    public void Remove_From_Cart(int id) {
            try {
                Statement stmt = con.createStatement();
                stmt.executeUpdate("delete from order_item where OrderItem_ID = '" + id + "'");
            } catch (Exception e) {
                System.err.println("DATABASE INSERTION ERROR: " + e.toString());
            }
        }
//    public void Redeem_Coupon(Coupon c){
//        try {
//                Statement stmt = con.createStatement();
//                stmt.executeUpdate("delete from order_item where OrderItem_ID = '" + id + "'");
//            } catch (Exception e) {
//                System.err.println("DATABASE INSERTION ERROR: " + e.toString());
//            }
//    }

    public JTable displayCartItems(JTable tbl,Order o) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select `Quantity`, `Total_ItemPrice`,`Item_Name`,`order_item`.`MenuItem_ID` from order_item , menu_item where Order_ID = '" + o.getOrder_Id() +"'");

            DefaultTableModel model;
            model = (DefaultTableModel) tbl.getModel();
            Object rowData[] = new Object[4];

            while (rs.next()) {
                rowData[0] = rs.getInt("MenuItem_ID");
                rowData[1] = rs.getString("Item_Name");
                rowData[2] = rs.getInt("Quantity");
                rowData[3] = rs.getInt("Total_ItemPrice");
                
                model.addRow(rowData);
            }
        } catch (Exception e) {
            System.err.println("DATABASE DISPLAY CART ITEMS QUERY ERROR: " + e.toString());
        }
        return tbl;
    }
    
   

    /*
    public JTable viewOrderHistory(JTable jTable1) {
        try {
            String add = this.getAddress();
            ResultSet rs = null;

            String sql2 = "SELECT * FROM ORDERDETAILS";
            rs = stcat.executeQuery(sql2);

            DefaultTableModel model;
            model = (DefaultTableModel) jTable1.getModel();
            Object rowData[] = new Object[4];

            while (rs.next()) {
                rowData[0] = rs.getInt("PROD_ID");
                rowData[1] = rs.getDouble("PRICE");
                rowData[2] = rs.getString("STATUS");
                rowData[3] = add;

                model.addRow(rowData);
            }
            rs.close();
            stcat.close();
            conncat.close();

        } catch (SQLException ex) {
            System.out.println("werrrrrrrrrr");
        }

        return jTable1;
    }
 
     */
}
