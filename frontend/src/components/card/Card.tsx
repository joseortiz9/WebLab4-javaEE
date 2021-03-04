import React, {FC} from "react";
import "./card.scss";

type ICardProps = {
    title?: string
}

const Card: FC<ICardProps> = ({children, title}) => {
    return (
        <div className="card">
            {title ? <h1 className="text-align-center">{title}</h1> : "" }
            {children}
        </div>
    );
};

export default Card;