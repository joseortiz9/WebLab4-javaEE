import React, {Dispatch, SetStateAction, useEffect, useState} from "react";
import PointForm from "../components/point-form/PointForm";
import {useDispatch, useSelector} from "react-redux";
import {addPoint, fetchAllPoints, POINT_REQUEST_FAILURE} from "../store/ducks/Points";
import {AppState} from "../store/ducks";
import {loggedUser} from "../store/ducks/Auth";
import PointsTable from "../components/table/PointsTable";
import {IPoint, IPointFetched} from "../models/IPoint";
import PointsCanvas from "../components/canvas/PointsCanvas";
import Card from "../components/card/Card";
import {validatePoint} from "../validators";


export interface IPointsArrProps {
    points: IPointFetched[]
}
export interface IPointFormProps {
    valR: number
    setValR: Dispatch<SetStateAction<number>>
    submitPoint(point: IPoint): void
}


const HomePage = () => {
    const dispatch = useDispatch();
    const authSession = useSelector((state: AppState) => loggedUser(state));
    const fetchedPoints = useSelector((state: AppState) => state.points.points);
    const [valR, setValR] = useState(NaN);

    useEffect(() => {
        dispatch(fetchAllPoints(authSession));
    }, [authSession, dispatch]);


    const isValidPoint = (point: IPoint): boolean => {
        let msg = validatePoint(point);
        if (fetchedPoints.length > 0) {
            const lastPoint = fetchedPoints[fetchedPoints.length - 1];
            if (lastPoint.x === point.x && lastPoint.y === point.y && lastPoint.r === point.r) {
                msg = "new point is same as last added!";
            }
        }
        if (msg !== "") {
            dispatch({
                type: POINT_REQUEST_FAILURE,
                payload: {name: "validation", message: msg} as Error
            });
            return false;
        }
        return true;
    }


    function submitPoint(point: IPoint) {
        point = {
            x: Number(point.x),
            y: Number(Number(point.y).toFixed(3)),
            r: Number(valR)
        };
        if (!isValidPoint(point))
            return;
        dispatch(addPoint(point, authSession));
    }


    return(
        <>
            <div className="flex-container">
                <Card title="Create a point!">
                    <PointForm valR={valR} setValR={setValR} submitPoint={submitPoint} />
                </Card>
                <Card>
                    <PointsCanvas valR={valR}
                                  setValR={setValR}
                                  submitPoint={submitPoint}
                                  points={fetchedPoints} />
                </Card>
            </div>
            <PointsTable points={fetchedPoints} />
        </>
    );
};

export default HomePage;