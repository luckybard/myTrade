export default function ValidateCreateAd(values) {
  let errors = {};

  if (values.adCategory.length === 0) {
    errors.adCategory = "Select ad category!";
  }

  if (!values.title.trim()) {
    errors.title = "Title required";
  } else if (values.title.length < 6) {
    errors.title = "Title needs to be 6 characters or more";
  }

  if (!values.description) {
    errors.description = "Description is required";
  } else if (values.description.length < 20) {
    errors.description = "Description needs to be 20 characters or more";
  }

  if (values.city.length === 0) {
    errors.city = "Select city!";
  }

  if (values.price.length === 0) {
    errors.price = "Enter price!";
  }
  return errors;
}
