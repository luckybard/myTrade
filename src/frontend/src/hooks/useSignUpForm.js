import { useState, useEffect } from "react";
import { useHistory } from "react-router";

const useSignUpForm = (validate, callback ) => {
  const history = useHistory();
  const [values, setValues] = useState({
    username: "",
    email: "",
    password: "",
    password2: "",
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
          username: values.username,
          email: values.email,
          password: values.password,
        }),
      };
      fetch("http://localhost:8080/user/register", requestOptions).then(history.push("/sign-in"));
    }
  }, [errors]);

  return { handleChange, handleSubmit, values, errors };
};

export default useSignUpForm;