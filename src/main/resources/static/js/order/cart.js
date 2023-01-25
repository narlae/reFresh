import {$} from "../utils/dom.js";
import Api from "../api/index.js";


function App() {

    this.init = async () => {
        await renderCart();
        await renderAddr();
        initEventListeners();
    }

    this.list = {}

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
            temp += `     <div class="product">
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
    const renderAddr = async () =>{
        let temp = '';
        if (loginMember == null) {
            temp = `<h4>배송지</h4>
                    <p id="is_star_deli">배송지를 등록하고</p>
                    <p>구매 가능한 상품을 확인하세요!</p>
                    <a href="/address/list"><button>배송지 등록</button></a>`;
            $("#shipping").innerHTML = temp;
            return;
        }
        let result = await Api.getDefaultAdd();
        temp = `    <h4>배송지</h4>
                    <p>${result.address} ${result.addressDetail}</p>
                    <p id="is_star_deli">새벽배송</p>
                    <a href="/address/list"><button>배송지 변경</button></a>
        `;
        $("#shipping").innerHTML = temp;
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
                console.log(e.target);
                e.target.oninput = () =>{
                    console.log("oninput");
                    console.log(e.target.closest(".product").querySelector(".update-quantity-box").type);
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
        document.querySelectorAll('input[name=check]:checked').forEach((e)=>{
            totalPrice += parseInt(e.closest(".product").querySelector(".cart-sum-pdt").innerText);
            totalSelPrice += parseInt(e.closest(".product").querySelector(".cart-sum").innerText);
        })
        let totalDsPrice = totalPrice - totalSelPrice;

        $("#product_price").innerText = totalPrice.toLocaleString() + '원';
        $("#discount_price").innerText = totalDsPrice.toLocaleString() + '원';
        $("#payment_price").innerText = totalSelPrice.toLocaleString() + '원';
    }

}
const app = new App();
app.init();