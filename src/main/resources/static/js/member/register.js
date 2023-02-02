import {$} from "../utils/dom.js";
import Api from "../api/index.js";
import {kakaoMap} from "../api/kakaoMap.js";


$("#member-submit").addEventListener("click", () => {
    if (!$("#required").checked) {
        alert("필수 약관 동의를 해주세요.");
    }else{
        $("#form").submit();
    }
});

$("#telno").addEventListener("keyup", ()=>{
    $("#telno").value = ($("#telno").value.replace(/[^0-9]/g, "").replace(/(^02|^0505|^1[0-9]{3}|^0[0-9]{2})([0-9]+)?([0-9]{4})$/, "$1-$2-$3").replace("--", "-"));
})

$("#member-signup").addEventListener("click", ()=>{
    //카카오 지도 발생
    kakaoMap();
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