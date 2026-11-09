/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Rental;
import model.Rental_Management;
import View.MainForm;
/**
 *
 * @author User
 */
public class controller {
    private final MainForm view;
    private final Rental_Management model;
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public controller(MainForm view, Rental_Management model) {
        this.view = view;
        this.model = model;
        init();
    }

    private void init() {
        // Item and Size combo boxes
        view.getCmbItem().setModel(new DefaultComboBoxModel<>(model.getItems()));
        view.getCmbSize().setModel(new DefaultComboBoxModel<>(model.getSizes()));

        // Rate: filled automatically from the selected item, not typed by the user
        view.getTxtRate().setEditable(false);
        updateRate();
        view.getCmbItem().addActionListener(e -> updateRate());

        // Date Rented: user must enter the date manually
        view.getTxtDateRented().setText("");
       
        // Table should not be editable
        view.getTblRentals().setDefaultEditor(Object.class, null);

        // Buttons
        view.getBtnRent().addActionListener(e -> rentItem());
        view.getBtnDelete().addActionListener(e -> deleteSelected());

        refreshTable();
    }

    private void updateRate() {
        String item = (String) view.getCmbItem().getSelectedItem();
        view.getTxtRate().setText(String.format("%.2f", model.getRate(item)));
    }

    // RENT button
    private void rentItem() {
        String item = (String) view.getCmbItem().getSelectedItem();
        String size = (String) view.getCmbSize().getSelectedItem();
        String name = view.getTxtCustomerName().getText().trim();
        String address = view.getTxtAddress().getText().trim();
        String phone = view.getTxtPhone().getText().trim();
        String dateInput = view.getTxtDateRented().getText().trim();

        if (dateInput.equals("Enter date (yyyy-MM-dd)")) {
         error("Please enter the rental date.");
        return;
        }
        
        // Customer Name: required, letters/spaces/dots/hyphens only
        if (name.isEmpty() || !name.matches("[\\p{L} .'-]+")) {
            error("Enter a valid customer name (letters only).");
            return;
        }
        
        // Address: required
        if (address.isEmpty()) {
            error("Address is required.");
            return;
        }
        
       // Phone Number: exactly 11 digits
        if (!phone.matches("\\d{11}")) {
             error("Phone number must be exactly 11 digits.");
    return;
}
        // Day: whole number greater than 0
        int days;
        try {
            days = Integer.parseInt(view.getTxtDay().getText().trim());
            if (days <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            error("Day must be a whole number greater than 0.");
            return;
        }
        // Date Rented: must be yyyy-MM-dd
        LocalDate rented;
        try {
            rented = LocalDate.parse(view.getTxtDateRented().getText().trim(), FMT);
        } catch (DateTimeParseException ex) {
            error("Date Rented must be in yyyy-MM-dd format, e.g. 2026-11-04.");
            return;
        }

        double rate = model.getRate(item);
        model.addRental(new Rental(item, size, name, address, phone, rented, days, rate));
        refreshTable();   // list is re-sorted by date rented on every add
        clearFields();
    }

    // DELETE button
    private void deleteSelected() {
        int row = view.getTblRentals().getSelectedRow();
        if (row < 0) {
            error("Select a row to delete.");
            return;
        }
        int ok = JOptionPane.showConfirmDialog(view, "Delete the selected rental?",
                "Confirm", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) {
            model.deleteRental(row);   // table rows match list order, so row = index
            refreshTable();
        }
    }

    // Rebuilds the table from the (already sorted) list
    private void refreshTable() {
        model.sortByDateRented();
        DefaultTableModel tm = (DefaultTableModel) view.getTblRentals().getModel();
        tm.setRowCount(0);
        for (Rental r : model.getRentals()) {
            tm.addRow(new Object[]{
                r.getItem(), r.getSize(), r.getCustomerName(), r.getAddress(),
                r.getPhone(), r.getRentedDate().format(FMT), r.getDueDate().format(FMT),
                String.format("%.2f", r.getTotal()), r.getStatus()
            });
        }
    }

    private void clearFields() {
        view.getTxtCustomerName().setText("");
        view.getTxtAddress().setText("");
        view.getTxtPhone().setText("");
        view.getTxtDay().setText("");
        view.getTxtDateRented().setText("Enter date (yyyy-MM-dd)");
        view.getCmbItem().setSelectedIndex(0);
        view.getCmbSize().setSelectedIndex(0);
        view.getTxtCustomerName().requestFocus();
    }

    private void error(String msg) {
        JOptionPane.showMessageDialog(view, msg, "Invalid input", JOptionPane.WARNING_MESSAGE);
    }
}

