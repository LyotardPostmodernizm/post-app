import React, { useState, useRef, useEffect } from 'react';
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
import { Container } from '@mui/material';
import CommentForm from '../Comment/CommentForm';
import Comment from '../Comment/Comment';


const ExpandMore = styled((props) => {
    const { expand, ...other } = props;
    return <IconButton {...other} />;
})(({ theme, expand }) => ({
    transform: !expand ? 'rotate(0deg)' : 'rotate(0deg)', 
    marginLeft: 'auto',
    transition: theme.transitions.create('transform', {
        duration: theme.transitions.duration.shortest,
    }),
}));

function Post(props) {
    const { title, text, userId, userName, postId, likes} = props;
    const [expanded, setExpanded] = useState(false);
    const navigate = useNavigate(); 
    const [error, setError] = useState(null);
    const [commentList, setCommentList] = useState([]);
    const [isLiked, setIsLiked] = useState(false);
    const [isLoaded,setIsLoaded] = useState(false);
    const [likeCount, setLikeCount] = useState(likes ? likes.length : 0);
    const [likeId, setLikeId] = useState(null);

    const handleExpandClick = () => {
        setExpanded(!expanded); 
        LoadComments(); //Comment ikonuna basıldığında commentlerin yüklenmesini istiyoruz.
        console.log(commentList);
    };

    const handleAvatarClick = () => {
        navigate(`/users/${userId}`); 
    };

    const handleFavourite = () => {
        if (isLiked) {
            deleteLike();
            setLikeCount((prevCount) => prevCount - 1);
        } else {
            postLikeToDb();
            setLikeCount((prevCount) => prevCount + 1);
        }
        setIsLiked(!isLiked);
    };

    const LoadComments = () => {
        fetch("/comments?postId="+postId)
            .then(res => res.json())
            .then(
                (result) => {
                    setIsLoaded(true);
                    setCommentList(result);
                },
                (error) => {
                    setIsLoaded(true);
                    setError(error);
                }
            );
    }
    const checkLikes = () => {
        if (likes && Array.isArray(likes)) {  // likes'in varlığını ve bir dizi olup olmadığını kontrol ediyoruz
            const alreadyLike = likes.find(like => like.userId === userId); 
            if (alreadyLike != null) {
                setLikeId(alreadyLike.id);
                setIsLiked(true);
            }
        }
    }

    const postLikeToDb = () => {
        fetch("/likes", {
            method: "POST",
            headers: {
                "Content-type": "application/json"
            },
            body: JSON.stringify({
                postId: postId,
                userId: userId
            })
        })
        .then(res => res.json())
        .then(data => {
            setLikeId(data.id);  // Yeni beğeni ID'sini set ediyoruz
        })
        .catch(err => console.log(err));
    }
    
const deleteLike = () => {
    if (!likeId) return;  // likeId null ise işlemi iptal ediyoruz
    
    fetch("/likes/" + likeId, {
        method: "DELETE",
    })
    .then(() => setLikeId(null))  // Silme başarılı olursa likeId’yi sıfırlıyoruz
    .catch(err => console.log(err));
}

   
    useEffect(() => {
        checkLikes();
    }, []);

    useEffect(() => {
        if (expanded) {  // expanded true olduğunda commentler yüklenecek. Yani sayfa her render edildiğinde değil, state değiştiğinde
            LoadComments();
        }
    }, [expanded]);

    return (
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
                title={title}
            />
          
            <CardContent>
                <Typography variant="body2" sx={{ color: 'text.secondary' }}>
                    {text}
                </Typography>
            </CardContent>
            <CardActions disableSpacing>
                <IconButton aria-label="add to favorites" onClick={handleFavourite}>
                    
                    <FavoriteIcon style={isLiked? {color:"red"}:null}/>
                    
                </IconButton>
                {likeCount}
                <ExpandMore
                    expand={expanded}
                    onClick={handleExpandClick}
                    aria-expanded={expanded}
                    aria-label="show more"
                >
                    <AddCommentIcon/>
                </ExpandMore>
            </CardActions>
            <Collapse in={expanded} timeout="auto" unmountOnExit>
            <Container >
                    {error? "error" :
                    isLoaded? commentList.map(comment => (
                      <Comment userId = {1} userName = {"USER"} text = {comment.text}></Comment>
                    )) : "Loading"}
                    <CommentForm userId = {1} userName = {"USER"} postId = {postId}></CommentForm>
                    </Container>
            </Collapse>
        </Card>
    );
}

export default Post;
