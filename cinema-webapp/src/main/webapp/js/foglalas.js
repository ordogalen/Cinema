$(document).ready(function () {
    var chairs = [];

    let helyek = $("#modositottHelyek");
    if (helyek.val() != null) {
        helyek.val().split(",").forEach(e => {
            chairs.push(e);
        });
        $("span").each(function () {
            if (chairs.includes(this.innerHTML)) {
                this.className = "chair";
                $(this).css({
                    "background-image":"url(../images/director-chair3.png)",
                });
            }
        });
    }

    let jegya = $("#jegyar").val();
    $("span").click(function (){
        var url = $(this).css('background-image').replace(/^url|[\(\)]/g, '');
        imageUURL = url;
        splitted = url.split('/');
        filename = splitted[splitted.length -1].split('"')[0];


        if(filename === "director-chair2.png"){
            console.log(chairs)
            return;
        }
        else if(filename === "director-chair3.png") {
            for(let i = 0; i<chairs.length;i++){
                if(chairs[i] === this.innerHTML){
                    chairs.splice(i,1);
                }
            }
            $(this).css({
                "background-image":"url(../images/director-chair1.png)",
            })
        }else{
            if(!chairs.includes(this.innerHTML)) {
                chairs.push(this.innerHTML);
            }
            $(this).css({
                "background-image":"url(../images/director-chair3.png)",
            })
        }
        $("#modositottHelyek").attr("value",chairs.join(","));
        $("#helyek").attr("value",chairs.join(","));
        $("#jegyar").attr("value",chairs.length*jegya);
    });
});

