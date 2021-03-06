/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinefoodorderingsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author mostafa gado
 */
public class DB_Connection_Maiada {

    private final String userName = "root";
    private final String password = "";
    private final String dbName = "food_ordering_system";

    private Connection con;

    public DB_Connection_Maiada() {
        try {
            //Loading the jdbc driver
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            //Get a connection to database
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, userName, password);
        } catch (Exception e) {
            System.err.println("DATABASE CONNECTION ERROR: " + e.toString());
        }
    }

    // done
    public void addRestAdmin(Restaurant_Admin ra) {
        // add restaurant admin to db 
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate("INSERT INTO rest_admin (`Username`, `Password`)"
                    + "VALUES ('" + ra.getUsername() + "', '" + ra.getPassword() + "')");
            System.out.println("Restaurant Admin added");
        } catch (Exception e) {
            System.err.println("RESTAURANT ADMIN INSERTION ERROR: " + e.toString());
        }
    }

    // done
    public ArrayList<Customer> getAllCusts() {

        try {
            ArrayList<Customer> result = new ArrayList();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from customer");
            while (rs.next()) {
                result.add(new Customer(rs.getInt("ID")));
            }
            return result;
        } catch (Exception e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        return null;
    }

    // done
    public void notifyNewRestsObservers(Restaurant rest) {
        // Upon addition of a new restaurant notify all registered customers 
        // 1st get all customers 
        ArrayList<Customer> custs = this.getAllCusts();

        // 2nd add to newRestsNotifs table a notification message for each cust in the array we ve just retreived 
        String msg = "We want to notify you that " + rest.getRest_Name() + " is now available on our application! \n Located at " + rest.getRest_Location()
                + ".\n The restaurant is known for its " + rest.getRest_Categ() + ".\n Be the first to try it out and rate!";
        for (Customer c : custs) {
            try {
                Statement stmt = con.createStatement();
                stmt.executeUpdate("insert into newrestnotif (`msg`, `Cust_ID`, `Rest_ID`) values('" + msg + "', " + c.getID() + ", '" + rest.getRest_Id() + "')");
                System.out.println("Notification added successfully");
            } catch (Exception e) {
                System.err.println("DATABASE NOTIFICATION INSERTION ERROR: " + e.toString());
            }
        }
    }

    // done
    public void addRest(Restaurant rest, String uname) {
        int id = 0;
        // get id of the recently added rest admin's login credentials to fill restaurant's FK with it
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select ID from rest_admin where Username = '" + uname + "'");
            if (rs.first()) {
                id = rs.getInt("ID");
            }
        } catch (Exception e) {
            System.err.println("DATABASE ADD RESTAURANT QUERY ERROR: " + e.toString());
        }

        // add the restaurant to db
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate("INSERT INTO restaurant (`Rest_Name`, `RestAdmin_ID`, `Rest_Loc`, `Rest_Categ`)"
                    + "VALUES ('" + rest.getRest_Name() + "', '" + id + "', '" + rest.getRest_Location() + "', '" + rest.getRest_Categ() + "')");
            System.out.println("Restaurant added");
        } catch (Exception e) {
            System.err.println("RESTAURANT INSERTION ERROR: " + e.toString());
        }

        try {
            Statement stmt = con.createStatement();
            ResultSet rss = stmt.executeQuery("select * from restaurant where `RestAdmin_ID` = " + id + "");
            if (rss.first()) {
                rest.setRest_Id(rss.getInt("Rest_ID"));
            }
            System.out.println("Restaurant id retreives successfully");
        } catch (Exception e) {
            System.err.println("RESTAURANT INSERTION ERROR: " + e.toString());
        }

    }

    public JTable displayRests(JTable tbl) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from restaurant, rest_admin where restaurant.RestAdmin_ID = rest_admin.ID");

            DefaultTableModel model;
            model = (DefaultTableModel) tbl.getModel();
            Object rowData[] = new Object[4];

            while (rs.next()) {
                rowData[0] = rs.getInt("Rest_ID");
                rowData[1] = rs.getString("Rest_Name");
                rowData[2] = rs.getInt("ID");
                rowData[3] = rs.getString("Name");

                model.addRow(rowData);
            }
        } catch (Exception e) {
            System.err.println("DATABASE DISPLAY CART ITEMS QUERY ERROR: " + e.toString());
        }
        return tbl;
    }

    //done
    public void deleteRestAdmin(int id) {
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate("delete from rest_admin where ID = '" + id + "'");
            System.out.println("Restaurant Admin deleted");

        } catch (Exception e) {
            System.err.println("DATABASE RESTAURANT ADMIN DELETION ERROR: " + e.toString());
        }
    }

    //done
    public void deleteRest(int id) {
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate("delete from restaurant where Rest_ID = '" + id + "'");
            System.out.println("Restaurant deleted");

        } catch (Exception e) {
            System.err.println("DATABASE RESTAURANT DELETION ERROR: " + e.toString());
        }
    }

    public JTable displayNotifs(JTable tbl, Customer c) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `newrestnotif`, restaurant WHERE restaurant.Rest_ID = newrestnotif.Rest_ID and Cust_ID = '" + c.getID() + "'");

            DefaultTableModel model;
            model = (DefaultTableModel) tbl.getModel();
            Object rowData[] = new Object[2];

            while (rs.next()) {
                rowData[0] = rs.getString("Rest_Name");
                rowData[1] = rs.getString("msg");

                model.addRow(rowData);
            }
            System.out.println("Notifications displayed successfully.");

        } catch (Exception e) {
            System.err.println("DATABASE DISPLAY CART ITEMS QUERY ERROR: " + e.toString());
        }
        return tbl;
    }

    //done
    public Restaurant getRestByName(String restName) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select Rest_ID from restaurant where Rest_Name = '" + restName + "'");
            if (rs.first()) {
                return new Restaurant(rs.getInt("Rest_ID"));
            }
        } catch (Exception e) {
            System.err.println("DATABASE GET REST BY NAME QUERY ERROR: " + e.toString());
        }
        return null;
    }

    public boolean Edit_Rest_Details(Restaurant rest) {
        try {
            Statement stmt = con.createStatement();

            int res = 0;
            if (rest.isRest_Open()) {
                res = 1;
            } else {
                res = 0;
            }
            stmt.executeUpdate("UPDATE `restaurant` SET `Rest_Loc`='" + rest.getRest_Location()
                    + "',`Rest_Categ`='" + rest.getRest_Categ() + "',`Rest_Name`='" + rest.getRest_Name() + "',`Rest_Open`='" + res + "' where Rest_ID = " + rest.getRest_Id() + "");
            System.out.println("Restaurant details Updated");
            return true;
        } catch (Exception e) {
            System.err.println("DATABASE RESTAURANT EDIT UPDATE ERROR: " + e.toString());
            return false;
        }
    }

    public Restaurant SelectRestData(int restAdminID) {
        try {
            Statement stmt = con.createStatement();
            ResultSet d = stmt.executeQuery("SELECT * FROM `restaurant` WHERE RestAdmin_ID = '" + restAdminID + "'");
            if (d.first()) {
                boolean res = false;
                if (d.getByte("Rest_Open") == 1) {
                    res = true;
                } else {
                    res = false;
                }
                return new Restaurant(d.getInt("Rest_ID"), d.getString("Rest_Loc"), d.getString("Rest_Categ"), d.getString("Rest_Name"), res);
            } else {
                return null;
            }
        } catch (Exception e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
            return null;
        }
    }

    // done
    public JTable displaySalesTable(JTable tbl) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select restaurant.Rest_Name, COUNT(`order`.Order_ID), SUM(`order`.Order_Price) from restaurant, `order` where restaurant.Rest_ID = `order`.Rest_ID GROUP BY restaurant.Rest_Name;");

            DefaultTableModel model;
            model = (DefaultTableModel) tbl.getModel();
            Object rowData[] = new Object[3];

            while (rs.next()) {
                rowData[0] = rs.getString("restaurant.Rest_Name");
                rowData[1] = rs.getInt("COUNT(`order`.Order_ID)");
                rowData[2] = rs.getDouble("SUM(`order`.Order_Price)");

                model.addRow(rowData);
            }
            System.out.println("Sales Report displayed successfully.");

        } catch (Exception e) {
            System.err.println("DATABASE DISPLAY SALES REPORT QUERY ERROR: " + e.toString());
        }
        return tbl;
    }
}
