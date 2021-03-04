import {IAuthSession} from "../../models/IAuthSession";
import {AppState} from "./index";
import {Dispatch} from "redux";
import {LocalAuthID} from "../../utils/config";
import {api, authApi} from "../../utils/Api";
import {Method} from "axios";
import history from "../../routes/history";


/* ---------------------- Types ------------------------- */

export const AUTH_REQUEST_START = 'users/request-start';
export const AUTH_REQUEST_FAILURE = 'users/request-failure';
export const AUTH_REQUEST_SUCCESS = 'users/request-success';
export const LOG_OUT_REQUEST = 'users/log-out';



interface IAuthFormProps {
    username: string
    password: string
}

/* ------------------ Action Creators ------------------- */

interface IRequestStart {
    readonly type: typeof AUTH_REQUEST_START
}
interface IRequestSuccess {
    readonly type: typeof AUTH_REQUEST_SUCCESS,
    payload: IAuthSession
}
interface IRequestFailure {
    readonly type: typeof AUTH_REQUEST_FAILURE,
    payload: Error
}
export interface ILogOut {
    readonly type: typeof LOG_OUT_REQUEST,
}
export type AuthActions = | IRequestStart | IRequestSuccess | IRequestFailure | ILogOut;


const fetchStart = (): IRequestStart => ({
    type: AUTH_REQUEST_START
});

const fetchSuccess = (payload: IAuthSession): IRequestSuccess => ({
    type: AUTH_REQUEST_SUCCESS,
    payload,
});

const fetchError = (payload: Error): IRequestFailure => ({
    type: AUTH_REQUEST_FAILURE,
    payload,
});

const logOut = (): ILogOut => ({
    type: LOG_OUT_REQUEST
});


/* -------------------- Initial State ------------------- */

type InitialState = {
    user: IAuthSession | null
    fetching: boolean
    error: Error | null
}

let authUser;
try {
    authUser = JSON.parse(localStorage.getItem(LocalAuthID) ?? 'null');
} catch (e) {
    authUser = null;
    console.log(e);
}

const INITIAL_STATE: InitialState = {
    user: authUser,
    fetching: false,
    error: null
}


/* ----------------------- Reducers --------------------- */

export default function reducer(state = INITIAL_STATE, action: AuthActions) {
    switch (action.type) {
        case AUTH_REQUEST_START:
            return {
                ...state,
                fetching: true
            }
        case AUTH_REQUEST_SUCCESS:
            return {
                ...state,
                user: action.payload,
                fetching: false,
                error: null
            }
        case AUTH_REQUEST_FAILURE:
            return {
                ...state,
                error: action.payload,
                fetching: false
            }
        case LOG_OUT_REQUEST:
            return {
                ...state,
                user: null,
                fetching: false
            }
        default:
            return state
    }
}


/* ---------------------- Selectors --------------------- */

export const loggedUser = (state: AppState) => state.auth.user;
export const isLoggedIn = (state: AppState) => !!state.auth.user;
export const isError = (state: AppState) => !!state.auth.error;



/* ------------------- Juicy actions -------------------- */

export const authRequest = (requestTypeUrl: string, loginInputs: IAuthFormProps) => (dispatch: Dispatch<AuthActions>) => {
    dispatch(fetchStart());
    return api({method: "POST" as Method, requestUrl: `auth/${requestTypeUrl}`, data: loginInputs})
        .then(res => {
            const json = res.data as IAuthSession;
            dispatch(fetchSuccess(json));
            history.push("/");
            return Promise.resolve(res);
        })
        .catch(error => {
            if (error.response) error.message = error.response.data;
            dispatch(fetchError(error));
            return Promise.reject(error);
        });
};

export const checkSession = (authSession: IAuthSession | null) => (dispatch: Dispatch<AuthActions>) => {
    dispatch(fetchStart());
    return authApi({method: "POST" as Method, requestUrl: 'auth/check_session', authSession})
        .catch(error => {
            if (error.response) error.message = error.response.data;
            dispatch(fetchError(error));
            dispatch(logOut());
        });
};

export const logout = (authSession: IAuthSession | null) => (dispatch: Dispatch<AuthActions>) => {
    dispatch(fetchStart());
    return authApi({method: "POST" as Method, requestUrl: 'auth/logout', authSession})
        .catch(error => {
            if (error.response) error.message = error.response.data;
            dispatch(fetchError(error));
        }).finally(() => dispatch(logOut()));
};