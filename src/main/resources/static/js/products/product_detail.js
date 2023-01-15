import Api from "../api/index.js";
import {$} from "../utils/dom.js";
function App(){

    this.init = async () =>{
        localeString()
        dsRatePercent()
    }

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

    // let offsetTop = $(".menu_nav").offsetTop;
    // window.scroll( function() {
    //     if ($(document).scrollTop() > offsetTop) {
    //         $(".menu_nav").css({"position":"fixed","top":"0px","left":"50%","transform":"translateX(-50%)"});
    //     }
    //     else {
    //         $(".menu_nav").css({"position":'relative'});
    //     }
    // });


    $(".menu_nav").addEventListener("click", (e)=>{
        e.preventDefault();
        let x = e.target.getAttribute("value");
        $(x).scrollIntoView({block: "start"});
    })

    const $pdt_qty = $("#pdt_qty");

    $("#down_qty").addEventListener("click", () =>{
        if(parseInt($pdt_qty.innerText)>1)
            $pdt_qty.innerText = parseInt($pdt_qty.innerText)-1;
        $("#actual_price").innerText = (parseInt($pdt_qty.innerText)*selPrice).toLocaleString();
    })

    $("#up_qty").addEventListener("click", ()=>{
        $pdt_qty.innerText = parseInt($pdt_qty.innerText)+1;
        $("#actual_price").innerText = (parseInt($pdt_qty.innerText)*selPrice).toLocaleString();
    });
}
const app = new App();
app.init();

