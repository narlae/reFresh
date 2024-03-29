import Api from "../api/index.js";
import {$} from "../utils/dom.js";

function App(){

    this.init = async () =>{
        localeString()
        dsRatePercent()
        fixMenuNav();
        menuNav();
        updateQty();
    }
    this.pdt = {
        pdtId : pdtId,
        quantity : 1,
    };

    const localeString = () =>{
        let selPriceLocaleString = selPrice.toLocaleString();
        let priceLocaleString = price.toLocaleString();

        document.getElementById("sel_price").innerText = selPriceLocaleString;
        document.getElementById("actual_price").innerText = selPriceLocaleString;
        document.getElementById("price").innerText = priceLocaleString;
    }

    const dsRatePercent = () => {
        if (dsRate !== 0) {
            document.getElementById("ds_rate").innerText = dsRate + '%';
        } else{
            document.getElementById("ds_rate").style.paddingRight = '0';
        }
    }

    const fixMenuNav = () =>{
        const target = $(".menu_nav");
        const menuNavY = target.offsetTop

        window.addEventListener("scroll", ()=>{
            if (window.scrollY > menuNavY) {
                target.style.cssText = `
            position: fixed; top: 0px; left: 50%; transform: translateX(-50%);
            `;
            } else {
                target.style.position = 'relative';
            }
        })
    }

    const menuNav = () =>{
        $(".menu_nav").addEventListener("click", (e)=>{
            e.preventDefault();
            let x = e.target.getAttribute("value");
            $(x).scrollIntoView({block: "start"});
        })
    }

    const updateQty = () => {
        const $pdt_qty = $("#pdt_qty");

        $("#down_qty").addEventListener("click", () =>{
            if(parseInt($pdt_qty.innerText)>1)
                $pdt_qty.innerText = parseInt($pdt_qty.innerText)-1;
            this.pdt.quantity = $pdt_qty.innerText;
            $("#actual_price").innerText = (parseInt($pdt_qty.innerText)*selPrice).toLocaleString();
        })

        $("#up_qty").addEventListener("click", ()=>{
            $pdt_qty.innerText = parseInt($pdt_qty.innerText)+1;
            this.pdt.quantity = $pdt_qty.innerText;
            $("#actual_price").innerText = (parseInt($pdt_qty.innerText)*selPrice).toLocaleString();
        });
    }

    $("#addCart").addEventListener("click",async ()=>{
        await Api.addCart(this.pdt);
        if (!confirm("장바구니에 성공적으로 상품이 담겼습니다. 장바구니로 가시겠습니까?")) {

        } else {
            window.location.href = "/cart";
        }
    })




}
const app = new App();
app.init();

