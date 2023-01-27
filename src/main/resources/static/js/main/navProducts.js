import Api from "../api/index.js";
import {$} from "../utils/dom.js";
import {searchParam} from "../utils/utils.js"

function App(){

    this.list = {
        page : 1,
    }
    this.init = async () =>{
        await render();
    }

    this.page = {};

    const render = async ()=>{
        if (searchParam('page') !== null) {
            this.list.page = searchParam('page');
        }
        let navName = searchParam('navName');
        this.page = await Api.getProductsByNav(this.list.page - 1, navName);
        $("#productsContainer").innerHTML = this.page.content.map((item) => {
            let selPrice = item.selPrice.toLocaleString();
            let price = item.price.toLocaleString();
            return `
            <div class="products">
            <a href="/product/${item.pdtId}"><img id="img" src="${item.image}"/></a>
            <span class="de_type">${item.deType ? '새벽배송' : '낮배송'}</span>
            <div class="product_title">${item.title}</div>
            <span class="product_ds_rate">${(item.dsRate === 0) ? '' : item.dsRate + '%'} <span class="product_sel_price">${selPrice}</span></span>
            <span class="product_price">${price}</span>
            <span class="product_tag">${item.tagName}</span></div>
            `;
        }).join("");

        const pagingNav = () => {
            let temp = '';
            let page = this.list.page;
            let naviSize = 10;
            let beginPage = parseInt((page - 1 )/ naviSize) * naviSize + 1;
            let totalPage = this.page.totalPages;
            let endPage = Math.min(beginPage + naviSize - 1, totalPage);
            if (!this.page.empty) {
                if (beginPage !==1) {
                    temp += `<a class="page" href="/products/nav?page=${beginPage-1}&navName=${navName}"><</a>`
                }
                for (let i = beginPage; i <= endPage; i++) {
                    temp += `<a class="page ${i== page ? 'paging-active' : ''}" href="/products/nav?page=${i}&navName=${navName}" >${i}</a>`
                }
                if (totalPage !== endPage) {
                    temp += `<a class="page" href="/products/nav?page=${endPage+1}&navName=${navName}">></a>`
                }

            }
            else{
                temp += `<div>게시물이 없습니다.</div>`;
            }

            return temp;
        };
        $(".paging").innerHTML = pagingNav();

    }



}
const app = new App();
app.init();