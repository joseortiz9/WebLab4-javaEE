import React, {FC} from "react";

import "./Container.scss";

const Container: FC = ({children}) => (
    <div className="container">
        {children}
    </div>
);

export default Container;