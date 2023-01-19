import {$} from "../utils/dom.js";
import Api from "../api/index.js";

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

let $duplBtn = $("#duplBtn");

$duplBtn.addEventListener("click", async (e)=>{
    let $userEmail = $("#userEmail");
    let userEmail = $userEmail.value;
    let res = await Api.checkDuplicateEmail(userEmail);
    if (!res) {
        alert("가입할 수 있는 이메일입니다.");
        $userEmail.setAttribute("readonly", 'readonly');
        $userEmail.style.background = '#dedede';
        $duplBtn.style.pointerEvents = 'none';
        $duplBtn.style.background = '#dedede';
        $duplBtn.innerText = '가입가능';

    }else{
        alert("동일한 이메일이 이미 가입되어있습니다.");
        $userEmail.focus();
    }
})