import React from "react";
import "./alert.scss";

interface IAlert {
    type: "success" | "info" | "error"
    content?: string
}

const Alert = ({type, content}: IAlert) => {
    return (
        <div className={"alert " + type}>
            <span className="title-alert">{type}: </span>
            <span className="content-alert">{content}</span>
        </div>
    );
};

export default Alert;