import React from "react";
import {Redirect, Switch} from "react-router";
import PrivateRoute from "./PrivateRoute";
import { HomePage, LoginPage } from "../pages";
import SimpleRoute from "./SimpleRoute";

const Routes = () => (
    <Switch>
        <PrivateRoute exact path="/" component={HomePage} />
        <SimpleRoute path="/login" component={LoginPage} />
        <Redirect from="*" to="/" />
    </Switch>
);

export default Routes;

