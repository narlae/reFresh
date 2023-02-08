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
        $('#below-div').innerHTML = pay;
        $(".select-payment").addEventListener("click",(e)=>{
            console.log($("#payment").value);
            if (e.target.classList.contains("payment")) {
                let now = e.target.innerText;
                if (now === '신용카드') {
                    $('#below-div').innerHTML = credit;
                } else if (now === '간편 결제') {
                    $('#below-div').innerHTML = pay;
                } else {
                    $('#below-div').innerHTML = '';
                }
            }
        })

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
        console.log($("#kakao-pay"))
        if ($("#payment").value !== 'pay') {
            alert("카카오페이를 선택해주십시오. 다른 결제수단은 미구현입니다.");
            return;
            }

        pageGoPost({
            url: "/payment/kakaoPay",
            items: this.data
        });
    })

    let credit =
        `<div>
                <select class="credit-select" name='credit'>
                    <option value=''>카드를 선택해주세요</option>
                    <option value='현대'>현대</option>
                    <option value='신한'>신한</option>
                    <option value='비씨'>비씨</option>
                    <option value='KB국민'>KB국민</option>
                </select>
                <select class="credit-select" name='installment'>
                    <option value=''>할부를 선택해주세요</option>
                    <option value='일시불'>일시불</option>
                    <option value='2개월'>2개월</option>
                    <option value='4개월'>4개월</option>
                    <option value='6개월'>6개월</option>
                </select>
            </div>`;
    let pay =
        `
                <div style="display: flex; justify-content: center">
                    <label class="container" for="kurly-pay">컬리페이
                        <input type="radio" id="kurly-pay" name="payment-pay" checked="checked">
                        <span class="checkmark"></span>
                    </label>
                    <label class="container" for="kakao-pay">카카오페이
                        <input type="radio" id="kakao-pay" name="payment-pay" checked>
                        <span class="checkmark"></span>
                    </label>
                </div>`;

}
const app = new App();
app.init();