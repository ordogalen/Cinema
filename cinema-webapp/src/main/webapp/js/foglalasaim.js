$(document).ready(function () {
            $(".foglalasaimAdatok").each(function () {
                var idopont = $(this).children(".idopont")[0].innerHTML;

                //<td class = "torles"><a href ="foglalasaim.jsp?value=${item.jegy_idProperty().value}"> Torles </a></td>
                var today = new Date();
                var idopontDate = new Date(idopont);
                var diffMs = (idopontDate - today)
                var diffDays = Math.floor(diffMs / 86400000); // days
                if(diffDays < 1){
                    $(this).find(".torles").remove();
                    $(this).find(".modositas").remove();
                }
            });
})