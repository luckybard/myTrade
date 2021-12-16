import useEditAdForm from "../../hooks/useEditAdForm";
import validate from "./ValidateEditAd";
import { Container } from "react-bootstrap";
import React from "react";

const EditAdForm = (props) => {
  const { handleChange, handleSubmit, values, errors } = useEditAdForm(
    validate,
    props.ad
  );

  return (
    <Container>
      <div
        style={{
          display: "flex",
          justifyContent: "center",
          alignContent: "center",
          marginTop: "7rem",
        }}
      >
        <form onSubmit={handleSubmit} noValidate>
          <h1>Edit your ad</h1>
          <div className="form-group">
            <label htmlFor="adCategory">Category</label>
            <select
              class="form-select"
              name="adCategory"
              value={values.adCategory}
              onChange={handleChange}
            >
              <option value="CLOTHES">Clothes</option>
              <option value="APPLIANCES">Appliances</option>
              <option value="BOOKS">Books</option>
              <option value="FURNITURE">Furniture</option>
              <option value="OTHER">Other</option>
            </select>
            {errors.adCategory && <p>{errors.adCategory}</p>}
          </div>
          <div>
            <label htmlFor="title">Title</label>
            <input
              class="form-control"
              type="text"
              name="title"
              value={values.title}
              onChange={handleChange}
            />
            {errors.title && <p>{errors.title}</p>}
          </div>
          <div>
            <label htmlFor="description">Description</label>
            <textarea
              class="form-control"
              rows="10"
              type="text"
              name="description"
              value={values.description}
              onChange={handleChange}
            />
            {errors.description && <p>{errors.description}</p>}
          </div>
          <div>
            <label htmlFor="price">Price</label>
            <input
              class="form-control"
              type="number"
              min="0"
              max="1000000"
              name="price"
              value={values.price}
              onChange={handleChange}
            />
            {errors.price && <p>{errors.price}</p>}
          </div>
          <div>
            <label htmlFor="city">City</label>
            <select
              class="form-select"
              name="city"
              value={values.city}
              onChange={handleChange}
            >
              <option value="WARSAW">Warsaw</option>
              <option value="LONDON">London</option>
              <option value="PARIS">Paris</option>
              <option value="MOSCOW">Moscow</option>
              <option value="PORTO">Porto</option>
              <option value="BERLIN">Berlin</option>
            </select>
            {errors.city && <p>{errors.city}</p>}
          </div>
          <div style={{ marginTop: "0.5rem" }}>
            <button class="btn btn-warning" type="submit">
              Edit
            </button>
          </div>
        </form>
      </div>
    </Container>
  );
};

export default EditAdForm;
