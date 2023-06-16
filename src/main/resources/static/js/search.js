document.addEventListener('DOMContentLoaded', function () {
    var form = document.getElementById('search-bar');

    form.addEventListener('keydown', function (event) {
        if (event.key === 'Enter') {
            event.preventDefault();
            form.submit();
        }
    });
});