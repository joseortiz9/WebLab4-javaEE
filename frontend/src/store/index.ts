import {applyMiddleware, createStore} from "redux";
import reducers from './ducks';
import {authStorageSaver} from "./middlewares/authStorageSaver";
import thunk from "redux-thunk";

const store = createStore(
    reducers,
    applyMiddleware(authStorageSaver, thunk)
);

export default store;