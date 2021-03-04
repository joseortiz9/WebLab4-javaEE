import React from "react";
import {Route} from "react-router";

import Container from "../components/container/Container";

const SimpleRoute = ({ component: Component, ...rest }: any) => {
    return (
        <Route
            {...rest}
            render={(routeProps) =>
                <Container>
                    <Component {...routeProps} />
                </Container>
            }
        />
    );
};

export default SimpleRoute;