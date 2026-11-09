
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

public class Rental_Management {

    private final List<Rental> rentals = new ArrayList<>();
    private final Map<String, Double> itemRates = new LinkedHashMap<>();
    private final String[] sizes = {"XS", "S", "M", "L", "XL"};

    public Rental_Management() {
        itemRates.put("Gown", 500.0);
        itemRates.put("Suit", 400.0);
        itemRates.put("Barong", 350.0);
        itemRates.put("Dress", 300.0);
        itemRates.put("Jacket", 200.0);
    }

    public String[] getItems() {
        return itemRates.keySet().toArray(new String[0]);
    }

    public String[] getSizes() {
        return sizes;
    }

    public double getRate(String item) {
        return itemRates.getOrDefault(item, 0.0);
    }

    public void addRental(Rental r) {
        rentals.add(r);
        sortByDateRented();
    }

    public void deleteRental(int index) {
        if (index >= 0 && index < rentals.size()) {
            rentals.remove(index);
            sortByDateRented();
        }
    }

    // QUICK SORT: Oldest rented date first
    public void sortByDateRented() {
        if (!rentals.isEmpty()) {
            quickSort(0, rentals.size() - 1);
        }
    }

    private void quickSort(int low, int high) {

        if (low < high) {

            int pivotIndex = partition(low, high);

            quickSort(low, pivotIndex - 1);
            quickSort(pivotIndex + 1, high);
        }
    }

    private int partition(int low, int high) {

        // Select the last rental date as pivot
        java.time.LocalDate pivot =
                rentals.get(high).getRentedDate();

        int i = low - 1;

        for (int j = low; j < high; j++) {

            // Compare rental dates
            if (rentals.get(j).getRentedDate()
                    .compareTo(pivot) <= 0) {

                i++;

                Rental temp = rentals.get(i);
                rentals.set(i, rentals.get(j));
                rentals.set(j, temp);
            }
        }

        // Place pivot in its correct position
        Rental temp = rentals.get(i + 1);
        rentals.set(i + 1, rentals.get(high));
        rentals.set(high, temp);

        return i + 1;
    }

    public List<Rental> getRentals() {
        return rentals;
    }
}
