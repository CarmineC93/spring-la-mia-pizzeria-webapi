// GLOBAL VARIABLES
const PIZZAS_URL = 'http://localhost:8080/api/pizzas';
const contentElement = document.getElementById('content');
const toggleForm = document.getElementById('toggle-form');
const pizzaForm = document.getElementById('pizza-form');

// API REQUESTS
const getAllPizzas = async () => {
                            // default = GET
    const response = await fetch(PIZZAS_URL);
    return response;
};


const postPizza = async (jsonData) => {
    const fetchOptions = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: jsonData,
    };
    const response = await fetch(PIZZAS_URL, fetchOptions);
    return response;
  };
  
  const deletePizzaById = async (pizzaId) => {
    const response = await fetch(PIZZAS_URL + '/' + pizzaId, { method: 'DELETE' });
    return response;
  };

//DOM MANIPULATION

const toggleFormVisibility = () => {
    document.getElementById('form').classList.toggle('d-none');
  };
  
  const createIngredientsList = (ingredients) => {
    let ingredientsHtml = '<p>';
    ingredients.forEach((ingr) => {
        ingredientsHtml += `<span class="mx-1 badge text-bg-info">${ingr.name}</span>`;
    });
    ingredientsHtml += '</p>';
    return ingredientsHtml;
  };
  
  const createDeleteBtn = (pizza) => {
    let btn = '';
    const specialOffers = pizza.specialOffers;
    if (specialOffers.length !== 0) {
      // disabled button
      btn = `<button class="btn btn-danger" disabled>
              Delete
          </button>`;
    } else {
      btn = `<button data-id="${pizza.id}" class="btn btn-danger">
              Delete
          </button>`;
    }
    return btn;
  };

const createPizzaItem = (item) => {
    return `<div class="col-4">
              <div class="card h-100">
                  <div class="card-header">Name: ${item.name}</div>
                  <div class="card-body">
                      <h5 class="card-title">${item.description}</h5>
                      
                      <p class="card-text d-flex justify-content-between">
                          <span>${item.price}</span>
                      </p>
                    
                      <div>${createDeleteBtn(item)}</div>
                  </div>
                  <div class="card-footer">
                    ${createIngredientsList(item.ingredients)}
                  </div>
              </div>
      </div>`;
  };

const createPizzaList = (data) => {
    console.log(data);
    let list = `<div class="row gy-3">`;
    // add pizza items
    data.forEach((element) => {
      list += createPizzaItem(element);
    });
    list += '</div>';
    return list;
  };

//PRINCIPAL FUNCTIONS 

const loadData = async() =>{
    //call api request
    const response = await getAllPizzas();
    console.log(response);
    if(response.ok){
        //get data
        const data = await response.json();
        console.log(data);
        //render html
        contentElement.innerHTML = createPizzaList(data);
        //add event listener. Select all the buttons
        const deleteBtns = document.querySelectorAll('button[data-id]');
        console.log(deleteBtns);
        for (let btn of deleteBtns) {
          btn.addEventListener('click', () => {
            //id is into the dataset field
            deletePizza(btn.dataset.id);
          });
        }
    }else{
        console.log("error");
    }
};

//save new pizza
const savePizza = async (event) => {
    // prevent default
    event.preventDefault();
    // read input values
    const formData = new FormData(event.target);
    const formDataObj = Object.fromEntries(formData.entries());
    // produce a json
    const formDataJson = JSON.stringify(formDataObj);
    // send ajax request
    const response = await postPizza(formDataJson);
    // parse response
    const responseBody = await response.json();
    if (response.ok) {
      // reload data
      loadData();
      // reset form
      event.target.reset();
    } else {
      // handle error
      console.log('ERROR', response.status, responseBody);
    }
  };

  //delete pizza
  const deletePizza = async (pizzaId) => {
    console.log('delete pizza ' + pizzaId);
    // call delete api
    const response = await deletePizzaById(pizzaId);
    // parse response
    if (response.ok) {
      // reload book list
      loadData();
    } else {
      // handle error
      console.log('ERROR', response.status);
    }
  };

//GLOBAL CODE
toggleForm.addEventListener('click', toggleFormVisibility);
pizzaForm.addEventListener('submit', savePizza);
loadData();

