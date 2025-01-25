import React from "react";
import { Link, useNavigate } from "react-router-dom";
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import { LockOpen } from '@mui/icons-material';

function Navbar() {
    const navigate = useNavigate();

    const onClick = () => {
        localStorage.removeItem("tokenKey");
        localStorage.removeItem("currentUser");
        localStorage.removeItem("refreshKey");
        localStorage.removeItem("userName");
        navigate(0); // Sayfayı yenilemek için
    }

    return (
        <div>
            <AppBar position="static">
                <Toolbar>
                    <IconButton edge="start" color="inherit" aria-label="menu" sx={{ mr: 2 }}>
                        <MenuIcon />
                    </IconButton>
                    <Typography variant="h6" sx={{ flexGrow: 1, textAlign: "left" }}>
                        <Link to="/" style={{ textDecoration: "none", color: "white" }}>
                            Home
                        </Link>
                    </Typography>
                    <Typography variant="h6">
                        {localStorage.getItem("currentUser") == null ? (
                            <Link to="/auth" style={{ textDecoration: "none", color: "white" }}>
                                Login/Register
                            </Link>
                        ) : (
                            <div>
                                <IconButton onClick={onClick} style={{ color: "white" }}>
                                    <LockOpen />
                                </IconButton>
                                <Link
                                    to={`/users/${localStorage.getItem("currentUser")}`}
                                    style={{ textDecoration: "none", color: "white" }}
                                >
                                    Profile
                                </Link>
                            </div>
                        )}
                    </Typography>
                </Toolbar>
            </AppBar>
        </div>
    );
}

export default Navbar;
