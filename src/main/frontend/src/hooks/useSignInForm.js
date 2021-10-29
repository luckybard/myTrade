import { useState, useEffect } from "react";
const useSignInForm = (callback, validate,context) => {
  
  const [values, setValues] = useState({
    username: "",
    password: "",
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
      callback();
      const requestOptions = {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        // credentials: "include", //maybe to
        body: JSON.stringify({
          username: values.username,
          password: values.password,
        }),
      };
      fetch("http://localhost:8080/login", requestOptions).then((response) => {
        console.log(response.headers.get("Authorization"));
        console.log(response.headers.get("Set-Cookie"));
        context.login(response.headers.get("Authorization"));
        console.log(context.login);
      });
    }
  }, [errors]);

  return { handleChange, handleSubmit, values, errors };
};

export default useSignInForm;
