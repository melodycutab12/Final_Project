/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;
import Controller.controller;
import model.Rental_Management;
/**
 *
 * @author User
 */
public class Main {
    public static void main(String[] args) {
        
      
        java.awt.EventQueue.invokeLater(() -> {
            MainForm form = new MainForm();
            new controller(form, new Rental_Management());
            form.setLocationRelativeTo(null);
            form.setVisible(true);
        });
    }
}
 
