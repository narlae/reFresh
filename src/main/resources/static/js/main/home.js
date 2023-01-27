import Api from "../api/index.js";
import {$} from "../utils/dom.js";

function App(){

    this.init = async () =>{
        await render();

    }
    const render = async () => {
        let result = await Api.getHomeProducts();
        for (let i = 0; i <result.length; i++) {
            let template = '';
            for (let j = 0; j < result[i].length; j++) {
                let selPrice = (result[i][j].selPrice).toLocaleString();
                let price = (result[i][j].price).toLocaleString();
                template += `
                            <div class="products"><a href="/product/${result[i][j].pdtId}">
                            <img class="mainImg" src="${result[i][j].image}"/></a>
                            <h3 class="product_title">${result[i][j].title}<h3/>
                            <div class="product"><span class="product_ds_rate">${result[i][j].dsRate}%</span>
                            <span class="product_sel_price">${selPrice}원</span></div>
                            <span class="product_price">${price}원</span></div>
`;
            }
            $(`#` + "product" + `${i+1}`).innerHTML = template;
        }
    }

}
const app = new App();
app.init();

