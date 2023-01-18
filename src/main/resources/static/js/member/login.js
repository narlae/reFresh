import {$} from "../utils/dom.js";

$("#member-submit").addEventListener("click", () => {
    $("#form").submit();
});

$("#member-signup").addEventListener("click", ()=>{
    //카카오 지도 발생
    new daum.Postcode({
        oncomplete: function(data) {
            console.log(data);
            document.getElementById("addr1").value = data.address;
            document.getElementById("zoneCode").value = data.zonecode;
            $("#addr2").focus();
        }
    }).open();
})
