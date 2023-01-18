import {$} from "../utils/dom.js";

$("#member-submit").addEventListener("click", () => {
    if (!$("#required").checked) {
        alert("필수 약관 동의를 해주세요.");
    }else{
        $("#form").submit();
    }
});

$("#member-signup").addEventListener("click", ()=>{
    //카카오 지도 발생
    new daum.Postcode({
        oncomplete: function(data) {
            document.getElementById("address").value = data.address;
            document.getElementById("zoneCode").value = data.zonecode;
            $("#addressDetail").focus();
        }
    }).open();
})

$("#AllCheck").addEventListener("click", ()=>{
    if ($("#AllCheck").checked) {
        $("#required").checked = true;
        $("#prvcArge").checked = true;
    }else{
        $("#required").checked = false;
        $("#prvcArge").checked = false;
    }
})