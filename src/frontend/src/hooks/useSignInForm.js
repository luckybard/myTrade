import {useState, useEffect, useContext} from "react";
import AuthContext from "../store/auth-context";
import {useHistory} from "react-router-dom";

const useSignInForm = (validate) => {
    const history = useHistory();
    const authCtx = useContext(AuthContext);
    const [values, setValues] = useState({
        username: "",
        password: "",
    });
    const [errors, setErrors] = useState({});
    const [isSubmitting, setIsSubmitting] = useState(false);

    const handleChange = (e) => {
        const {name, value} = e.target;
        setValues({
            ...values,
            [name]: value,
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        setErrors(validate(values));
        setIsSubmitting(true);
    };

    useEffect(() => {
        if (Object.keys(errors).length === 0 && isSubmitting) {
            const requestOptions = {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({
                    username: values.username,
                    password: values.password,
                }),
            };
            fetch("http://localhost:8080/login", requestOptions).then((response) =>  {
                if (response.ok) {
                    authCtx.login(response.headers.get("Authorization"));
                } else {
                    throw new Error("Sorry something went wrong")
                }
            }).then(history.push("/home"));
        }
    }, [errors]);

    return {handleChange, handleSubmit, values, errors};
};

export default useSignInForm;
