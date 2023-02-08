import {$} from "../utils/dom.js";
import Api from "../api/index.js";
import {pageGoPost, searchParam} from "../utils/utils.js";


function App() {

    this.init = async () => {
        await renderCart();
        initEventListeners();
    }

    this.cart = [];

    let $cart = $("#cart");

    const renderCart = async () => {
        let temp = '';
        let result = await Api.getCartList();
        if (result.length === 0) {
            $("#cart-empty").innerText = '장바구니에 담긴 상품이 없습니다';
            $("#cart").innerHTML = '';
            return;
        }
        result.forEach((item) => {
            let value = (item.selPrice * item.quantity).toLocaleString();
            let subTemp = '';
            if (item.quantity >= 10) {
                subTemp = `<input class="input-quantity" type="text" value="${item.quantity}"style="width:30px;"/>
                               <input type="button" class="update-quantity-box" style="font-size: 10px; width: fit-content; display: none;" value="수량변경"/>`
            }else {
                subTemp = `
                            <select class="quantity-select" data-quantity="${item.quantity}" data-pdtId="${item.pdtId}">
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
                                <option value="6">6</option>
                                <option value="7">7</option>
                                <option value="8">8</option>
                                <option value="9">9</option>
                                <option value="10">10+</option>
                            </select>`
            }
            temp += `     <div class="product" data-pdtId="${item.pdtId}" data-quantity="${item.quantity}">
                        <input type="checkbox" id="checked-cart-${item.pdtId}" class="checkbox" name="check" checked>
                        <label for="checked-cart-${item.pdtId}"></label>
                        <a href="/product/${item.pdtId}" style="display: flex; align-items: center;">
                        <img src="${item.image}"
                             alt="" class="product_img"/>
                        <h4>${item.title}</h4>
                        </a><div class="quantity_control_box">`+subTemp+`</div>
                        <div class="cart-sum" id="cart-sum-hidden-${item.pdtId}" hidden>${item.selPrice * item.quantity}</div>
                        <div class="cart-sum-pdt" id="cart-sum-pdt-hidden-${item.pdtId}" hidden>${item.price * item.quantity}</div>
                        <p id="cart-sum-\` + index + \`" style="margin-bottom: 0px;">${value}원</p>
                        <button class="removePdt" data-pdtId="${item.pdtId}" style="margin-bottom: 5px; color: #cacaca; font-size: 16px">x</button>
                        <input class="pdt-stock" id="pdt-stock-${item.pdtId}" max="${item.stock}" value="${item.stock}" hidden>
                        </div>
            `;
        });
        $cart.innerHTML = temp;
        document.querySelectorAll('[data-quantity]').forEach(e => {
            e.value = e.dataset.quantity;
        });
        calculation();
    }


    const initEventListeners = () => {
        $cart.addEventListener("click", async (e) => {
            if (e.target.classList.contains("removePdt")) {
                let pdtId = e.target.dataset.pdtid;
                await Api.removePdt(pdtId);
                await renderCart();
            }
            if (e.target.classList.contains("update-quantity-box")) {
                let pdtId = e.target.closest(".product").querySelector(".removePdt").dataset.pdtid;
                let quantity = e.target.closest(".product").querySelector(".input-quantity").value;
                await Api.updateQuantity(pdtId, quantity);
                window.location.href = '/cart';
            }
            if (e.target.classList.contains("input-quantity")) {
                e.target.oninput = () =>{
                    e.target.closest(".product").querySelector(".update-quantity-box").style.display = 'block';
                }
            }

        })

        $cart.addEventListener("change", async (e) => {
            if (e.target.classList.contains("quantity-select")) {
                if (e.target.value === '10') {
                    e.target.closest(".quantity_control_box").innerHTML = `<input class="input-quantity" type="text" value="${e.target.value}"style="width:30px;"/>
                                                                            <input class="update-quantity-box" type="button" style="font-size: 10px; width: fit-content; display: block;" value="수량변경"/>
                    `;
                    return;
                }
                let pdtId = e.target.dataset.pdtid;
                await Api.updateQuantity(pdtId, e.target.value);
                window.location.href = '/cart';
            }
        })

        $("#order_submit").addEventListener("click",async ()=>{
            if (this.cart.length === 0) {
                alert("장바구니가 비어있습니다. 주문할 수 없습니다.");
                return;
            }
            pageGoPost({
                url: "/order/orderForm",	//이동할 페이지
                items: this.cart
            });

        })


        /*
        * 체크 박스
        * */

        let $check = document.querySelectorAll('input[name=check]');

        $("#allCheck").addEventListener("click", ()=>{
            if ($("#allCheck").checked) {
                $check.forEach((e) => e.checked = true);
            }else{
                $check.forEach((e) => e.checked = false);
            }
            calculation();
        })

        $check.forEach(e=>e.addEventListener("click", ()=>{
                let total = $check.length;
                let checked = document.querySelectorAll('input[name=check]:checked').length;

                if (total !== checked) {
                    $("#allCheck").checked = false;
                } else {
                    $("#allCheck").checked = true;
                }
                calculation();
            })
        )

        if (searchParam("error") === '1') {
            alert("결제가 실패했습니다. 다시 시도해주십시오");
        } else if (searchParam("error") === '2') {
            alert("결제를 취소하셨습니다.");
        } else if (searchParam("error") === '3') {
            alert("결제가 실패했습니다. 다시 시도해주십시오");
        }

    }

    $("#delete-checked").addEventListener("click", ()=>{
        document.querySelectorAll('input[name=check]:checked').forEach(async (e)=>{
            let pdtId = e.closest(".product").querySelector(".removePdt").dataset.pdtid;
            await Api.removePdt(pdtId);
        })
        window.location.href = '/cart';
    })

    const calculation = () =>{
        let totalPrice = 0;
        let totalSelPrice = 0;
        this.cart = [];
        document.querySelectorAll('input[name=check]:checked').forEach((e)=>{
            totalPrice += parseInt(e.closest(".product").querySelector(".cart-sum-pdt").innerText);
            totalSelPrice += parseInt(e.closest(".product").querySelector(".cart-sum").innerText);
            this.cart.push([e.closest(".product").dataset.pdtid, e.closest(".product").dataset.quantity]);
        })
        let totalDsPrice = totalPrice - totalSelPrice;

        $("#product_price").innerText = totalPrice.toLocaleString() + '원';
        $("#discount_price").innerText = totalDsPrice.toLocaleString() + '원';
        $("#payment_price").innerText = totalSelPrice.toLocaleString() + '원';
    }

}
const app = new App();
app.init();