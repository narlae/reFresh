import {$} from "../utils/dom.js";
import Api from "../api/index.js";


function App(){

    this.init = async () =>{
        await render();
        initEventListeners();
    }

    this.list = {
        elementNum : 0,
    }

    const render = async () => {
        let result = await Api.getAddressList(userId);
        this.list.elementNum = result.length;
        let temp= '';
        result.forEach((item)=>{
            let defaultAdd = item.defaultAdd ? '기본배송지' : '';
            temp+= `                    <div class="address colx">
                        <div class="address_main first_col_addr">
                                    <span class="chk_addr_tag_${item.defaultAdd}">${defaultAdd}
                                    </span>
                            <p class="addr" aria-readonly="true">${item.address}
                                <br>
                                ${item.addressDetail}
                            </p>
                        </div>
                        <div class="address_name second_col_addr colx">${item.recipient}
                        </div>
                        <div class="address_tel third_col_addr colx" id="phone-">${item.telno}
                        </div>
                        <div class="address_deli fourth_col_addr colx">
                                    <span id="deli_true">새벽배송
                                    </span>
                        </div>
                        <div class="address_modify fifth_col_addr colx">
                            <a href="/address/read?addId=${item.addId}">
                                <button type="button" id="readBtn">
                                    수정
                                </button>
                            </a>
                        </div>
                        <div class="addressRemove fifth_col_addr colx" data-id="${item.addId}">삭제</div>
                    </div>
            `
        })
        $("#addressList").innerHTML = temp;
    }

    $("#newAddrressAdd").addEventListener("click", (e)=>{
        if (this.list.elementNum >= 5) {
            alert("주소지는 5개까지만 등록할 수 있습니다.");
            e.preventDefault();
        }
    })

    const initEventListeners = () => {
        $("#addressList").addEventListener("click", async (e)=>{
            if (e.target.classList.contains("addressRemove")){
                let index = e.target.dataset.id;
                await Api.deleteAddress(index);
                await render();
            }
        })
    }


}
const app = new App();
app.init();