import React, { useState, useEffect } from "react";
import { Container, TextField, Button, Table, TableBody, TableCell, TableHead, TableRow } from "@mui/material";
import axios from "axios";

const ExpenseTracker = () => {
    const [expenses, setExpenses] = useState([]);
    const [name, setName] = useState("");
    const [amount, setAmount] = useState("");
    const [date, setDate] = useState("");

    useEffect(() => {
        fetchExpenses();
    }, []);

    const fetchExpenses = async () => {
        const response = await axios.get("http://localhost:8080/expenses");
        setExpenses(response.data);
    };

    const addExpense = async () => {
        const newExpense = { name, amount: parseFloat(amount), date };
        await axios.post("http://localhost:8080/expenses", newExpense);
        fetchExpenses();
        setName("");
        setAmount("");
        setDate("");
    };

    const deleteExpense = async (id) => {
        await axios.delete(`http://localhost:8080/expenses/${id}`);
        fetchExpenses();
    };

    return (
        <Container>
            <h2>Expense Tracker</h2>
            <TextField label="Name" value={name} onChange={(e) => setName(e.target.value)} />
            <TextField label="Amount" type="number" value={amount} onChange={(e) => setAmount(e.target.value)} />
            <TextField label="Date" type="date" InputLabelProps={{ shrink: true }} value={date} onChange={(e) => setDate(e.target.value)} />
            <Button variant="contained" onClick={addExpense} style={{ marginTop: "10px" }}>Add Expense</Button>
            
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>Name</TableCell>
                        <TableCell>Amount</TableCell>
                        <TableCell>Date</TableCell>
                        <TableCell>Action</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {expenses.map((expense) => (
                        <TableRow key={expense.id}>
                            <TableCell>{expense.name}</TableCell>
                            <TableCell>${expense.amount.toFixed(2)}</TableCell>
                            <TableCell>{expense.date}</TableCell>
                            <TableCell>
                                <Button variant="contained" color="secondary" onClick={() => deleteExpense(expense.id)}>
                                    Delete
                                </Button>
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </Container>
    );
};

export default ExpenseTracker;
