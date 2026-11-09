/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.time.LocalDate;
/**
 *
 * @author User
 */
public class Rental {

    private final String item, size, customerName, address, phone;
    private final LocalDate rentedDate;
    private final int days;
    private final double rate;

    public Rental(String item, String size, String customerName, String address,
                  String phone, LocalDate rentedDate, int days, double rate) {
        this.item = item;
        this.size = size;
        this.customerName = customerName;
        this.address = address;
        this.phone = phone;
        this.rentedDate = rentedDate;
        this.days = days;
        this.rate = rate;
    }

    public String getItem() { return item; }
    public String getSize() { return size; }
    public String getCustomerName() { return customerName; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public LocalDate getRentedDate() { return rentedDate; }
    public int getDays() { return days; }
    public double getRate() { return rate; }

    // Due date = date rented + number of days
    public LocalDate getDueDate() { return rentedDate.plusDays(days); }

    // Total = days x rate per day
    public double getTotal() { return days * rate; }

    // Overdue once today is past the due date
    public String getStatus() {
        return LocalDate.now().isAfter(getDueDate()) ? "Overdue" : "Rented";
    }
}
    

