function registration() {
    $('#loginform').find('#login').remove();
    $('#loginform').prepend('<input type="text" id="email" name="email" placeholder="Your email.."><br>');
    $('#reg').remove();
    $('#loginform').append('<input id = "register" type="submit" value="Register" required>');
    $('#loginform').attr("action","/cinema_webapp_war/Register_Controller");

}

$(document).ready(function () {
    $("#login").on("mouseenter", function () {
        $("#email").prop("disabled",true);
    })
    $("#login").on("mouseleave", function () {
        $("#email").prop("disabled",false);
    })
    $("#reg").click(registration);

})
