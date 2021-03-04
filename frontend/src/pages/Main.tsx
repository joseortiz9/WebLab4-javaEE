import React, {FC, Suspense} from "react";
import Loader from "../components/Loader";
import Container from "../components/container/Container";


const Main:FC = ({ children }) => {
    return (
        <Suspense fallback={Loader}>
            <Container>
                {children}
            </Container>
        </Suspense>
    );
};

export default Main;