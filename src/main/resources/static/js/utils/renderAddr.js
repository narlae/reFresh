import {$} from "./dom.js";
import Api from "../api/index.js";

const renderAddr = async () =>{
    let temp = '';
    let result = await Api.getDefaultAdd();
    temp = `    <h4>배송지</h4>
                    <p>${result.address} ${result.addressDetail}</p>
                    <p id="is_star_deli">새벽배송</p>
                    <a href="/address/list"><button>배송지 변경</button></a>
        `;
    $("#shipping").innerHTML = temp;
}
await renderAddr();