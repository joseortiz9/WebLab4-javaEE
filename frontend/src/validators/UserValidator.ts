import {max, min} from "./validators";
import {IUser} from "../models/IUser";

const UserValidator = (user: IUser): string => {
    if (!min(3, user.username.length)) {
        return `username must be bigger than 3 chars but has ${user.username.length}`;
    } else if (!max(15, user.username.length)) {
        return `username must be smaller than 15 chars but has ${user.username.length}`;
    }

    if (!min(3, user.password.length)) {
        return `password must be bigger than 3 chars but has ${user.password.length}`;
    } else if (!max(15, user.password.length)) {
        return `password must be smaller than 15 chars but has ${user.password.length}`;
    }

    return "";
};

export default UserValidator;