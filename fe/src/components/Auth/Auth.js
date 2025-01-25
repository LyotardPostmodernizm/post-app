import { FormControl, Input, InputLabel, Box, Button, FormHelperText } from "@mui/material";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

function Auth() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    let navigate = useNavigate();

    const handleUsername = (value) => {
        setUsername(value);
    };

    const handlePassword = (value) => {
        setPassword(value);
    };

    const handleButton = (path) => {
        sendRequest(path)
        setUsername("") //username ve passwordü sıfırlamak için
        setPassword("")
        navigate(0)
    }

    const sendRequest = (path) => {
        fetch("/auth/" + path, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                userName: username,
                password: password
            })
        })
        .then((res) => {
            if (!res.ok) {
                throw new Error("Server responded with status " + res.status);
            }
            return res.json();
        })
        .then((result) => {
            localStorage.setItem("tokenKey", result.message);
            localStorage.setItem("currentUser", result.userId);
            localStorage.setItem("userName", username);
        })
        .catch((err) => console.error("Error:", err));  // Hata mesajını daha belirgin hale getirin
    };
    

    return (
        <Box 
            component="form" 
            sx={{ 
                display: "flex", 
                flexDirection: "column", 
                gap: 2, 
                width: 300, 
                margin: "0 auto",
                alignItems: "center"
            }}
        >
            <FormControl fullWidth>
                <InputLabel htmlFor="username">Username</InputLabel>
                <Input 
                    id="username" 
                    onChange={(i) => handleUsername(i.target.value)}
                />
            </FormControl>
            
            <FormControl fullWidth>
                <InputLabel htmlFor="password">Password</InputLabel>
                <Input 
                    id="password" 
                    type="password" 
                    onChange={(i) => handlePassword(i.target.value)}
                />
            </FormControl>

            <Button 
                variant="contained" 
                sx={{ 
                    backgroundColor: "blue", 
                    color: "white", 
                    "&:hover": { backgroundColor: "darkblue" },
                    width: "100%" 
                }}
                onClick={() => handleButton("register")}>Register</Button>

            <FormHelperText>Are you already registered? Please Login.</FormHelperText>
            
            <Button 
                variant="contained" 
                sx={{ 
                    backgroundColor: "blue", 
                    color: "white", 
                    "&:hover": { backgroundColor: "darkblue" },
                    width: "100%" 
                }}
                onClick={() => handleButton("login")}>Login</Button>
        </Box>
    );
}

export default Auth;
