import {IPoint} from "../models/IPoint";
import {isNumber, max, min} from "./validators";

const PointValidator = (point: IPoint): string => {
    if (!isNumber(point.x)) {
        return `X must be a number!`;
    } else if (!min(-5, point.x)) {
        return `X must be bigger than -5 but is ${point.x}`;
    } else if (!max(3, point.x)) {
        return `X must be smaller than 3 but is ${point.x}`;
    }

    if (!isNumber(point.y)) {
        return `Y must be a number!`;
    } else if (!min(-5, point.y)) {
        return `Y must be bigger than -5 but is ${point.y}`;
    } else if (!max(5, point.y)) {
        return `Y must be smaller than 5 but is ${point.y}`;
    }

    if (!isNumber(point.r)) {
        return `R must be a number!`;
    } else if (!min(0, point.r)) {
        return `R must be positive! but is ${point.r}`;
    } else if (!max(3, point.r)) {
        return `R must be smaller than 3 but is ${point.r}`;
    }

    return "";
};

export default PointValidator;