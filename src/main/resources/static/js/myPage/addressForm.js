import {kakaoMap} from "../api/kakaoMap.js";
import {$} from "../utils/dom.js";

$(".rebtn").addEventListener("click", ()=>{
    kakaoMap();
})

$("#insertBtn").addEventListener("click", ()=>{
    $("#address_add_body").submit();
})