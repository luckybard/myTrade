export default function ValidateMessage(text) {
    let errors = {};

    if (!text.trim()) {
        errors.text = "Text required";
    }

    return errors;
}