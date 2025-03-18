package com.expensetracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@SpringBootApplication
@RestController
@RequestMapping("/expenses")
public class ExpenseTrackerApplication {
    private final List<Expense> expenses = new ArrayList<>();

    public static void main(String[] args) {
        SpringApplication.run(ExpenseTrackerApplication.class, args);
    }

    @GetMapping
    public List<Expense> getExpenses() {
        return expenses;
    }

    @PostMapping
    public Expense addExpense(@RequestBody Expense expense) {
        expenses.add(expense);
        return expense;
    }

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable int id) {
        expenses.removeIf(expense -> expense.getId() == id);
    }
}

class Expense {
    private static int counter = 1;
    private int id;
    private String name;
    private double amount;
    private String date;

    public Expense(String name, double amount, String date) {
        this.id = counter++;
        this.name = name;
        this.amount = amount;
        this.date = date;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getAmount() { return amount; }
    public String getDate() { return date; }
}

