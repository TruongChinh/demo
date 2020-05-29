$(".info-item .btn").click(function () {
    $(".container").toggleClass("log-in");
});
$(".container-form .btn").click(function () {
    $(".container").addClass("active");
});


function register() {

    let user = {
        "userName": $('#register [name=userName]').val(),
        "password": $('#register [name=password]').val(),
    }

    console.log(user);

    axios.post("/api/app/register-user", user).then(function (res) {
        if (res.data.http == 200) {
            window.location.href = '/';
        } else {
            swal(
                'Error',
                res.data.data.message,
                'error'
            );
        }
    }, function (err) {
    })

}