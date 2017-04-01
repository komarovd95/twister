function previewFile() {
    var preview = document.querySelector('img');
    var file    = document.querySelector('input[type=file]').files[0];
    var reader  = new FileReader();

    reader.onloadend = function () {
        preview.src = reader.result;
    };

    if (file) {
        reader.readAsDataURL(file);
    } else {
        preview.src = getDefaultAvatarUrl();
    }
}

function editProfileSubmit() {
    var loginForm = document.getElementById('editForm');
    var alertBlock = document.getElementById('form-error');
    var image = document.querySelector('img');
    var file = document.querySelector('input[type=file]').files[0];
    var password = document.getElementById('password').value;
    var repeatPassword = document.getElementById('repeatPassword').value;
    var errorMessage = '';

    if ((image.src && image.getAttribute('src') !== getDefaultAvatarUrl()) || password) {
        if (image.src && image.getAttribute('src') !== getDefaultAvatarUrl()
            && !/^data:image\//.test(image.src)) {
            errorMessage += 'Неверный формат изображения'
        }

        if (image.src && file && file.size > 1024 * 1024) {
            if (errorMessage) errorMessage += '<br>';

            errorMessage += 'Размер файла не должен превышать 1Mb'
        }

        if (password && !/^[a-zA-Z0-9_]{4,20}$/.test(password)) {
            if (errorMessage) errorMessage += '<br>';

            errorMessage += "Пароль должен иметь длину от 4 до 20 символов " +
                "и содержать только латинские буквы, цифры и символы нижнего подчеркивания";
        } else if (password && repeatPassword !== password) {
            if (errorMessage) errorMessage += '<br>';

            errorMessage += "Введенные пароли должны совпадать";
        }

        if (errorMessage) {
            alertBlock.innerHTML = '<strong>' + errorMessage + '</strong>';
            loginForm.classList.add('error');
            return false;
        }
    } else {
        return false;
    }
}