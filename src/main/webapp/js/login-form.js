function validateForm(formId) {
    var loginForm = document.getElementById(formId);
    var alertBlock = document.getElementById('form-error');
    var username = loginForm.username.value;
    var password = loginForm.password.value;
    var errorMessage = '';

    if (!username || !/^[a-zA-Z0-9_]{4,20}$/.test(username)) {
        errorMessage += "Имя пользователя должно иметь длину от 4 до 20 символов " +
            "и содержать только латинские буквы, цифры и символы нижнего подчеркивания";
    }

    if (!password || !/^[a-zA-Z0-9_]{4,20}$/.test(password)) {
        if (errorMessage) errorMessage += '<br>';

        errorMessage += "Пароль должен иметь длину от 4 до 20 символов " +
            "и содержать только латинские буквы, цифры и символы нижнего подчеркивания";
    } else if (formId === 'register-form') {
        var repeatPassword = loginForm.repeatPassword.value;

        if (repeatPassword !== password) {
            if (errorMessage) errorMessage += '<br>';

            errorMessage += "Введенные пароли должны совпадать";
        }
    }

    if (errorMessage) {
        alertBlock.innerHTML = '<strong>' + errorMessage + '</strong>';
        loginForm.classList.add('error');
        return false;
    }
}