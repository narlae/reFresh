import {$} from "../utils/dom.js";

$("#member-submit").addEventListener("click", () => {
    console.log("click");
    $("#form").submit();
});
