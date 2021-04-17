const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

function registration() {
    $('#loginform').find('#login').remove();
    $('#loginform').prepend('<input type="text" id="email" name="email" placeholder="johndoe@email.com" maxlength="50"><br>');
    $('#reg').remove();
    $('#loginform').append('<input id = "register" type="submit" value="Register now" disabled required>');
    $('#loginform').attr("action","/cinema_webapp_war/Register_Controller");


    $(window).on("keydown",function () {
        if(re.test($("#email").val())){
            $("#register").prop("disabled",false);
        }else{
            $("#register").prop("disabled",true);
        }
    });

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
