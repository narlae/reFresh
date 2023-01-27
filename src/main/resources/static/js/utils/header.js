
import Api from "../api/index.js";
import {$} from "../utils/dom.js";

function App(){

    this.init = async () =>{
        await category();
        initEventListeners();
    }

    /**
     * 카테고리 작업
     */
    /* 카테고리 */
    let main_cat_container = $("#main_cat_container");
    let wrapper = $("#cat_wrapper");
    let show_category_button = $("#show_category_button");
    show_category_button.addEventListener("mouseover",()=>{
        main_cat_container.style.display = 'block';
    })

    wrapper.addEventListener("mouseout",()=>{
        main_cat_container.style.display = 'none';
    })

    main_cat_container.addEventListener("mouseover",()=>{
        main_cat_container.style.display = 'block';
    })

    main_cat_container.addEventListener("mouseout",()=>{
        main_cat_container.style.display = 'none';
    })

    const initEventListeners = ()=>{
        $("#main_cat_container").addEventListener("mouseover", (e)=>{
            if (e.target.classList.contains('main_cat')) {
                let all = $("#main_cat_container").querySelectorAll(".depth");
                for (const allElement of all) {
                    allElement.style.display = 'none';
                }
                e.target.querySelector(".depth").style.display = 'block';
            }
        })
    }

    const category = async () =>{
        let result = [];
        result = await Api.getCategories();
        result.forEach(el=>{
            let subCat = '';
            for (let j = 0; j < el.childName.length; j++) {
                subCat+= `<li class="cat sub_cat">
                          <a href="/products/${el.childName[j].catId}">${el.childName[j].catName}</a></li>`
            }
            main_cat_container.insertAdjacentHTML("beforeend",
                `<li class="cat main_cat"><a href="/products/${el.catId}">${el.catName}</a>
                                <div class="depth">
                                    <ul class="depth2">
                                        `+subCat+`
                                    </ul>
                                </div>
                      </li>`)
        })
    }
    /**
     * 카테고리 끝.
     */


}
const app = new App();
app.init();

