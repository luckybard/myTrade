import React from "react";
import useCreateAdForm from "../../hooks/useCreateAdForm";
import validate from "./ValidateCreateAd";
import { Container } from "react-bootstrap";

const CreateAdForm = () => {
  const { handleChange, handleSubmit, values, errors } =
    useCreateAdForm(validate);

  return (
    <Container>
      <div className="position-absolute top-50 start-50 translate-middle">
        <form onSubmit={handleSubmit} noValidate>
          <h1>Now it's time for create your ad!</h1>
          <div className="form-group">
            <label htmlFor="adCategory">Category</label>
            <select
              class="form-select"
              name="adCategory"
              value={values.adCategory}
              onChange={handleChange}
            >
              <option value="" disabled selected>
                Select category
              </option>
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
              placeholder="Enter title"
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
              placeholder="Enter description"
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
              placeholder="Enter price"
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
              <option value="" disabled selected>
                Select city
              </option>
              <option value="WARSAW">Warsaw</option>
              <option value="LONDON">London</option>
              <option value="PARIS">Paris</option>
              <option value="MOSCOW">Moscow</option>
              <option value="PORTO">Porto</option>
              <option value="BERLIN">Berlin</option>
            </select>
            {errors.city && <p>{errors.city}</p>}
          </div>
          <div style={{ marginTop: "0.5rem", position: "right" }}>
            <button class="btn btn-warning" type="submit">
              Create now!
            </button>
          </div>
        </form>
      </div>
    </Container>
  );
};

export default CreateAdForm;
