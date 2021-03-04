import React, {useEffect, useRef} from "react";
import {IPointFormProps, IPointsArrProps} from "../../pages/HomePage";
import {drawer, drawPoint} from "./CanvasDrawer";
import {IPoint} from "../../models/IPoint";

const canvasStyle = {
    margin: "0 auto",
    display: "block",
}

const PointsCanvas = ({points, submitPoint, valR, setValR}: IPointsArrProps & IPointFormProps) => {

    const canvasRef = useRef<HTMLCanvasElement>(null);

    useEffect(() => {
        const canvas = canvasRef.current;
        if (!canvas) return;
        drawer(canvas, valR, points);
    }, [valR, points]);


    const onCanvasClick = (event: React.MouseEvent) => {
        const canvas = canvasRef.current;
        if (!canvas) return;

        const getMousePos = (event: React.MouseEvent) => {
            const rect = canvas.getBoundingClientRect();
            return {
                X: 300*(event.clientX - rect.left)/rect.width,
                Y: 150*(event.clientY - rect.top)/rect.height
            };
        }

        let physicR = canvas.height / 3 / valR;
        const clickedX = (getMousePos(event).X - canvas.width/2) / physicR;
        const clickedY = (-getMousePos(event).Y + canvas.height/2) / physicR;

        const clickedPoint: IPoint = {x: Number(clickedX.toFixed(3)), y: Number(clickedY.toFixed(3)), r: valR};
        drawPoint(canvas, clickedPoint, valR);
        submitPoint(clickedPoint);
    }

    return(
        <>
            <canvas ref={canvasRef} onClick={onCanvasClick} style={canvasStyle} />
        </>
    );
};

export default PointsCanvas;