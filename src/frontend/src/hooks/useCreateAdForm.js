import { useState, useEffect } from "react";
import { useHistory } from "react-router";

const useCreateAdForm = (callback, validate) => {
  const history = useHistory();
  const [values, setValues] = useState({
    title: "",
    description: "",
    adCategory: "",
    price: "",
    city: "",
  });

  const [errors, setErrors] = useState({});
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
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
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          title: values.title,
          description: values.description,
          adCategory: values.adCategory,
          price: values.price,
          city: values.city,
        }),
      };
      fetch("http://localhost:8080/ad/create", requestOptions).then(history.push("/home"));
    }
  }, [errors]);

  return { handleChange, handleSubmit, values, errors };
};

export default useCreateAdForm;
