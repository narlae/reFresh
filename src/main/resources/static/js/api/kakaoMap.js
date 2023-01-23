import {$} from "../utils/dom.js";

export function kakaoMap() {
    new daum.Postcode({
        oncomplete: function(data) {
            document.getElementById("address").value = data.address;
            document.getElementById("zoneCode").value = data.zonecode;
            $("#addressDetail").focus();
        }
    }).open();
}
