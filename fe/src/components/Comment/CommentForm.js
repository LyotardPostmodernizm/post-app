import React, { useState } from "react";
import { Link } from "react-router-dom";
import { CardContent, InputAdornment, OutlinedInput, Avatar } from "@mui/material";
import Button from '@mui/material/Button';

function CommentForm(props) {
    const {userId, userName ,postId } = props;

    const handleSubmit = () => {
        postComment();
        setText("");
    }
    const [text,setText] = useState("");
    
    const postComment = () => {
        fetch("/comments", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
        
        body: JSON.stringify({
            postId:postId,
            userId:userId,
            text:text
        })
    })
        .then(res =>res.json())
        .catch((err) => console.log(err))
    }
    const handleChange = () => {
        setText(value);
    }


    return (
        <CardContent
            sx={{
                display: "flex",
                flexWrap: "wrap",
                justifyContent: "flex-start",
                alignItems: "center",
            }}
        >
            <OutlinedInput
                
                id="outlined-adornment-amount"
                multiline
                inputProps={{ maxLength: 250 }}
                fullWidth
                onChange={(i) => handleChange(i.target.value)}
                startAdornment={
                    <InputAdornment position="start">
                        <Link to={`/users/${userId}`} style={{ textDecoration: "none", boxShadow: "none", color: "white" }}>
                            <Avatar
                                aria-label="recipe"
                                sx={{
                                    width: 32, 
                                    height: 32,
                                    bgcolor: 'primary.main', 
                                }}
                            >
                                {userName.charAt(0).toUpperCase()}
                            </Avatar>
                        </Link>
                    </InputAdornment>
                }
                endAdornment = {
                    <InputAdornment position="end">
                        <Button variant='contained'
                                    color='primary'
                                    onClick={handleSubmit}>
                                    Comment
                                </Button>
                    </InputAdornment>
                }
                value={text}
                sx={{ color: "black", backgroundColor: "white" }}
            />
        </CardContent>
    );
}

export default CommentForm;
