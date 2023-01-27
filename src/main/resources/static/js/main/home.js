import Api from "../api/index.js";
import {$} from "../utils/dom.js";

function App(){

    this.init = async () =>{
        await render();
        await category();
        initEventListeners();
    }

    this.categories = [];

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
    /**
     * 카테고리 작업
     */
    /* 카테고리 */
    let main_cat_container = $("#main_cat_container");
    let sub_cat_container = $("#sub_cat_container");
    let wrapper = $("#cat_wrapper");
    let show_category_button = $("#show_category_button");
    show_category_button.addEventListener("mouseover",()=>{
        main_cat_container.style.display = 'block';
    })

    wrapper.addEventListener("mouseout",()=>{
        main_cat_container.style.display = 'none';
        sub_cat_container.style.display = 'none';
    })

    sub_cat_container.addEventListener("mouseout", ()=>{
        sub_cat_container.style.display = 'none';
    })

    main_cat_container.addEventListener("mouseover",()=>{
        main_cat_container.style.display = 'block';
    })
    //
    // main_cat_container.addEventListener("mouseout",()=>{
    //     main_cat_container.style.display = 'none';
    //     sub_cat_container.style.display = 'none';
    // })

    const initEventListeners = ()=>{
        $("#main_cat_container").addEventListener("mouseover", (e)=>{
            if (e.target.classList.contains('main_cat')) {
                sub_cat_container.style.display = 'block';
                let temp = '';
                let length = Object.keys(this.categories).length;
                for (let i = 0; i < length; i++) {
                    let el = this.categories[e.target.innerText][i];
                    if (el === undefined) {
                        temp +=`<li class="cat main_cat">&nbsp</li>`;
                    }else{
                        temp += `<a href=""><li class="cat main_cat">${el}</li></a>`;
                    }
                }
                sub_cat_container.innerHTML = temp;
            }
        })
    }

    const category = async () =>{
        let result = [];
        result = await Api.getCategories();
        this.categories = result.categoryList;
        Object.keys(result.categoryList).forEach((el)=>{
            main_cat_container.insertAdjacentHTML("afterbegin", `<a href=""><li class="cat main_cat">${el}</li></a>`)
        });
    }

    /**
     * 카테고리 끝.
     */


}
const app = new App();
app.init();

