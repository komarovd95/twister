function postTextChange() {
    var postText = document.querySelector('textarea');
    var submit = document.querySelector('input[type="submit"]');

    submit.disabled = !postText.value.trim();
}
