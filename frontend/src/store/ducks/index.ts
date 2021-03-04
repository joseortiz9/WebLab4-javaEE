import { combineReducers } from "redux";
import auth from './Auth';
import points from './Points';

const rootReducer = combineReducers({
    auth,
    points
});

export type AppState = ReturnType<typeof rootReducer>

export default rootReducer;