import Api from "../api/index.js";
import {$} from "../utils/dom.js";
import {searchParam} from "../utils/utils.js"

function App(){

    this.list = {
        page : 1,
        catId : catId,
        sort_option : 'pdtId',
    }
    this.init = async () =>{
        await render();
        initEventListeners();
    }

    this.page = {};

    const render = async ()=>{
        if (searchParam('page') !== null) {
            this.list.page = searchParam('page');
        }
        getSortOption.call(this);
        this.page = await Api.getProductsByCategory(this.list.catId, this.list.page - 1, this.list.sort_option);
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
                    temp += `<a class="page" href="/products/${catId}?page=${beginPage-1}"><</a>`
                }
                for (let i = beginPage; i <= endPage; i++) {
                    temp += `<a class="page ${i== page ? 'paging-active' : ''}" href="/products/${catId}?page=${i}" >${i}</a>`
                }
                if (totalPage !== endPage) {
                    temp += `<a class="page" href="/products/${catId}?page=${endPage+1}">></a>`
                }

            }
            else{
                temp += `<div>게시물이 없습니다.</div>`;
            }

            return temp;
        };
        $(".paging").innerHTML = pagingNav();

    }

    const initEventListeners = () => {
        $("#salesRate").addEventListener("click", async () => {
            localStorage.setItem("productSortOption", 'salesRate');
            await render();
        })

        $("#inDate").addEventListener("click", async () => {
            localStorage.setItem("productSortOption", 'inDate');
            await render();
        })

        $("#dsRate").addEventListener("click", async () => {
            localStorage.setItem("productSortOption", 'dsRate');
            await render();
        })
    }

    let getSortOption = () => {
        if (localStorage.getItem("productSortOption")) {
            this.list.sort_option = localStorage.getItem("productSortOption");
            //이걸 클래스를 대입하는 방식으로 바꿀 예정.
            let elementsByClassName = document.getElementsByClassName("sortOption");
            for (let i = 0; i < elementsByClassName.length; i++) {
                elementsByClassName.item(i).style.fontWeight = 'normal';
            }
            document.getElementById(this.list.sort_option).style.fontWeight = '900';
        }
    }


}
const app = new App();
app.init();