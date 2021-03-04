import {IAuthSession} from "../../models/IAuthSession";
import {AppState} from "./index";
import {Dispatch} from "redux";
import {authApi} from "../../utils/Api";
import {Method} from "axios";
import {IPoint, IPointFetched} from "../../models/IPoint";


/* ---------------------- Types ------------------------- */

export const POINT_REQUEST_START = 'points/request-start';
export const POINT_REQUEST_FAILURE = 'points/request-failure';
export const POINT_ADD_REQUEST_SUCCESS = 'points/request-add-point';
export const POINT_FETCH_ALL_SUCCESS = 'points/request-fetch-all';



/* ------------------ Action Creators ------------------- */

interface IRequestStart {
    readonly type: typeof POINT_REQUEST_START
}
interface IRequestFailure {
    readonly type: typeof POINT_REQUEST_FAILURE,
    payload: Error
}
interface IAddPointSuccess {
    readonly type: typeof POINT_ADD_REQUEST_SUCCESS,
    payload: IPointFetched
}
interface IFetchAllSuccess {
    readonly type: typeof POINT_FETCH_ALL_SUCCESS,
    payload: IPointFetched[]
}

export type PointsActions = | IRequestStart | IRequestFailure | IAddPointSuccess | IFetchAllSuccess;


const fetchStart = (): IRequestStart => ({
    type: POINT_REQUEST_START
});

const fetchError = (payload: Error): IRequestFailure => ({
    type: POINT_REQUEST_FAILURE,
    payload,
});

const addPointSuccess = (payload: IPointFetched): IAddPointSuccess => ({
    type: POINT_ADD_REQUEST_SUCCESS,
    payload,
});

const fetchAllSuccess = (payload: IPointFetched[]): IFetchAllSuccess => ({
    type: POINT_FETCH_ALL_SUCCESS,
    payload,
});



/* -------------------- Initial State ------------------- */

type IinitialState = {
    points: IPointFetched[]
    fetching: boolean
    error: Error | null
}

const INITIAL_STATE: IinitialState = {
    points: [],
    fetching: false,
    error: null
}


/* ----------------------- Reducers --------------------- */

export default function reducer(state = INITIAL_STATE, action: PointsActions) {
    switch (action.type) {
        case POINT_REQUEST_START:
            return {
                ...state,
                fetching: true
            }
        case POINT_REQUEST_FAILURE:
            return {
                ...state,
                error: action.payload,
                fetching: false
            }
        case POINT_ADD_REQUEST_SUCCESS:
            return {
                ...state,
                points: state.points.concat(action.payload),
                fetching: false,
                error: null
            }
        case POINT_FETCH_ALL_SUCCESS:
            return {
                ...state,
                points: action.payload,
                fetching: false
            }
        default:
            return state
    }
}


/* ---------------------- Selectors --------------------- */

export const pointsFetched = (state: AppState) => state.points.points.length > 0;
export const isError = (state: AppState) => !!state.points.error;



/* ------------------- Juicy actions -------------------- */

export const fetchAllPoints = (authSession: IAuthSession | null) => (dispatch: Dispatch<PointsActions>) => {
    dispatch(fetchStart());
    return authApi({method: "GET" as Method, requestUrl: 'points', authSession})
        .then(res => {
            dispatch(fetchAllSuccess(res.data));
        })
        .catch(e => {
            dispatch(fetchError(e));
        });
};


export const addPoint = (pointInputs: IPoint, authSession: IAuthSession | null) => (dispatch: Dispatch<PointsActions>) => {
    dispatch(fetchStart());
    return authApi({method: "POST" as Method, requestUrl: 'points', authSession, data: pointInputs})
        .then(res => {
            dispatch(addPointSuccess(res.data.object))
        })
        .catch(error => {
            dispatch(fetchError(error));
        });
};

