import React, {useEffect, useState} from "react";
import {useHistory} from "react-router";

const useEditAdForm = (validate, ad) => {
    const history = useHistory();
    const [values, setValues] = useState({
        title: ad.title,
        description: ad.description,
        adCategory: ad.adCategory,
        price: ad.price,
        city: ad.city
    });

    const [errors, setErrors] = useState({});
    const [isSubmitting, setIsSubmitting] = useState(false);

    // useEffect(() =>
    //     setValues({
    //             ...values,
    //             adCategory: ad.adCategory
    //         }
    //         , []
    //     ));

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
            // callback();
            const requestOptions = {
                method: "PATCH",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({
                    id: ad.id,
                    title: values.title,
                    description: values.description,
                    adCategory: values.adCategory,
                    price: values.price,
                    city: values.city,
                }),
            };
            fetch("http://localhost:8080/ad/patch", requestOptions).then(
                history.push("/home")
            );
        }
    }, [errors]);

    return {handleChange, handleSubmit, values, errors};
};
export default useEditAdForm;
