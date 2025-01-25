import React, { useState } from 'react';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Collapse from '@mui/material/Collapse';
import Avatar from '@mui/material/Avatar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import { red } from '@mui/material/colors';
import FavoriteIcon from '@mui/icons-material/Favorite';
import { styled } from '@mui/material/styles';
import AddCommentIcon from '@mui/icons-material/AddComment';
import { useNavigate } from 'react-router-dom';
import OutlinedInput from '@mui/material/OutlinedInput';
import { Button, InputAdornment } from '@mui/material';
import Snackbar from '@mui/material/Snackbar';



function PostForm(props) {
    const { userId, userName, refreshPosts } = props;
    const navigate = useNavigate();
    const [text, setText] = useState("");
    const [title, setTitle] = useState("");
    const [isSent, setIsSent] = useState(false);

    const savePost = () => {
        fetch("/posts",
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    title: title,
                    userId: userId,
                    text: text
                })
            }
        )
            .then(res => res.json())
            .catch(err => console.log("error"))
    }

    const handleAvatarClick = () => {
        navigate(`/users/${userId}`);
    };



    const handleTitle = (value) => {
        setTitle(value);
        setIsSent(false);
    }

    const handleText = (value) => {
        setText(value);
        setIsSent(false);
    }

    const handleSend = () => {
        savePost();
        setIsSent(true);
        setTitle("");
        setText("");
        refreshPosts();
    }
    const handleClose = (event, reason) => {
        if (reason === 'clickaway') {
            return;
        }

        setIsSent(false);
    };

    return (
        <div>
            <Snackbar
                open={isSent}
                autoHideDuration={2000}
                onClose={handleClose}
                message="Your post is successfully sent"
            />
            <Card sx={{ width: 800, textAlign: 'left', marginBottom: 2, bgcolor: 'white' }}>
                <CardHeader
                    avatar={
                        <Avatar
                            sx={{ bgcolor: red[500], cursor: 'pointer' }}
                            aria-label="recipe"
                            onClick={handleAvatarClick}
                        >
                            {userName.charAt(0).toUpperCase()}
                        </Avatar>
                    }
                    title={<OutlinedInput
                        id="outlined-adornment-amount"
                        multiline
                        placeholder='Title'
                        inputProps={{ maxLength: 50 }}
                        fullWidth
                        value={title}
                        onChange={(input) => handleTitle(input.target.value)}>

                    </OutlinedInput>}
                />

                <CardContent>
                    <Typography variant="body2" sx={{ color: 'text.secondary' }}>
                        Text here...
                    </Typography>

                    <OutlinedInput
                        id="outlined-adornment-amount"
                        multiline
                        placeholder='Text'
                        inputProps={{ maxLength: 250 }}
                        fullWidth
                        value={text}
                        onChange={(i) => handleText(i.target.value)}
                        endAdornment={
                            <InputAdornment position='end'>
                                <Button variant='contained'
                                    color='primary'
                                    onClick={handleSend}>
                                    Post
                                </Button>
                            </InputAdornment>
                        }
                    />
                </CardContent>
            </Card>
        </div>
    );
}

export default PostForm;
