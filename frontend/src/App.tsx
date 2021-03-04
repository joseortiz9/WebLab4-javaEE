import React, {useEffect} from "react";
import history from "./routes/history";
import {Router} from "react-router";
import Navbar from "./components/navbar/Navbar";
import Routes from "./routes/Routes";
import "./styles/global.scss"
import {useDispatch, useSelector} from "react-redux";
import {checkSession, isLoggedIn, loggedUser} from "./store/ducks/Auth";
import {AppState} from "./store/ducks";

const App = () => {
    const dispatch = useDispatch();
    const authSession = useSelector((state: AppState) => loggedUser(state));
    const hasSavedToken = useSelector((state: AppState) => isLoggedIn(state));

    useEffect(() => {
        if (hasSavedToken)
            dispatch(checkSession(authSession));
    }, []);

    return (
       <Router history={history}>
           <Navbar />
           <Routes />
       </Router>
    );
}

export default App;