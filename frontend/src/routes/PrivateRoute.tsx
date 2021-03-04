import React from "react";
import {useSelector} from "react-redux";
import {Redirect, Route, RouteProps} from "react-router";

import {Main} from "../pages";
import {isLoggedIn} from "../store/ducks/Auth";
import {AppState} from "../store/ducks";
import Container from "../components/container/Container";

interface PrivateRouteProps extends RouteProps {
    component: any;
}

const PrivateRoute = ({ component: Component, ...rest }: PrivateRouteProps) => {
    const isAuthenticated = useSelector((state: AppState) => isLoggedIn(state))

    return (
        <Route
            {...rest}
            render={(routeProps) =>
                isAuthenticated ? (
                    <Container>
                        <Component {...routeProps} />
                    </Container>
                ) : (
                    <Redirect
                        to="/login"
                    />
                )
            }
        />
    );
};

export default PrivateRoute;