import React, { useState, useEffect } from "react";
import Post from "../Post/Post";
import PostForm from "../Post/PostForm";
import CssBaseline from '@mui/material/CssBaseline';
import Box from '@mui/material/Box';
import Container from '@mui/material/Container';

function Home() {
    const [error, setError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [postList, setPostList] = useState([]);
    const refreshPosts = () => {
        fetch("/posts")
            .then(res => res.json())
            .then(
                (result) => {
                    setIsLoaded(true);
                    setPostList(result);
                },
                (error) => {
                    setIsLoaded(true);
                    setError(error);
                }
            );
    }

    useEffect(() => {
        refreshPosts()
    }, []);  

    if (error) {
        return <div>Error!</div>;
    } else if (!isLoaded) {
        return <div>Loading ...</div>;
    } else {
        return (
            <Container maxWidth={false} sx={{ padding: 0 }}> 
                <Box 
                    sx={{ 
                        display: "flex", 
                        flexDirection: "column", 
                        alignItems: "center", 
                        gap: 2,
                        backgroundColor:'#f0f5ff',
                        minHeight: '100vh' 
                    }}
                >
                    <PostForm userId={localStorage.getItem("currentUser")} userName={localStorage.getItem("userName")} refreshPosts = {refreshPosts}/>
                    {postList.map(post => (
                        <Post 
                            key={post.id}
                            likes={post.postLikes}
                            postId={post.id} 
                            userId={post.userId} 
                            userName={post.userName} 
                            title={post.title} 
                            text={post.text} 
                        />
                    ))}
                </Box>
            </Container>
        );
    }
}
export default Home;
