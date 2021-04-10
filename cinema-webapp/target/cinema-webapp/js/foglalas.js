let chairs = [];
$(document).ready(function () {
    let jegya = $("#jegyar").val();
    $("span").each(function () {
        if(this.className === "reservedChair"){
            $(this).css({
                "background-color":"red"
            })
        }
    }).click(function (){
        let color = $(this).css("background-color");
        if(color === "rgb(255, 0, 0)"){
            return;
        }
        else if(color === "rgb(0, 128, 0)") {
            for(let i = 0; i<chairs.length;i++){
                if(chairs[i] === this.innerHTML){
                    chairs.splice(i,1);
                }
            }
            $(this).css({
                "background-color":"white"
            })
        }else{
            if(!chairs.includes(this.innerHTML)) {
                chairs.push(this.innerHTML);
            }
            $(this).css({
                "background-color":"green"
            })
        }
        $("#helyek").attr("value",chairs.join(","));
        $("#jegyar").attr("value",chairs.length*jegya);
    })
});