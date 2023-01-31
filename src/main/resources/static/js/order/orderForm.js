import Api from "../api/index.js";
import {$} from "../utils/dom.js";
import {pageGoPost} from "../utils/utils.js";

function App(){

    this.init = async () =>{
        initEventListeners();
        getPdtInfo();
    }
    const initEventListeners = () => {
        $("#order_submit").innerText = $("#payment_price").innerText + ' 결제하기';
    }

    const getPdtInfo = () =>{
        cartFormList.forEach(el => setData(el));
    }
    const setData = (el)=> {
        this.data.push([el.pdtId, el.quantity]);
    }
    this.data = [];

    /***
     * 필수 요소
     * cid 서버에서
     * partner_order_id 서버에서
     * partner_user_id 서버에서
     * item_name ---
     * quantity ---
     * total_amount ---
     * tax_free_amount 서버에서
     * approval_url 서버에서
     * cancel_url 서버에서
     * fail_url 서버에서
     */

    $("#order_submit").addEventListener("click",async ()=>{
        pageGoPost({
            url: "/payment/kakaoPay",
            items: this.data
        });
    })

}
const app = new App();
app.init();